import java.util.*;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
public class Road {
    private int length;
    private double jamDensity;
    private double maxFlow;
    private double currentFlow;
    private double ffspeed;

    private int id;

    private ArrayList<Double[]> connecting_points;
    private Graph<Cell, DefaultEdge> roadGraph;

    public Road(int length, double jamDensity, double maxFlow, double currentFlow, double ffspeed, int id) {
        this.length = length;
        this.jamDensity = jamDensity;
        this.maxFlow = maxFlow;
        this.currentFlow = currentFlow;
        this.ffspeed = ffspeed;
        Graph<Cell, DefaultEdge> roadGraph1 = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.roadGraph = roadGraph1;
        ArrayList<Double[]> connecting_points1 = new ArrayList<Double[]>();
        this.connecting_points = connecting_points1;
        this.id = id;
    }
    public Road() {
        Graph<Cell, DefaultEdge> roadGraph1 = new DefaultDirectedGraph<>(DefaultEdge.class);
        this.roadGraph = roadGraph1;
    }
    public int getLength() {return this.length;}
    public double getJamDensity() {return this.jamDensity;}
    public double getMaxFlow() {return this.maxFlow;}
    public double getCurrentFlow() {return this.currentFlow;}
    public double getFfspeed() {return this.ffspeed;}
    public Graph<Cell, DefaultEdge> getRoadGraph() {return this.roadGraph;}
    public int getId() {return this.id;}

    public ArrayList<Double[]> getConnecting_points() {return this.connecting_points;}
    public void addConnectingPoint(double x,double id) {
        Double[] pair = {x,id};
        this.connecting_points.add(pair);
    }
    @Override
    public String toString() {
        String toreturn = "";
        Set<Cell> cellSet = roadGraph.vertexSet();
        Object[] cellArray = cellSet.toArray();
        for (Object cell:cellArray) {
            Cell printcell = (Cell) cell;
            toreturn = toreturn + printcell + "\n";
        }
        return toreturn;
    }
}
