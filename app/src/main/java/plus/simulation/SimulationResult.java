package plus.simulation;

/**
 * @param totalDowntime   in minutes
 * @param totalRepairTime in minutes
 */
public record SimulationResult(double totalDowntime, double totalRepairTime) {

    @Override
    public String toString() {
        return "SimulationResult{" +
                "totalDowntime=" + totalDowntime +
                ", totalRepairTime=" + totalRepairTime +
                '}';
    }
}
