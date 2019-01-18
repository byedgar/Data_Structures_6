public class Node {

    private final int value;

    private Node leftChild;
    private Node rightChild;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public boolean isShouldLeft(int value) {
        return value < this.value;
    }

    public Node getChild(int value) {
        return isShouldLeft(value) ? getLeftChild() : getRightChild();
    }

    void addChild(Node child) {
        if (isShouldLeft(child.getValue())) {
            setLeftChild(child);
        }
        else {
            setRightChild(child);
        }
    }

    public void display() {
        System.out.println(value);
    }

    boolean isLeaf() {
        return getLeftChild() == null && getRightChild() == null;
    }
    boolean hasOnlyOneChild() {
        return getLeftChild() == null ^ getRightChild() == null;
    }

    void removeChild(Node child) {
        if (leftChild == child) {
            leftChild = null;
        }
        else {
            rightChild = null;
        }
    }
}