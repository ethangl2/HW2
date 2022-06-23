import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
class SimulationMethods {
    public static LinkedList<Cell> createCellChain(int length, double timestep,double jamDensity, double maxFlow, double currentFlow,double ffspeed) {
        double density = currentFlow/ffspeed;
        int numCells = (int) (length*ffspeed/timestep);
        Cell []cells = new Cell[numCells];
        for (int i = 0; i < numCells ; i++) {
            cells[i] = new Cell(maxFlow/3600.0*timestep,density/numCells*length,jamDensity*length/numCells,0.0);
        }
        List<Cell> list = Arrays.asList(cells);
        LinkedList<Cell> network = new LinkedList<Cell>(list);
        return network;
    }

    public static void changeCapacity(LinkedList<Cell> cellchain, double frac,double capacity,double timestep) {
        ListIterator<Cell> cellIterator = cellchain.listIterator((int) (frac*cellchain.size()));
        while (cellIterator.hasNext()) {
            cellIterator.next().setMaxFlow(capacity/3600.0*timestep);
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