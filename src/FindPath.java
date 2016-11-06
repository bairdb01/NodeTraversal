import java.util.*;

/**
 * Created by ben on 2016-11-04.
 * TODO: Not mapping some correctly/not displaying correctly
 */
public class FindPath {
    PairHeap ph;
    HashSet<Integer> modifiedNodes;
    String foundPath;

    public FindPath(Node [] nodes ) {
        ph = new PairHeap();
        foundPath = new String();
        this.modifiedNodes =  new HashSet<>();
    }

    public ArrayList<Integer[]> search(Node [] nodes, int src, int dest) {
        // Reset any previously visited edges
        Iterator it;
        modifiedNodes.clear();
        ph.clear();

        // Each container stores the path taken to a node
        ArrayList <Integer []> searchOrder = new ArrayList();
        Integer [] nodePath = new Integer[2];
        nodePath[0] = src;
        nodePath[1] = src;


        // Start the search at desired location
        NodePackage nodePackage = new NodePackage(nodes[src]);
        nodes[src].updatePriority(0);
        ph.insert(nodePackage);


        // Begin the search
        while (!ph.isEmpty()) {
            NodePackage curContainer = ph.removeMin();
            nodePath[0] = curContainer.node.getId();

            // Goal check
            if (curContainer.node.getId() == dest) {
                this.foundPath = curContainer.path;
                return searchOrder;
            } else if (modifiedNodes.contains(curContainer.node.getId())) {
                // Short path to this node already visited
                continue;
            }

            // Find the distances to neighbouring nodes
            it = curContainer.node.getEdges();
            while (it.hasNext()) {
                int neighbourId = (Integer)it.next();

                // Already been at the node
                if (modifiedNodes.contains(neighbourId))
                    continue;

                NodePackage neighbour = new NodePackage(new Node(nodes[neighbourId]), curContainer.path);
                double distance = getDistance(curContainer.node, neighbour.node);
                double newPriority = distance + curContainer.node.getPriority();
                neighbour.node.updatePriority(newPriority);
                ph.insert(neighbour);
                nodePath[1] = neighbourId;
                searchOrder.add(nodePath.clone());
            }

            modifiedNodes.add(curContainer.node.getId());
        }
        return searchOrder;
    }

    public double getDistance(Node node1, Node node2) {
        double xDif =  node2.getXCoord() - node1.getXCoord();
        double yDif = node2.getYCoord() - node1.getYCoord();
        return Math.sqrt(xDif*xDif + yDif*yDif);
    }

    public String getPath(){
        return this.foundPath;
    }
}
