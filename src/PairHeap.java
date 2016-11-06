import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ben on 2016-11-05.
 */
public class PairHeap {
    PairHeapNode root;

    public PairHeap(){
        this.root = null;
    }

    public void clear() {
        root = null;
    }
    public boolean isEmpty(){
        return root == null;
    }

    public NodePackage removeMin() {
        if (root == null)
            return null;

        if (root.leftChild == null) {
            NodePackage nodePkg= root.pkg;
            root = null;
            return nodePkg;
        } else {
            NodePackage nodePkg = root.pkg;
            root = mergeSiblings(root.leftChild);
            return nodePkg;
        }
    }

    public PairHeapNode mergeSiblings (PairHeapNode heap) {
        if (heap == null)
            return null;

        if (heap.sibling == null) {
            return heap;
        }

        ArrayList<PairHeapNode> siblings = new ArrayList<>(10);
        PairHeapNode tempHeap = heap;
        while (tempHeap != null) {
            siblings.add(tempHeap);
            tempHeap = tempHeap.sibling;
        }

        // First Pass Merge Pairs left -> right
        int i = 0;
        for ( ; i+1 < siblings.size(); i+= 2) {
            siblings.add(i, merge(siblings.get(i), siblings.get(i+1)));
            siblings.remove(i+1);
        }

        i -= 2;
        // Odd number of siblings
        if (i == (siblings.size() - 3)) {
            siblings.add(i, merge(siblings.get(i), siblings.get(i+2)));
            siblings.remove(i+1);
        }

        // Second Pass Merge pairs right <- left
        for (; i >= 2; i-=2) {
            siblings.add(i-2, merge(siblings.get(i), siblings.get(i-2)));
            siblings.remove((i-2)+1);
        }

        return siblings.get(0);
    }
//    o Merging
// Make the heap with the larger root the new first child of the heap with the
//    smaller root
    public PairHeapNode merge (PairHeapNode heap1, PairHeapNode heap2){
        if (heap2 == null) {
            return heap1;
        } else if (heap1 == null) {
            return heap2;
        }

        // Add heap1 to left child of heap2
        if (heap1.pkg.node.getPriority() > heap2.pkg.node.getPriority()) {

            heap1.sibling = heap2.leftChild;
            heap2.leftChild = heap1;
            return heap2;

        } else {
            heap2.sibling = heap1.leftChild;
            heap1.leftChild = heap2;
            return heap1;
        }
    }

//    o Insertion
// A special case of merge
    public PairHeapNode insert( NodePackage nodePkg) {
        PairHeapNode toInsert = new PairHeapNode(nodePkg);

        if (root == null) {
            root = toInsert;
            return root;
        }
        root =  merge(root, toInsert);
        return toInsert;
    }


//    public static void main (String [] args){
//        PairHeap heap = new PairHeap();
//        Random rn = new Random();
//        for (int i = 0 ; i < 10; i++) {
//            NodePackage pkgNode = new NodePackage(new Node(i,i,i));
//            node.updatePriority(rn.nextInt(500));
//            heap.insert(node);
//        }
//
//        while (!heap.isEmpty())
//            System.out.print(heap.removeMin());
//
//    }
}
