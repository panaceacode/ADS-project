package MaxFibonacciHeap;
import java.util.HashMap;

public class Implement {

    public Node max;

    public Implement() {
        this.max = null;
    }

    /**
     * This is the function for inserting a node;
     * If this is an empty heap, this newly inserted node will become the max Node;
     * If this is not an empty heap, I put the newly inserted node to the top list of this heap, and if this node's
     * frequency is larger than the max node, max pointer will point to this newly inserted Node
     * @param inNode the newly inserted node
     */
    public void insertNode(Node inNode) {

        if (this.max == null) {
            this.max = inNode;
            inNode.leftSibling = inNode;
            inNode.rightSibling = inNode;
        } else {
            inNode.leftSibling = this.max;
            inNode.rightSibling = this.max.rightSibling;
            this.max.rightSibling.leftSibling = inNode;
            this.max.rightSibling = inNode;
            inNode.childCut = null;

            if (inNode.frequency > this.max.frequency) {
                this.max = inNode;
            }
        }

    }

    /**
     * This is the function to simulate the process when I remove one Node from its parent. var1 is the node we need to
     * remove and var2 is Var1's parent. (Note: it's not the remove operation since I assume var1 has a parent)
     *
     * @param rmNode the node I removed from its parent
     * @param parent the removed node's parent
     */
    private void removeOneNode(Node rmNode, Node parent) {
        parent.degree = parent.degree - 1;
        lostOneChild(rmNode, parent);
        insertNode(rmNode);
        cascadingCut(parent);
    }

    /**
     * This is the function to simulate the process when a node lost its children.
     *
     * @param child the lost child
     * @param parent the node who lost children
     */
    private void lostOneChild(Node child, Node parent) {
        if (parent.degree == 0) {
            parent.leftmostChild = null;
        }

        if (parent.leftmostChild == child) {
            parent.leftmostChild = child.rightSibling;
        }

        child.leftSibling.rightSibling = child.rightSibling;
        child.rightSibling.leftSibling = child.leftSibling;
        child.parent = null;
    }

    /**
     * This is the function to implement the cascading cut operation in a max fibonacci heap.
     *
     * @param ccNode the potential node who needs to execute cascading cut
     */
    private void cascadingCut(Node ccNode) {
        if (ccNode.childCut != null) {
            if (!ccNode.childCut) {
                ccNode.childCut = true;
            } else {
                Node temp = ccNode.parent;
                removeOneNode(ccNode, temp);
            }
        }
    }

    /**
     * This function is to implement the increase key operation in a Fibonacci heap.
     *
     * @param ikNode the node who need to increase frequency
     * @param icValue the value
     */
    public void increaseKey(Node ikNode, int icValue) {

        ikNode.frequency = ikNode.frequency + icValue;

        if (ikNode.parent != null) {
            if (ikNode.frequency > ikNode.parent.frequency) {
                removeOneNode(ikNode, ikNode.parent);
            }
        } else if (ikNode.frequency > this.max.frequency) {
            this.max = ikNode;
        }

    }

    /**
     * This is the function to combine two nodes and I assume node1 has a larger frequency.
     *
     * @param node1 the node with larger frequency
     * @param node2 the node with smaller frequency
     */
    private void combineTwoNodes(Node node1, Node node2) {
        node1.degree = node1.degree +1;
        node2.parent = node1;
        node2.childCut = false;
        if (node1.degree == 1) {
            node1.leftmostChild = node2;
            node2.rightSibling = node2;
            node2.leftSibling = node2;
        } else {
            node2.leftSibling = node1.leftmostChild.leftSibling;
            node1.leftmostChild.leftSibling.rightSibling = node2;
            node2.rightSibling = node1.leftmostChild;
            node1.leftmostChild.leftSibling = node2;
        }

    }


