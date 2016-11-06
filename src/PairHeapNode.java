/**
 * Author: Benjamin Baird
 * Created on: 2016-11-05
 * Last Updated on: 2016-11-05
 * Filename: PairHeapNode.java
 * Description: Nodes for the pairing heap
 **/
public class PairHeapNode {
    NodePackage pkg;
    PairHeapNode leftChild;
    PairHeapNode sibling;

    public PairHeapNode(){
        pkg = null;
        leftChild = null;
        sibling = null;
    }

    public PairHeapNode(NodePackage nodePkg){
        this.pkg = nodePkg;
        leftChild = null;
        sibling = null;
    }

    @Override
    public boolean equals (Object o){
        if(o instanceof PairHeapNode){
            PairHeapNode c = (PairHeapNode) o;
            return pkg.node == c.pkg.node;
        }
        return false;
    }
}