import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ben on 2016-11-03.
 */
public class Node {
    private HashSet<Integer> edges;
    private Integer nodeId;
    private int xCoord;
    private int yCoord;
    private double priority;

    public Node (Node node) {
        this.edges = node.edges;
        this.nodeId = node.nodeId;
        this.xCoord = node.xCoord;
        this.yCoord = node.yCoord;
        this.priority = node.priority;
    }
    public Node(){
        this.edges = new HashSet<>();
        xCoord = 0;
        yCoord = 0;
        priority = 999999999;
        nodeId = -1;
    }

    public Node(int nodeId, int xCoord, int yCoord){
        this.nodeId = nodeId;
        this.edges = new HashSet<>();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.priority = 999999999;
    }

    /**
     * Creates a connection between 2 nodes
     * @return
     */
    public void addEdge(Integer destId){
        this.edges.add( destId );
    }

    /**
     * Outputs the class variables to a String
     * @return String of the class variables and values
     */
    public String toString(){
        StringBuilder newString = new StringBuilder();
        newString.append("NodeID: " + nodeId + "\n");
        newString.append("Priority: " + priority + "\n");
        newString.append("Coordinates: " + this.xCoord + " " +  this.yCoord + "\n");
//        Iterator it = edges.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            newString.append(pair.getKey() + ":" + edges.(pair.getKey()).toString());
//        }
        return newString.toString();
    }

    public int getYCoord(){
        return yCoord;
    }

    public int getXCoord(){
        return xCoord;
    }

    public Iterator getEdges(){
        return edges.iterator();
    }

    public int getId(){
        return nodeId;
    }

    void updatePriority(double newPriority) {
        this.priority = newPriority;
    }

    double getPriority(){
        return this.priority;
    }


}
