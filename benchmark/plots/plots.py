import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import matplotlib    
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np
import math
import json
import datetime
import matplotlib.dates as mdates
import os.path
import pickle

sns.set(context='paper', style={'axes.axisbelow': True,
    'axes.edgecolor': '.8',
    'axes.facecolor': 'white',
    'axes.grid': True,
    'axes.labelcolor': '.15',
    'axes.linewidth': 0.5,
    'figure.facecolor': 'white',
    'font.family': [u'sans-serif'],
    'font.sans-serif': [u'Abel'],
    'font.weight' : u'light',
    'grid.color': '.8',
    'grid.linestyle': u'-',
    'image.cmap': u'Greys',
    'legend.frameon': True,
    'legend.numpoints': 1,
    'legend.scatterpoints': 1,
    'lines.solid_capstyle': u'butt',
    'text.color': '.15',
    'xtick.color': '.15',
    'xtick.direction': u'out',
    'xtick.major.size': 0.0,
    'xtick.minor.size': 0.0,
    'ytick.color': '.15',
    'ytick.direction': u'out',
    'ytick.major.size': 0.0,
    'ytick.minor.size': 0.0}, font_scale = 2)

flatui = ['#28aad5', '#b24d94', '#38ae97' ,'#ec7545']

def minutes_second_formatter(value, tick_number):
    m, s = divmod(value / 1000.0, 60)
    return '%02d:%02d' % (m, s)

def second_formatter(value, tick_number):
    v =  int(value / 1000.0)
    if value == 0 or v > 0:
        return v
    else:
         return '{:.1f}'.format(value / 1000.0)
        

def loadPrecisionRecallData(fn):
    d = {'batch': [], 'run': [], 'precision' : [], 'recall' : [] }
    data = json.load(open(fn))
    run_cnt = 0
    for run in data['runs']:
        tp = len(run['supportErrors'][0]['diffs'])
        fp = run['falsePositivesPerBatch']
        fn = run['falseNegativesPerBatch']
        
        for i in range(len(run['falsePositivesPerBatch'])):
            d['precision'].append(float(tp) / (float(tp) + float(fp[i])))
            d['recall'].append(float(tp) / (float(tp) + float(fn[i])))
            d['batch'].append(i)
            d['run'].append(run_cnt)
        run_cnt += 1    
    return d

def loadMemoryData(fn):
    memory = []
    d = {'time': [], 'memory': [], 'run': []}
    data = json.load(open(fn))
    run_cnt = 0
    step = 2000
    for run in data['runs']:
        bt = run['memoryUsagePerTimeInMillis']

        for b in bt:
            memory.append(bt[b])
        interval = 0
        interval_values = []
        times = [float(t) for t in bt]
        min_t = 0
        max_t = np.max(times)

        d['run'].append(run_cnt)
        d['time'].append(0)
        d['memory'].append(0)

        for bin in np.arange(0, max_t, step):
            values = []
            for t in bt:
                tf = float(t)
                gb = bt[t] / 1024.0
                
                if tf < bin and tf >= bin - step:
                    values.append(gb)
                    
            if len(values) > 0:
                d['run'].append(run_cnt)
                d['time'].append(int((bin)))
                d['memory'].append(np.mean(values))
        run_cnt += 1    
    return d, memory

def loadTruePositives(fn):
    data = json.load(open(fn))
    return len(data['runs'][0]['supportErrors'][0]['diffs'])
    

def loadRawData(fn, dataLabel, yLabel):
    d = {'batch': [], 'run': [], yLabel : []}
    data = json.load(open(fn))
    run_cnt = 0
    for run in data['runs']:
        data = run[dataLabel]
        
        for i in range(len(data)):
            d[yLabel].append(np.mean(data[i]))
            d['batch'].append(i)
            d['run'].append(run_cnt)
        run_cnt += 1 
    return d
    
    
def loadErrorData(fn):
    d = {'batch': [], 'run': [], 'meanError' : []}
    data = json.load(open(fn))
    run_cnt = 0
    for run in data['runs']:
        data = run['errors']
        
        for i in range(len(data)):
            d['meanError'].append(data[i] * 100.0)
            d['batch'].append(i)
            d['run'].append(run_cnt)
        run_cnt += 1 
    return d    
    
