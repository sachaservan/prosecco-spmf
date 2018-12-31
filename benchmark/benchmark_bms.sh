#!/bin/bash
sleep 15

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-10k" -o "./results/n_ps-bms-webview-lg-0.025-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-10k" -o "./results/n_ps-bms-webview-lg-0.025-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-10k_prefixspan" -o "./results/n_ps-bms-webview-lg-0.025-10k_results_prefixspan.json"-n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-10k" -o "./results/n_ps-bms-webview-lg-0.05-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-10k" -o "./results/n_ps-bms-webview-lg-0.05-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-10k_prefixspan" -o "./results/n_ps-bms-webview-lg-0.05-10k_results_prefixspan.json"-n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.1 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-10k" -o "./results/n_ps-bms-webview-lg-0.10-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.1 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-10k" -o "./results/n_ps-bms-webview-lg-0.10-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.1 -z 10000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-10k_prefixspan" -o "./results/n_ps-bms-webview-lg-0.10-10k_results_prefixspan.json"-n 5 --benchmarkRuntime --prefixspan


# different block sizes

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 20000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-20k" -o "./results/n_ps-bms-webview-lg-0.025-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 40000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-40k" -o "./results/n_ps-bms-webview-lg-0.025-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.025 -z 80000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.025-80k" -o "./results/n_ps-bms-webview-lg-0.025-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 20000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-20k" -o "./results/n_ps-bms-webview-lg-0.05-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 40000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-40k" -o "./results/n_ps-bms-webview-lg-0.05-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.05 -z 80000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.05-80k" -o "./results/n_ps-bms-webview-lg-0.05-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.10 -z 20000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-20k" -o "./results/n_ps-bms-webview-lg-0.10-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.10 -z 40000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-40k" -o "./results/n_ps-bms-webview-lg-0.10-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bms-webview.txt" -s 0.10 -z 80000 -d 17880001 -r "/usr/local/bin/shuf" -i "n_ps-bms-webview-lg-0.10-80k" -o "./results/n_ps-bms-webview-lg-0.10-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
