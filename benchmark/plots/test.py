from os import listdir
import json
import numpy as np
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

