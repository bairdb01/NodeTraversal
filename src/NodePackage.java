/**
 * Created by ben on 2016-11-05.
 */
public class NodePackage {
    String path;
    Node node;

    public NodePackage(Node node){
        this.node = node;
        path = Integer.toString(node.getId());
    }

    public NodePackage(Node node, String existingPath){
        this.node = node;
        this.path = existingPath + "," + Integer.toString(node.getId());
    }

    @Override
    public boolean equals (Object o){
        if(o instanceof NodePackage){
            NodePackage c = (NodePackage) o;
            return node == c.node;
        }
        return false;
    }
}