def loadAbsoluteErrorData(fn):
    d = {'batch': [], 'run': [], 'meanAbsoluteError' : [], 'maxAbsoluteError' : [], 'theoreticalAbsoluteError' : []}
    data = json.load(open(fn))
    run_cnt = 0
    for run in data['runs']:
        supportErrors = run['supportErrors']

        for i in range(len(supportErrors)):
            dat = [x for x in supportErrors[i]['absoluteErrors']]
            d['meanAbsoluteError'].append(np.mean(dat))
            d['maxAbsoluteError'].append(np.max(dat))
            d['theoreticalAbsoluteError'].append(run['errors'][i])
            d['batch'].append(i)
            d['run'].append(run_cnt)
        run_cnt += 1 
    return d

    
def loadNormalizedErrorData(fn):
    d = {'batch': [], 'run': [], 'meanNormalizedError' : [], 'maxNormalizedError' : []}
    data = json.load(open(fn))
    run_cnt = 0
    for run in data['runs']:
        supportErrors = run['supportErrors']
        
        for i in range(len(supportErrors)):
            dat = [x * 100.0 for x in supportErrors[i]['normalizedErrors']]
            print(np.mean(dat), len(dat))
            d['meanNormalizedError'].append(np.mean(dat))
            d['maxNormalizedError'].append(np.max(dat))
            d['batch'].append(i)
            d['run'].append(run_cnt)
        run_cnt += 1 
    return d
    
def plot_ts(df, ax, color, yLabel, xLabel, width, label, linestyle = '-', showMax = False, displayCumsum = False):
    valid_y1 = []
    valid_y2 = []
    valid_x = []
    line_x = []
    line_y = []
    single_x = []
    single_y = []
    count_never = True
    max_batch = df[xLabel].max() + 1
    min_batch = df[xLabel].min()
    steps = []

    if width > 0:
        steps = range(min_batch, max_batch, width)
    else:
        steps = [t for t in df[xLabel]]

    cumsum = 0
    for b in steps:
        q = df.query(xLabel + ' >= ' + str(b) + ' and ' + xLabel + ' < ' + str(b + width))
        if width == -1:
            q = df.query(xLabel + ' == ' + str(b))
        mean = q[yLabel].mean()
        if displayCumsum:
            cumsum = cumsum + mean
            mean = cumsum
        max = q[yLabel].max()
        std = q[yLabel].std()
        count = q[yLabel].count()
        if count > 1:
            if not showMax:
                valid_x.append(b)
                valid_y1.append(mean - 1.96 * (std / math.sqrt(count)))
                valid_y2.append(mean + 1.96 * (std / math.sqrt(count)))
                line_y.append(mean)
                line_x.append(b)   
            else:
                line_y.append(max)
                line_x.append(b) 

    if not showMax:
        ax.fill_between(valid_x, valid_y1, valid_y2, facecolor=color, alpha=0.4, interpolate=True)
    ax.plot(line_x, line_y, color=color, linewidth=3, linestyle=linestyle, label=label)#, label=yLabel)
    
def getRuntime(fn):
    data = json.load(open(fn))
    runtimes = []
    for run in data['runs']:
        runtimes.append(run['totalRuntimeInMillis'])
        
    return runtimes
    

