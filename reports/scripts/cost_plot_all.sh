#!/bin/bash

# Loop through m1.txt to m20.txt
for i in $(seq -w 1 20); do
    filename="m${i}.txt"
    title="Kosten: ${i} Maschine(n)"
    python cost_plot.py "../$filename" "$title"
done