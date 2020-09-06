package MaxFibonacciHeap;

public class Node {

    /** The keyword of hashtag
     */
    public String keyword;

    /** Frequency of the hashtag
     */
    public int frequency;

    public Boolean childCut;

    public int degree;

    public Node parent;

    public Node leftmostChild;

    public Node leftSibling;

    public Node rightSibling;

    public Node(int var1, String var2) {

        this.keyword = var2;

        this.frequency = var1;

        this.childCut=null;

        /** When we insert a new node in max Fibonacci heap, the default degree will be 0, therefore, I set the default
         *  parent, leftmostChild, leftSibling and rightSibling to null.
         */
        this.degree = 0;

        this.parent = null;

        this.leftmostChild = null;

        this.leftSibling = null;

        this.rightSibling = null;

    }
}
