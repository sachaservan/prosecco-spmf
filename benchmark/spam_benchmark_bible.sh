#!/bin/bash

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 10000 -d 5455350 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-10k_spam" -o "./results/n_ps-bible-lg-0.40-10k_results_spam.json"-n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 10000 -d 5455350 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-10k_spam" -o "./results/n_ps-bible-lg-0.50-10k_results_spam.json"-n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 10000 -d 5455350 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-10k_spam" -o "./results/n_ps-bible-lg-0.60-10k_results_spam.json"-n 5 --benchmarkRuntime --spam
