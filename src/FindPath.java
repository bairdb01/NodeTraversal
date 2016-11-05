import java.awt.*;
import java.util.*;

/**
 * Created by ben on 2016-11-04.
 * TODO: Not mapping some correctly/not displaying correctly
 */
public class FindPath {
    PriorityQueue<QContainer> q;
    private HashMap<Integer, Node> modifiedNodes;
    String foundPath;

    public FindPath(Node [] nodes ) {

        q = new PriorityQueue<>(new Comparator<QContainer>() {
            @Override
            public int compare(QContainer container1, QContainer container2) {
                if (container1.node.getPriority() > container2.node.getPriority())
                    return 1;
                else if (container1.node.getPriority() < container2.node.getPriority())
                    return -1;
                else
                    return 0;
            }
        });
        modifiedNodes = new HashMap<>();
        foundPath = new String();
    }

    public void createQueue(Node [] nodes) {
        if (nodes != null)
            // Add the Edges to the queue
            for(Node node : nodes) {
                q.add(new QContainer(node));
            }
    }

    public ArrayList<Integer[]> search(Node [] nodes, int src, int dest) {
        if (q.isEmpty())
            return null;

        // Reset any previously visited edges
        Iterator it;
        it = modifiedNodes.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Node node = nodes[(Integer)pair.getKey()];
            QContainer qContainer = new QContainer(node);
            q.remove(qContainer);
            qContainer.node.updatePriority(999999999);
            q.add(qContainer);
        }
        modifiedNodes.clear();
        ArrayList <Integer []> searchOrder = new ArrayList();
        Integer [] nodePath = new Integer[2];
        nodePath[0] = src;
        nodePath[1] = src;


        // Start the search at desired location
        QContainer qContainer = new QContainer(nodes[src]);
        q.remove(qContainer);
        nodes[src].updatePriority(0);
        q.add(qContainer);


        // Begin the search
        while (!q.isEmpty()) {
            QContainer curContainer = q.poll();
            nodePath[0] = curContainer.node.getId();

//            System.out.println(curNode);

            // Goal check
            if (curContainer.node.getId() == dest) {
                q.add(curContainer);
                System.out.println("Found path!");
                this.foundPath = curContainer.path;
                return searchOrder;
            } 

            // Find the distances to neighbouring nodes
            it = curContainer.node.getEdges();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                int neighbourId = (Integer)pair.getKey();
                QContainer neighbour = new QContainer(nodes[neighbourId], curContainer.path);
                double distance = getDistance(curContainer.node, neighbour.node);
                double newPriority = distance + curContainer.node.getPriority();

                if (neighbour.node.getPriority() == 999999999) {
                    q.remove(neighbour);
                    neighbour.node.updatePriority(newPriority);
                    curContainer.node.updateEdge(neighbourId, distance);
                    modifiedNodes.put(neighbourId, neighbour.node);
                    q.add(neighbour);

                    nodePath[1] = neighbourId;
                    searchOrder.add(nodePath.clone());

                } else if (newPriority < neighbour.node.getPriority()) {
                        q.remove(neighbour);
                        neighbour.node.updatePriority(newPriority);
                        curContainer.node.updateEdge(neighbourId, distance);
                        modifiedNodes.put(neighbourId, neighbour.node);
                        q.add(neighbour);
                        nodePath[1] = neighbourId;
                        searchOrder.add(nodePath.clone());
                }

//                System.out.println(neighbour);
            }
            modifiedNodes.put(curContainer.node.getId(), curContainer.node);
        }
        System.out.println("No path found!");
        return searchOrder;
    }

    public double getDistance(int x1, int y1, int x2, int y2){
        double xDif =  x2 - x1;
        double yDif = y2 - y1;
        return Math.sqrt(xDif*xDif + yDif*yDif);
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
