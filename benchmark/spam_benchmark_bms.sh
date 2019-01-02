#!/bin/bash
sleep 15

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.01 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.01-10k_spam" -o "./results/n_ps-bms-webview-lg-0.01-10k_results_spam.json" -n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-10k_spam" -o "./results/n_ps-bms-webview-lg-0.025-10k_results_spam.json" -n 5 --benchmarkRuntime --spam
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-10k_spam" -o "./results/n_ps-bms-webview-lg-0.05-10k_results_spam.json" -n 5 --benchmarkRuntime --spam
