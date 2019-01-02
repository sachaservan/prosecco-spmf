#!/bin/bash

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-10k" -o "./results/n_ps-msnbc-lg-0.20-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-10k" -o "./results/n_ps-msnbc-lg-0.20-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-10k_prefixspan" -o "./results/n_ps-msnbc-lg-0.20-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-10k" -o "./results/n_ps-msnbc-lg-0.30-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-10k" -o "./results/n_ps-msnbc-lg-0.30-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-10k_prefixspan" -o "./results/n_ps-msnbc-lg-0.30-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-10k" -o "./results/n_ps-msnbc-lg-0.40-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-10k" -o "./results/n_ps-msnbc-lg-0.40-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 10000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-10k_prefixspan" -o "./results/n_ps-msnbc-lg-0.40-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan


# different block sizes

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 20000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-20k" -o "./results/n_ps-msnbc-lg-0.20-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 40000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-40k" -o "./results/n_ps-msnbc-lg-0.20-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.20 -z 80000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.20-80k" -o "./results/n_ps-msnbc-lg-0.20-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 20000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-20k" -o "./results/n_ps-msnbc-lg-0.30-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 40000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-40k" -o "./results/n_ps-msnbc-lg-0.30-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.30 -z 80000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.30-80k" -o "./results/n_ps-msnbc-lg-0.30-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 20000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-20k" -o "./results/n_ps-msnbc-lg-0.40-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 40000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-40k" -o "./results/n_ps-msnbc-lg-0.40-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/msnbc.txt" -s 0.40 -z 80000 -d 3179000 -r "/usr/local/bin/shuf" -i "n_ps-msnbc-lg-0.40-80k" -o "./results/n_ps-msnbc-lg-0.40-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

