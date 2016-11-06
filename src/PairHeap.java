import java.util.ArrayList;

/**
 * Author: Benjamin Baird
 * Created on: 2016-11-05
 * Last Updated on: 2016-11-05
 * Filename: PairHeap.java
 * Description: A pairing heap implementation where each node is valued by its Node.priority
 **/
public class PairHeap {
    PairHeapNode root;

    public PairHeap(){
        this.root = null;
    }

    // Clears the heap
    public void clear() {
        root = null;
    }

    // Checks if the heap is empty
    public boolean isEmpty(){
        return root == null;
    }

    // Removes the root and reheapifies
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

    // Merges siblings into one heap using 2-passes
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

    // Merges two heaps into one
    public PairHeapNode merge (PairHeapNode heap1, PairHeapNode heap2){
        if (heap2 == null) {
            return heap1;
        } else if (heap1 == null) {
            return heap2;
        }

        // Add heap with larger root to to left child of heap with smaller root
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

    // Inserts a NodePackage into the heap
    public PairHeapNode insert( NodePackage nodePkg) {
        PairHeapNode toInsert = new PairHeapNode(nodePkg);

        if (root == null) {
            root = toInsert;
            return root;
        }
        root =  merge(root, toInsert);
        return toInsert;
    }

}
