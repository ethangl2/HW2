import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

class SimulationMethods {
    static int timestep;

    public static void changeTimeStep(int t) {
        timestep = t;
    }

    public static void createMergingRoad(Graph<Road, DefaultEdge> network,int length,double jamDensity, double maxFlow, double currentFlow,double ffspeed, int name, int parentRode, double x) {
        network.addVertex(new Road(length,jamDensity,maxFlow,currentFlow,ffspeed,name));
        Set<Road> roadSet = network.vertexSet();
        Road[] roadArray = (Road[]) roadSet.toArray();
        Road newRoad = roadArray[roadArray.length-1];
        network.addEdge(newRoad,roadArray[parentRode]);
        double density = currentFlow/ffspeed;
        int numCells = (int) (length*ffspeed/timestep);
        Cell []cells = new Cell[numCells];
        for (int i = 0; i < numCells ; i++) {
            if (i == 0) {
                cells[i]= new Cell(0,Double.MAX_VALUE,0,0);
                continue;
            }
            cells[i]= new Cell(maxFlow/3600.0*timestep,density/numCells*length,jamDensity*length/numCells,0.0);
            newRoad.getRoadGraph().addVertex(cells[i]);
        }
    }

    public static void createFirstRoad(Graph<Road, DefaultEdge> network,int length,double jamDensity, double maxFlow, double currentFlow,double ffspeed) {
        network.addVertex(new Road(length,jamDensity,maxFlow,currentFlow,ffspeed,1));
        Set<Road> roadSet = network.vertexSet();
        Object[] roadArray = roadSet.toArray();
        Road newRoad = (Road) roadArray[roadArray.length-1];
        double density = currentFlow/ffspeed;
        int numCells = (int) (length*ffspeed/timestep + 2);
        Cell []cells = new Cell[numCells];
        for (int i = 0; i < numCells ; i++) {
            if (i == 0) {
                cells[i]= new Cell(0,Double.MAX_VALUE,0,0);
                newRoad.getRoadGraph().addVertex(cells[i]);
                continue;
            } else if (i == numCells - 1) {
                cells[i]= new Cell(0,0,Double.MAX_VALUE,0);
                newRoad.getRoadGraph().addVertex(cells[i]);
                newRoad.getRoadGraph().addEdge(cells[i-1],cells[i]);
                break;
            }
            cells[i]= new Cell(maxFlow/3600.0*timestep,density/(numCells-2)*length,jamDensity*length/(numCells-2),0.0);
            newRoad.getRoadGraph().addVertex(cells[i]);
            newRoad.getRoadGraph().addEdge(cells[i-1],cells[i]);
        }
    }

    public static Graph<Road, DefaultEdge> createNetwork(int t) {
        timestep = t;
        Road initial_road = new Road();
        Graph<Road, DefaultEdge> toreturn = new DefaultDirectedGraph<>(DefaultEdge.class);
        Cell source_cell = new Cell(0,Double.MAX_VALUE,0,0);
        Cell sink_cell = new Cell(0,0,Double.MAX_VALUE,0);
        initial_road.getRoadGraph().addVertex(source_cell);
        initial_road.getRoadGraph().addVertex(sink_cell);
        toreturn.addVertex(initial_road);
        return toreturn;
    }

    public static void changeCapacity(Graph<Road, DefaultEdge> network, double x,double capacity,int id) {
        Set<Road> roadSet = network.vertexSet();
        Object[] roadArray = roadSet.toArray();
        Road to_change = (Road) roadArray[id];
        Set<Cell> cellSet = to_change.getRoadGraph().vertexSet();
        int length = to_change.getLength();
        Object[] cellArray = cellSet.toArray();
        int startIndex = (int) (cellArray.length*x/length);
        for (int i = startIndex; i < cellArray.length - 1; i++) {
            Cell cell = (Cell) cellArray[i];
            cell.setMaxFlow(capacity/3600*timestep);
        }
    }

    public static LinkedList<Cell> initializeFlow(LinkedList<Cell> cellchain) {
        for (int i = 0; i < cellchain.size() - 1; i++) {
            Cell currentCell = cellchain.get(i);
            Cell nextCell = cellchain.get(i+1);
            double S = Math.min(currentCell.getMaxFlow(),currentCell.getNumCars());
            double R = Math.min(nextCell.getMaxFlow(),nextCell.getMaxCars()-nextCell.getNumCars());
            double yt = Math.min(S,R);
            System.out.println(yt);
            currentCell.setCurrentFlow(yt);
        }
        return cellchain;
    }
}