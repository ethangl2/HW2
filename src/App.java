import java.util.LinkedList;
public class App {
    public static void main(String[] args) throws Exception {
        LinkedList<Cell> network = SimulationMethods.createCellChain(3,6.0,180.0,3000.0,2400.0,60.0);
        SimulationMethods.changeCapacity(network, 0.5, 600 , 6);
        SimulationMethods.updateFlow(network);
        int time = 0;
        int time_step = 6;

    }
}
