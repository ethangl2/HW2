import java.util.LinkedList;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class App {
    public static void main(String[] args) throws Exception {
        int time_step = 6;
        Graph<Road, DefaultEdge> network = SimulationMethods.createNetwork(time_step);
        SimulationMethods.createFirstRoad(network,3,180.0,3000.0,2400.0,60.0);
        SimulationMethods.changeCapacity(network, 1.5, 600,1);
        System.out.println(network);
    }
}