def main(runtimes, memory, id, file_id, title, show, includeSpam):
    prosecco_runtime = '../results/n_ps-' + id + '-10k_results_runtime_correctness.json'
    prefixspan_runtime = '../results/n_ps-' + id + '-10k_results_prefixspan.json'
    spam_runtime = '../results/n_ps-' + id + '-10k_results_spam.json'

    # eval runtime
    runtimes_prosecco = getRuntime(prosecco_runtime)
    runtimes_prefixspan = getRuntime(prefixspan_runtime)
    if includeSpam:
        runtimes_spam = getRuntime(spam_runtime)
    
    print(title, np.mean(runtimes_prosecco), np.std(runtimes_prosecco), np.mean(runtimes_prefixspan), np.std(runtimes_prefixspan))

    runtimes['prefixspan'][title] = runtimes_prefixspan
    runtimes['prosecco'][title] = runtimes_prosecco
    if includeSpam:
        runtimes['spam'][title] = runtimes_spam

    tp = loadTruePositives(prosecco_runtime)
    file_id = file_id + '-' + str(tp)

    # MEMORY
    f, (ax1) = plt.subplots(1, 1, sharey=False, figsize=(5, 3.5)) 
    
    d, mem = loadMemoryData('../results/n_ps-' + id + '-10k_results_memory.json')
    memory['prosecco'][title] = mem
    df = pd.DataFrame(data=d)   
    df = df.sort_values(by=['time'])
    plot_ts(df, ax1, flatui[0], 'memory', 'time', -1, 'ProSecCo', linestyle = '-')

    d, mem = loadMemoryData('../results/n_ps-' + id + '-10k_results_prefixspan.json')
    memory['prefixspan'][title] = mem
    df = pd.DataFrame(data=d)       
    df = df.sort_values(by=['time'])
    plot_ts(df, ax1, flatui[1], 'memory', 'time', -1, 'PrefixSpan', linestyle = ':')

    if includeSpam:
        d, mem = loadMemoryData('../results/n_ps-' + id + '-10k_results_spam.json')
        memory['spam'][title] = mem
        df = pd.DataFrame(data=d)       
        df = df.sort_values(by=['time'])
        plot_ts(df, ax1, flatui[2], 'memory', 'time', -1, 'SPAM', linestyle = ':')

    ax1.set_ylabel('Memory (GB)')
    ax1.set_xlabel('Time (mm:ss)')
    ax1.xaxis.set_major_formatter(plt.FuncFormatter(minutes_second_formatter))
    #if title == 'ACCIDENTS-0.80'
    #    lgegend = ax1.legend(loc='upper right')
    legend = ax1.legend(loc='best')
    
    legend.get_frame().set_facecolor('#ffffff')
    legend.get_frame().set_linewidth(0.0)
    
    f.savefig('../fig/' + file_id + '-memory.pdf', bbox_inches='tight')
    #f.savefig('../fig/' + file_id + '-memory.svg', bbox_inches='tight')
    plt.tight_layout()
   
    if show:
        plt.show()
    plt.close('all')
    

    # ERROR
    f, (ax2) = plt.subplots(1, 1, sharey=False, figsize=(5, 3.5)) 
    
    d = loadNormalizedErrorData(prosecco_runtime)
    df = pd.DataFrame(data=d)   
    df = df.sort_values(by=['batch'])
    plot_ts(df, ax2, flatui[0], 'meanNormalizedError', 'batch', 1, 'Mean', '-')
    plot_ts(df, ax2, flatui[0], 'maxNormalizedError', 'batch', 1, 'Max', ':', True)
   
    ax2.set_ylabel('Relative Percentage Error')
    ax2.set_xlabel('Block')
    legend = ax2.legend(loc='upper right')   
    legend.get_frame().set_facecolor('#ffffff')
    legend.get_frame().set_linewidth(0.0)
    
    f.savefig('../fig/' + file_id + '-error.pdf', bbox_inches='tight')
    plt.tight_layout()
   
    if show:
        plt.show()
    plt.close('all')  
    
       
    # Precision Recall
    f, (ax3) = plt.subplots(1, 1, sharey=False, figsize=(5, 3.5)) 
    
    d = loadPrecisionRecallData(prosecco_runtime)
    df = pd.DataFrame(data=d)   
    df = df.sort_values(by=['batch'])
    plot_ts(df, ax3, flatui[0], 'precision', 'batch', 1, 'precision')
    plot_ts(df, ax3, flatui[0], 'recall', 'batch', 1, 'Recall', ':')

    ax3.set_ylabel('Precision and Recall')
    ax3.set_xlabel('Block')
    legend = ax3.legend(loc='lower right')
    legend.get_frame().set_facecolor('#ffffff')
    legend.get_frame().set_linewidth(0.0)
    ax3.set_ylim([0.0, 1.1])
        
    f.savefig('../fig/' + file_id + '-precision_recall.pdf', bbox_inches='tight')
    plt.tight_layout()
   
    if show:
        plt.show()
    plt.close('all')    

    # RuntimePerBatch
    if False:
        f, (ax4) = plt.subplots(1, 1, sharey=False, figsize=(5, 3.5)) 
        
        d = loadRawData(prosecco_runtime, 'runtimePerBatch', 'runtimePerBatch')
        df = pd.DataFrame(data=d)  
        df = df.sort_values(by=['batch'])
        plot_ts(df, ax4, flatui[0], 'runtimePerBatch', 'batch', 1, 'ProSecCo', displayCumsum=False, linestyle = '-')

        ax4.set_ylabel('Time (s)')
        ax4.set_xlabel('Block')
        ax4.yaxis.set_major_formatter(plt.FuncFormatter(second_formatter))
        legend = ax4.legend(loc='best')
        legend.get_frame().set_facecolor('#ffffff')
        legend.get_frame().set_linewidth(0.0)
        ax4.set_ylim(bottom=0)
    
        f.savefig('../fig/' + file_id + '-batch_runtime.pdf', bbox_inches='tight')
        plt.tight_layout()
    
        if show:
            plt.show()
        plt.close('all')
    
    # ABS-ERROR
    f, (ax5) = plt.subplots(1, 1, sharey=False, figsize=(5, 3.5)) 
    
    d = loadAbsoluteErrorData(prosecco_runtime)
    df = pd.DataFrame(data=d)   
    df = df.sort_values(by=['batch'])
    plot_ts(df, ax5, flatui[0], 'meanAbsoluteError', 'batch', 1, 'Mean', '-')
    plot_ts(df, ax5, flatui[0], 'maxAbsoluteError', 'batch', 1, 'Max', ':', True)
    plot_ts(df, ax5, flatui[0], 'theoreticalAbsoluteError', 'batch', 1, 'Theoretical', '--', True)
   
    ax5.set_ylabel('Absolute Error')
    ax5.set_xlabel('Block')
    legend = ax5.legend(loc='upper right')
    legend.get_frame().set_facecolor('#ffffff')
    legend.get_frame().set_linewidth(0.0)
    
    f.savefig('../fig/' + file_id + '-absolute_error.pdf', bbox_inches='tight')
    plt.tight_layout()
   
    if show:
        plt.show()
    plt.close('all')  

    return (prefixspan_runtime, prosecco_runtime)
    

