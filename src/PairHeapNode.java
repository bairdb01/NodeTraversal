/**
 * Created by ben on 2016-11-05.
 */
public class PairHeapNode {
    Node node;
    PairHeapNode leftChild;
    PairHeapNode sibling;

    public PairHeapNode(){
        node = null;
        leftChild = null;
        sibling = null;
    }

    public PairHeapNode(Node node){
        this.node = node;
        leftChild = null;
        sibling = null;
    }

    @Override
    public boolean equals (Object o){
        if(o instanceof PairHeapNode){
            PairHeapNode c = (PairHeapNode) o;
            return node == c.node;
        }
        return false;
    }
}