import numpy as np
import matplotlib.pyplot as plt
import sys
import os

def main():
    if len(sys.argv) != 3:
        print("Usage: python plot_costs.py <filename> <plot_title>")
        sys.exit(1)

    filename = sys.argv[1]
    plot_title = sys.argv[2]

    # Load data, skipping header
    data = np.loadtxt(filename, skiprows=1)

    # Split into columns
    s = data[:, 0]
    machine_cost = data[:, 1]
    worker_cost = data[:, 2]
    total_cost = data[:, 3]

    # Plot
    plt.figure(figsize=(10, 6))
    plt.plot(s, machine_cost, 'o-', label='Machine Cost')
    plt.plot(s, worker_cost, 's--', label='Worker Cost')
    plt.plot(s, total_cost, '^-', label='Total Cost')

    plt.xlabel('# Service Mitarbeiter')
    plt.ylabel('Kosten')
    plt.title(plot_title)
    plt.legend()
    plt.grid(True)
    plt.xticks(np.arange(int(0), int(max(s)) + 1, 2))
    plt.tight_layout()

    # Save plot
    base_name = os.path.splitext(os.path.basename(filename))[0]
    output_file = f"{base_name}_plot.pdf"
    plt.savefig(output_file)
    print(f"Plot saved as: {output_file}")

if __name__ == "__main__":
    main()