    /**
     * This is the function to delete one node from max Fibonacci heap
     *
     * @param rmNode the node I removed
     * @return Node
     */
    public Node remove(Node rmNode) {
        if (rmNode == this.max) {
            return removeMax();
        } else {
            Node temp;
            if (rmNode.leftmostChild != null) {
                rmNode.leftmostChild.parent = null;
                rmNode.leftmostChild.childCut = null;

                for(temp = rmNode.leftmostChild.rightSibling; temp != rmNode.leftmostChild; temp = temp.rightSibling) {
                    temp.parent = null;
                    temp.childCut = null;
                }

                rmNode.leftmostChild.leftSibling.rightSibling = this.max.rightSibling;
                this.max.rightSibling.leftSibling = rmNode.leftmostChild.leftSibling;
                rmNode.leftmostChild.leftSibling = this.max;
                this.max.rightSibling = rmNode.leftmostChild;
                rmNode.leftmostChild = null;
                rmNode.degree = 0;
                rmNode.childCut = null;
            }

            if (rmNode.parent != null) {
                rmNode.parent.degree = rmNode.parent.degree -1;
                lostOneChild(rmNode, rmNode.parent);
                cascadingCut(rmNode.parent);
            }

        }

        return rmNode;
    }


    /**
     * This is the function to implement the remove max in a max Fibonacci heap
     *
     * @return removed max node
     */
    public Node removeMax() {
        if (this.max == null) {
            return null;
        } else {
            Node currentMax = this.max;
            Node maxLeftmostChild;
            if (currentMax.degree != 0) {
                maxLeftmostChild = this.max.leftmostChild;
                maxLeftmostChild.parent = null;
                maxLeftmostChild.childCut = null;

                for(maxLeftmostChild = maxLeftmostChild.rightSibling; maxLeftmostChild != this.max.leftmostChild; maxLeftmostChild = maxLeftmostChild.rightSibling) {
                    maxLeftmostChild.parent = null;
                    maxLeftmostChild.childCut = null;
                }

                this.max.leftmostChild.leftSibling.rightSibling = this.max.rightSibling;
                this.max.rightSibling.leftSibling = this.max.leftmostChild.leftSibling;
                this.max.leftmostChild.leftSibling = this.max;
                this.max.rightSibling = this.max.leftmostChild;
                this.max.leftmostChild = null;
                this.max.degree = 0;
            }

            if (this.max.rightSibling == this.max) {
                this.max = null;
            } else {
                Node temporaryRoot = this.max.rightSibling;
                this.max.leftSibling.rightSibling = this.max.rightSibling;
                this.max.rightSibling.leftSibling = this.max.leftSibling;
                this.max = null;
                maxLeftmostChild = temporaryRoot;
                HashMap<Integer, Node> keywordRecords;
                keywordRecords = new HashMap<>();
                keywordRecords.put(maxLeftmostChild.degree, maxLeftmostChild);

                Node nextRighSibling;
                for(maxLeftmostChild = maxLeftmostChild.rightSibling; maxLeftmostChild != temporaryRoot; maxLeftmostChild = nextRighSibling) {
                    int frequency;
                    Node keyword;
                    for(nextRighSibling = maxLeftmostChild.rightSibling; keywordRecords.get(maxLeftmostChild.degree) != null; keywordRecords.remove(frequency, keyword)) {
                        frequency = maxLeftmostChild.degree;
                        keyword = keywordRecords.get(maxLeftmostChild.degree);
                        if (maxLeftmostChild.frequency >= keyword.frequency) {
                            this.combineTwoNodes(maxLeftmostChild, keyword);
                        } else {
                            this.combineTwoNodes(keyword, maxLeftmostChild);
                            maxLeftmostChild = keyword;
                        }
                    }

                    keywordRecords.put(maxLeftmostChild.degree, maxLeftmostChild);
                }

                for (Integer temp : keywordRecords.keySet()) {
                    this.insertNode(keywordRecords.get(temp));
                }

            }

            currentMax.rightSibling = null;
            currentMax.leftSibling = null;
            return currentMax;
        }
    }

}
