public class Cell {
    private double maxFlow;
    private double numCars;
    private double maxCars;
    private double currentFlow;

    public Cell(double maxFlow, double numCars,double maxCars,double currentFlow) {
        this.maxFlow = maxFlow;
        this.numCars = numCars;
        this.maxCars = maxCars;
        this.currentFlow = currentFlow;
    }
    public double getMaxFlow() {
        return maxFlow;
    }
    public double getNumCars() {
        return numCars;
    }
    public double getMaxCars() {
        return maxCars;
    }
    public double getCurrentFlow() {
        return currentFlow;
    }
    public void setMaxFlow(double flow) {
        this.maxFlow = flow;
    }
    public void setNumCars(double cars) {
        this.numCars = cars;
    }
    public void setMaxCars(double max) {
        this.maxCars = max;
    }
    public void setCurrentFlow(double flow) {
        this.currentFlow= flow;
    }
    public void setAll(double maxFlow, double numCars,double maxCars,double currentFlow) {
        this.maxFlow = maxFlow;
        this.numCars = numCars;
        this.maxCars = maxCars;
        this.currentFlow = currentFlow;
    }
    @Override
    public String toString() {
        return Double.toString(numCars) + " " + Double.toString(maxFlow) + " " + Double.toString(maxCars) + " " + Double.toString(currentFlow);
    }
}