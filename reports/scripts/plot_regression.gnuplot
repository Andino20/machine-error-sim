# Set the CSV file (first line is filename, skip it)
filename = 'downtime_over_time.csv'  # Replace with your actual filename

# Set output
set terminal pngcairo size 800,600 enhanced font 'Verdana,10'
set output 'regression_plot.png'

# Labels and grid
set title "Machine Downtime over Time"
set xlabel "Simulation Time [minutes]"
set ylabel "Machine Downtime [minutes]"
set grid

# Define linear model: y = a*x + b
f(x) = a*x + b

# Fit model to data, skipping the first line
FIT_LIMIT = 1e-8
fit f(x) filename using 1:2 skip 1 via a, b

# Plot
plot \
    filename using 1:2 skip 1 with points pointtype 7 pointsize 0.5 title 'Data Points', \
    f(x) with lines lw 2 linecolor rgb 'red' title sprintf("Fit: y = %.3fx + %.3f", a, b)
