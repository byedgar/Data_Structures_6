import java.util.Stack;

public class TreeImpl implements Tree {

    private Node root;
    private int size;
    private int level;

    public void setLevel(int level) {
        this.level = level;
    }

    private static class NodeAndParent {
        Node current;
        Node parent;

        public static NodeAndParent create(Node parent, Node current) {
            NodeAndParent nodeAndParent = new NodeAndParent();
            nodeAndParent.parent = parent;
            nodeAndParent.current = current;
            return nodeAndParent;
        }
    }


    @Override
    public void add(int value) {
        Node newNode = new Node(value);

        if (isEmpty()) {
            root = newNode;
            size++;
            level++;
            return;
        }

        NodeAndParent nodeAndParent = doFind(value);
        nodeAndParent.parent.addChild(newNode);
        size++;
    }

    @Override
    public boolean remove(int value) {
        NodeAndParent nodeAndParent = doFind(value);
        Node current = nodeAndParent.current;
        Node parent = nodeAndParent.parent;

        if (current == null) {
            return false;
        }

        if (current.isLeaf()) {
            if (root == current) {
                root = null;
            } else {
                parent.removeChild(current);
            }
        } else if (current.hasOnlyOneChild()) {
            Node child = current.getLeftChild() != null ? current.getLeftChild() : current.getRightChild();
            if (current == root) {
                root = child;
            } else if (parent.getLeftChild() == current) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        } else {
            Node successor = getSuccessor(current);
            Node successorParent = doFind(successor.getValue()).parent;

            if (successor != current.getRightChild()) {
                successorParent.setLeftChild(successor.getRightChild());
                successor.setRightChild(current.getRightChild());
            }

            if (current == root) {
                root = successor;
            } else if (parent.getLeftChild() == current) {
                successor.setLeftChild(current.getLeftChild());
                parent.setLeftChild(successor);
            } else {
                successor.setLeftChild(current.getLeftChild());
                parent.setRightChild(successor);
            }
        }
        size--;

        return true;
    }

    private Node getSuccessor(Node node) {
        Node successorParent = null;
        Node successor = null;
        Node current = node.getRightChild();

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }

        return successor;
    }

    @Override
    public boolean find(int value) {
        NodeAndParent nodeAndParent = doFind(value);
        return nodeAndParent.current != null;
    }

    private NodeAndParent doFind(int value) {
        Node current = root;
        Node previous = null;

        while (current != null) {
            if (current.getValue() == value) {
                break;
            }

            previous = current;
            current = current.getChild(value);
        }

        return NodeAndParent.create(previous, current);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void traverse(TraverseMode mode) {
        switch (mode) {
            case IN_ORDER:
                inOrder(root);
                break;
            case PRE_ORDER:
                preOrder(root);
                break;
            case POST_ORDER:
                postOrder(root);
                break;
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }

    private void postOrder(Node root) {
        if (root != null) {
            preOrder(root.getLeftChild());
            preOrder(root.getRightChild());
            root.display();
        }
    }

    private void preOrder(Node root) {
        if (root != null) {
            root.display();
            preOrder(root.getLeftChild());
            preOrder(root.getRightChild());
        }
    }

    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.getLeftChild());
            root.display();
            inOrder(root.getRightChild());
        }
    }

    public void displayTree() {
        Stack<Node> globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;
        System.out.println("................................................................");

        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>();

            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (!globalStack.isEmpty()) {
                Node tempNode = globalStack.pop();
                if (tempNode != null) {
                    System.out.print(tempNode.getValue());
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            }

            System.out.println();

            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println("................................................................");
    }

    @Override
    public void displayLevel() {
        int level =0;
        Stack<Node> globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;

        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>();

            isRowEmpty = true;
            level++;
            while (!globalStack.isEmpty()) {
                Node tempNode = globalStack.pop();
                if (tempNode != null) {
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                }

            }


            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println("Tree level: " + level);

    }
    @Override
    public int getLevel() {
        int level =0;
        Stack<Node> globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;

        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>();

            isRowEmpty = true;
            level++;
            while (!globalStack.isEmpty()) {
                Node tempNode = globalStack.pop();
                if (tempNode != null) {
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                }

            }


            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        return level;
    }

}