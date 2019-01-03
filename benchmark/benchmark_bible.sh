#!/bin/bash

# TODO: might need to have full path!

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-10k" -o "./results/n_ps-bible-lg-0.40-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-10k" -o "./results/n_ps-bible-lg-0.40-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-10k_prefixspan" -o "./results/n_ps-bible-lg-0.40-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-10k" -o "./results/n_ps-bible-lg-0.50.3-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-10k" -o "./results/n_ps-bible-lg-0.50-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-10k_prefixspan" -o "./results/n_ps-bible-lg-0.50-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-10k" -o "./results/n_ps-bible-lg-0.60-10k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-10k" -o "./results/n_ps-bible-lg-0.60-10k_results_memory.json" -n 5 --benchmarkMemory --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 10000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-10k_prefixspan" -o "./results/n_ps-bible-lg-0.60-10k_results_prefixspan.json" -n 5 --benchmarkRuntime --prefixspan


# different block sizes

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 20000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-20k" -o "./results/n_ps-bible-lg-0.40-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 40000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-40k" -o "./results/n_ps-bible-lg-0.40-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.40 -z 80000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.40-80k" -o "./results/n_ps-bible-lg-0.40-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 20000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-20k" -o "./results/n_ps-bible-lg-0.50-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 40000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-40k" -o "./results/n_ps-bible-lg-0.50-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.50 -z 80000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.50-80k" -o "./results/n_ps-bible-lg-0.50-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 20000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-20k" -o "./results/n_ps-bible-lg-0.60-20k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 40000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-40k" -o "./results/n_ps-bible-lg-0.60-40k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco
java -jar -Xmx40G ProseccoBenchmark.jar -f "/home/ez/data/sequence/bible.txt" -s 0.60 -z 80000 -d 7273800 -r "/usr/local/bin/shuf" -i "n_ps-bible-lg-0.60-80k" -o "./results/n_ps-bible-lg-0.60-80k_results_runtime_correctness.json" -n 5 --benchmarkRuntime --prosecco

