from os import listdir
import json
import numpy as np
import pickle
from os.path import isfile, join

mypath = '../results/'
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]

onlyfiles = sorted(onlyfiles)
for f in onlyfiles:
    if 'spam' in f:
        print (f)
        data = json.load(open(mypath + f))
        runtimes = []
        for run in data['runs']:
            runtimes.append(run['totalRuntimeInMillis'])
        print('  ' + str(np.mean(runtimes)))



runtimes_comb = {'prefixspan': [], 'prosecco': [], 'spam': []}
runtimes = {}
with open('runtimes.pickle', 'rb') as handle:
    runtimes = pickle.load(handle)
for m in runtimes:
    for ds in runtimes[m]:
        for i in runtimes[m][ds]:
            runtimes_comb[m].append(i)

print("runtimes in s")
for m in runtimes_comb:
    print("  ", m, "mean=", np.mean(runtimes_comb[m]), "std=", np.std(runtimes_comb[m]))


memory_comb = {'prefixspan': [], 'prosecco': [], 'spam': []}
memory = {}
with open('memory.pickle', 'rb') as handle:
    memory = pickle.load(handle)
for m in memory:
    for ds in memory[m]:
        for i in memory[m][ds]:
            memory_comb[m].append(i)

print("memory in MB")
for m in memory_comb:
    print("  ", m, "mean=", np.mean(memory_comb[m]), "std=", np.std(memory_comb[m]))

