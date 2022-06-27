import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

class SimulationMethods {
    static int timestep;

    public static void changeTimeStep(int t) {
        timestep = t;
    }

    public static void simulate(Graph<Road, DefaultEdge> network, int time) {
        int num_steps = time/timestep;
        initializeFlow(network);
        for (int t = 1; t <= num_steps; t ++) {
            Set<Road> roadSet = network.vertexSet();
            Object[] roadArray = roadSet.toArray();
            for (int i = 1; i <roadArray.length;i++) {
                Road road = (Road) roadArray[i];
                Set<Cell> cellSet = road.getRoadGraph().vertexSet();
                Object[] cellArray = cellSet.toArray();
                double numEPrime = 0;
                for (int j = 1; j < cellArray.length-1;j++) {
                    Cell currentCell = (Cell) cellArray[j];
                    Cell nextCell = (Cell) cellArray[j+1];
                    double S = Math.min(currentCell.getMaxFlow(),currentCell.getNumCars());
                    double R = Math.min(nextCell.getMaxFlow(),nextCell.getMaxCars()-nextCell.getNumCars());
                    double yt = Math.min(S,R);
                    if (j == 1) {
                        Cell source_cell = (Cell) cellArray[0];
                        currentCell.setNumCars(currentCell.getNumCars()-yt+source_cell.getCurrentFlow());
                        numEPrime = nextCell.getNumCars() + yt;
                        continue;
                    } else if (j == cellArray.length-2) {
                        currentCell.setNumCars(currentCell.getNumCars()-yt);
                        continue;
                    }
                    currentCell.setNumCars(numEPrime-yt);
                    numEPrime = nextCell.getNumCars() + yt;
                }
            }
            System.out.println(network + " " + t*timestep);
        }
    }
    public static void createMergingRoad(Graph<Road, DefaultEdge> network,int length,double jamDensity, double maxFlow, double currentFlow,double ffspeed, int name, int parentRode, double x) {
        network.addVertex(new Road(length,jamDensity,maxFlow,currentFlow,ffspeed,name));
        Set<Road> roadSet = network.vertexSet();
        Object[] roadArray = roadSet.toArray();
        Road newRoad = (Road) roadArray[roadArray.length-1];
        network.addEdge(newRoad,(Road) roadArray[parentRode]);
        double density = currentFlow/ffspeed;
        int numCells = (int) (length*ffspeed/timestep);
        Cell []cells = new Cell[numCells];
        for (int i = 0; i < numCells ; i++) {
            if (i == 0) {
                cells[i]= new Cell(maxFlow/3600.0*timestep,Double.MAX_VALUE,Double.MAX_VALUE,0);
                newRoad.getRoadGraph().addVertex(cells[i]);
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
                cells[i]= new Cell(maxFlow/3600.0*timestep,Double.MAX_VALUE,Double.MAX_VALUE,0);
                newRoad.getRoadGraph().addVertex(cells[i]);
                continue;
            } else if (i == numCells - 1) {
                cells[i]= new Cell(maxFlow/3600.0*timestep,0,Double.MAX_VALUE,0);
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
        int startIndex = (int) ((cellArray.length-2)*x/length);
        Cell cell = (Cell) cellArray[startIndex];
        cell.setMaxFlow(capacity/3600*timestep);
    }

    public static void initializeFlow(Graph<Road, DefaultEdge> network) {
        Set<Road> roadSet = network.vertexSet();
        Object[] roadArray = roadSet.toArray();
        for(int i = 1; i < roadArray.length; i++) {
            Road road = (Road) roadArray[i];
            Set<Cell> cellSet = road.getRoadGraph().vertexSet();
            Object[] cellArray = cellSet.toArray();
            for (int j = 0 ; j< cellArray.length-1;j++) {
                Cell currentCell = (Cell) cellArray[j];
                Cell nextCell = (Cell) cellArray[j+1];
                if (j == 0) {
                    currentCell.setCurrentFlow(nextCell.getNumCars());
                    continue;
                }
                double S = Math.min(currentCell.getMaxFlow(),currentCell.getNumCars());
                double R = Math.min(nextCell.getMaxFlow(),nextCell.getMaxCars()-nextCell.getNumCars());
                double yt = Math.min(S,R);
                currentCell.setCurrentFlow(yt);
            }
        }
    }
}