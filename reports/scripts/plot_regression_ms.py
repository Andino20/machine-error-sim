import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import linregress
import sys
import os

def main():
    if len(sys.argv) != 2:
        print("Usage: python plot_regression_ms.py <filename>")
        sys.exit(1)

    filename = sys.argv[1]

    if not os.path.isfile(filename):
        print(f"Error: File '{filename}' not found.")
        sys.exit(1)

    # Load data, skipping header
    data = np.loadtxt(filename, skiprows=1)
    m = data[:, 0]
    s = data[:, 1]

    # Linear regression
    slope, intercept, r_value, p_value, std_err = linregress(m, s)

    # Regression line points
    m_fit = np.linspace(min(m), max(m), 100)
    s_fit = slope * m_fit + intercept

    # Plot
    plt.figure(figsize=(8, 6))
    plt.scatter(m, s, color='blue', label='Datenpunkte')
    plt.plot(m_fit, s_fit, color='red', linestyle='--',
             label=f'Regression: s = {slope:.2f}·m + {intercept:.2f}')
    plt.xlabel('Maschinen')
    plt.ylabel('Mitarbeiter:innen')
    plt.title('Mitarbeiter:innen vs. Maschinen')
    plt.grid(True)
    plt.legend()
    plt.xticks(np.arange(int(0), int(max(m)) + 1, 2))
    plt.tight_layout()

    # Save plot
    output_file = os.path.splitext(os.path.basename(filename))[0] + "_regression.pdf"
    plt.savefig(output_file)
    print(f"Plot saved as: {output_file}")

    plt.show()

if __name__ == "__main__":
    main()
