#!/bin/bash

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-10k" -o "./results/n_ps-accidents-lg-0.80-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-10k" -o "./results/n_ps-accidents-lg-0.80-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-10k_prefixspan" -o "./results/n_ps-accidents-lg-0.80-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-10k" -o "./results/n_ps-accidents-lg-0.85-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-10k" -o "./results/n_ps-accidents-lg-0.85-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-10k_prefixspan" -o "./results/n_ps-accidents-lg-0.85-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-10k" -o "./results/n_ps-accidents-lg-0.90-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-10k" -o "./results/n_ps-accidents-lg-0.90-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 10000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-10k_prefixspan" -o "./results/n_ps-accidents-lg-0.90-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan


# different block sizes

# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 20000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-20k" -o "./results/n_ps-accidents-lg-0.80-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 40000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-40k" -o "./results/n_ps-accidents-lg-0.80-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.80 -z 80000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.80-80k" -o "./results/n_ps-accidents-lg-0.80-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 20000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-20k" -o "./results/n_ps-accidents-lg-0.85-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 40000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-40k" -o "./results/n_ps-accidents-lg-0.85-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.85 -z 80000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.85-80k" -o "./results/n_ps-accidents-lg-0.85-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 20000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-20k" -o "./results/n_ps-accidents-lg-0.90-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 40000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-40k" -o "./results/n_ps-accidents-lg-0.90-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
# java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/accidents.txt" -s 0.90 -z 80000 -d 3401830 -r "/usr/local/bin/shuf" -i "n_ps-accidents-lg-0.90-80k" -o "./results/n_ps-accidents-lg-0.90-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
