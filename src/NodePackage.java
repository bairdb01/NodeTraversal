/**
 * Author: ben
 * Created on: 2016-11-05
 * Last Updated on: 2016-11-05
 * Filename: NodePackage.java
 * Description: A wrapper class to hold both the node and the path taken to the node
 **/
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
