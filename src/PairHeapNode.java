/**
 * Created by ben on 2016-11-05.
 */
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