if __name__== '__main__':
    show = True
    includeSpam = False
    
    runtimes = {'prefixspan': {}, 'prosecco': {}, 'spam': {}}
    memory = {'prefixspan': {}, 'prosecco': {}, 'spam': {}}

    print('Dataset', 'PS-Mean', 'PS-STD', 'IPS-Mean', 'IPS-STD')

    #(s, p) = main(runtimes, memory, 'accidents-lg-0.80', 'accidents-5-0_80', 'ACCIDENTS-0.80', show, includeSpam)
    #(s, p) = main(runtimes, memory,'accidents-lg-0.85', 'accidents-5-0_85', 'ACCIDENTS-0.85', show, includeSpam)
    #(s, p) = main(runtimes, memory,'accidents-lg-0.90', 'accidents-5-0_90', 'ACCIDENTS-0.90', show, includeSpam)

    #(s, p) = main(runtimes, memory,'bms-webview-lg-0.01', 'bms-webview-100-0_001', 'BMS-0.01', show, includeSpam)
    #(s, p) = main(runtimes, memory,'bms-webview-lg-0.025', 'bms-webview-100-0_025', 'BMS-0.025', show, includeSpam)
    #(s, p) = main(runtimes, memory,'bms-webview-lg-0.05', 'bms-webview-100-0_005', 'BMS-0.05', show, includeSpam)

    #(s, p) = main(runtimes, memory,'kosarak-lg-0.025', 'kosarak-50-0_025', 'KORSARAK-0.025', show, includeSpam)
    #(s, p) = main(runtimes, memory,'kosarak-lg-0.05', 'kosarak-50-0_05', 'KORSARAK-0.05', show, includeSpam)
    #(s, p) = main(runtimes, memory,'kosarak-lg-0.10', 'kosarak-50-0_10', 'KORSARAK-0.10', show, includeSpam)

    #(s, p) = main(runtimes, memory,'msnbc-lg-0.20', 'msnbc-200-0_20', 'MSNBC-0.20', show, includeSpam)
    #(s, p) = main(runtimes, memory,'msnbc-lg-0.30', 'msnbc-200-0_30', 'MSNBC-0.30', show, includeSpam)
    #(s, p) = main(runtimes, memory,'msnbc-lg-0.40', 'msnbc-200-0_40', 'MSNBC-0.40', show, includeSpam)
    
    (s, p) = main(runtimes, memory,'bible-lg-0.40', 'bible-200-0_40', 'BIBLE-0.40', show, includeSpam)
    #(s, p) = main(runtimes, memory,'bible-lg-0.50', 'bible-200-0_50', 'BIBLE-0.50', show, includeSpam)
    #(s, p) = main(runtimes, memory,'bible-lg-0.60', 'bible-200-0_60', 'BIBLE-0.60', show, includeSpam)
    
    #(s, p) = main(runtimes, memory,'fifa-lg-0.30', 'fifa-50-0_30', 'FIFA-0.30', show, includeSpam)
    #(s, p) = main(runtimes, memory,'fifa-lg-0.35', 'fifa-50-0_35', 'FIFA-0.35', show, includeSpam)
    #(s, p) = main(runtimes, memory,'fifa-lg-0.40', 'fifa-50-0_40', 'FIFA-0.40', show, includeSpam)

    
    with open('runtimes.pickle', 'wb') as handle:
        pickle.dump(runtimes, handle, protocol=pickle.HIGHEST_PROTOCOL)

    with open('memory.pickle', 'wb') as handle:
        pickle.dump(memory, handle, protocol=pickle.HIGHEST_PROTOCOL)
    