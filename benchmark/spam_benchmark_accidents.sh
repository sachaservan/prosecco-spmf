#!/bin/bash
sleep 15


# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-10k_spam" -o "./results/n_ps-accidents-lg-0.80-10k_results_spam.json" -n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-10k_spam" -o "./results/n_ps-accidents-lg-0.85-10k_results_spam.json" -n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-10k_spam" -o "./results/n_ps-accidents-lg-0.90-10k_results_spam.json" -n 5 --benchmarkRuntime --spam

