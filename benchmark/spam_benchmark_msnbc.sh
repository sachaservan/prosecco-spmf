#!/bin/bash

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-10k_spam" -o "./results/n_ps-msnbc-lg-0.20-10k_results_spam.json"-n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-10k_spam" -o "./results/n_ps-msnbc-lg-0.30-10k_results_spam.json"-n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-10k_spam" -o "./results/n_ps-msnbc-lg-0.40-10k_results_spam.json"-n 5 --benchmarkRuntime --spam

å