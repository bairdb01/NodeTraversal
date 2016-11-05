import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ben on 2016-11-03.
 * TODO: Edge class doesn't do anything, remove? Change edges to hashset
 */
public class Node {
    private HashMap<Integer, Edge> edges;
    private Integer nodeId;
    private int xCoord;
    private int yCoord;
    private double priority;


    public Node(){
        this.edges = new HashMap<>();
        xCoord = 0;
        yCoord = 0;
        priority = 999999999;
        nodeId = -1;
    }

    public Node(int nodeId, int xCoord, int yCoord){
        this.nodeId = nodeId;
        this.edges = new HashMap<>();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.priority = 999999999;
    }

    /**
     * Creates a connection between 2 nodes
     * @return
     */
    public boolean addEdge(Edge newEdge, Integer destId){
        this.edges.put(destId, newEdge);
        return (this.edges.get(destId) != null);
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
        Iterator it = edges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            newString.append(pair.getKey() + ":" + edges.get(pair.getKey()).toString());
        }
        return newString.toString();
    }

    /**
     * Change the distance of an edge
     * @param destId id of connected node
     * @param distance the new distance from this node to the other node
     */
    public void updateEdge(Integer destId, double distance){
        Edge temp = edges.get(destId);
        temp.updateDistance(distance);
    }

    public int getYCoord(){
        return yCoord;
    }

    public int getXCoord(){
        return xCoord;
    }

    public Iterator getEdges(){
        return edges.entrySet().iterator();
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
