from os import listdir
from os.path import isfile, join

mypath = '../results/'
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]

onlyfiles = sorted(onlyfiles)
for f in onlyfiles:
    if 'spam' in f:
        print (f)

