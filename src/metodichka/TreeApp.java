package metodichka;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Stack{
    private int maxSize;
    private Node[] stack;
    private int top;

    public Stack(int size){
        this.maxSize = size;
        this.stack = new Node[this.maxSize];
        this.top = -1;
    }

    public void push(Node n){
        this.stack[++this.top] = n;
    }

    public Node pop(){
        return this.stack[this.top--];
    }

    public Node peek(){
        return this.stack[this.top];
    }

    public boolean isEmpty(){
        return (this.top == -1);
    }

    public boolean isFull(){
        return (this.top == this.maxSize-1);
    }
}

class Person{
    public String name;
    public int id;
    public int age;

    public Person(){

    }

    public Person(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }
}

class Node{
    public Person person;
    public Node leftChild;
    public Node rightChild;

    public void display(){
        System.out.println("Name: "+person.name+", age: "+person.age);
    }
}

class Tree{

    private Node root;

    public Node find(int key){
        Node current = root;
        while (current.person.id != key) {
            if (key < current.person.id){
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }

            if (current == null){
                return null;
            }
        }
        return current;
    }

    public void insert(Person person){
        Node node = new Node();
        node.person = person;
        if (root == null){
            root = node;
        } else {
            Node current = root;
            Node parent;
            while (true) {
                parent = current;
                if (person.id < current.person.id){
                    current = current.leftChild;
                    if (current == null){
                        parent.leftChild = node;
                        return;
                    }
                } else {
                    current = current.rightChild;
                    if (current == null){
                        parent.rightChild = node;
                        return;
                    }
                }
            }
        }
    }

    public boolean delete(int id){
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;

        while (current.person.id != id) {
            parent = current;
            if (id < current.person.id){
                isLeftChild = true;
                current = current.leftChild;
            } else {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null){
                return false;
            }
        }

        // Если узел не имеет потомков

        if (current.leftChild == null && current.rightChild == null){
            if (current == null){
                root = null;
            } else if(isLeftChild){
                parent.leftChild = null;
            }else{
                parent.rightChild = null;
            }
        }
        // Если нет правого потомка
        else if(current.rightChild == null){
            if (current == null){
                root = current.leftChild;
            } else if(isLeftChild){
                parent.leftChild = current.leftChild;
            }else{
                parent.rightChild = current.leftChild;
            }
        }
        // Если нет левого потомка
        else if(current.leftChild == null){
            if (current == null){
                root = current.rightChild;
            } else if(isLeftChild){
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else {
            Node successor = getSuccesor(current);
            if (current == root){
                root = successor;
            }else if(isLeftChild){
                parent.leftChild = successor;
            }else {
                parent.rightChild = successor;
            }
            successor.leftChild = current.leftChild;
        }
        return true;
    }

    public Node getSuccesor(Node node){
        Node successorParent = node;
        Node successor = node;
        Node current = node.rightChild;

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if (successor != node.rightChild){
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = node.rightChild;
        }

        return successor;
    }

    public void traverse(int traverseType){
        switch(traverseType){
            case 1:System.out.println("Preorder traversal");
                preOrder(root);
                break;
        }
    }

    private void preOrder(Node rootNode){
        if(rootNode != null){
            rootNode.display();
            preOrder(rootNode.leftChild);
            preOrder(rootNode.rightChild);
        }
    }

    private void postOrder(Node rootNode){
        if(rootNode != null){
            postOrder(rootNode.leftChild);
            postOrder(rootNode.rightChild);
            rootNode.display();
        }
    }

    private void inOrder(Node rootNode){
        if(rootNode != null){
            inOrder(rootNode.leftChild);
            rootNode.display();
            inOrder(rootNode.rightChild);
        }
    }

    public void displayTree(){
        Stack stack = new Stack(100);
        stack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;

        while (!isRowEmpty) {
            Stack localStack = new Stack(100);
            isRowEmpty = true;
            for(int i=0;i<nBlanks;i++){
                System.out.print(" ");
            }
            while (!stack.isEmpty()) {
                Node temp = stack.pop();
                if (temp != null){
                    temp.display();
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if(temp.leftChild != null || temp.rightChild != null){
                        isRowEmpty = false;
                    }
                }else{
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j < nBlanks * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println(" ");
            nBlanks = nBlanks / 2;
            while (!localStack.isEmpty()) {
                stack.push(localStack.pop());
            }
            System.out.println("......................................................");
        }
    }

}

public class TreeApp {


    public static void main(String[] args) throws IOException {
        int value;
        Tree theTree = new Tree();
        theTree.insert(new Person());
        theTree.insert(new Person());
        theTree.insert(new Person());
        theTree.insert(new Person());
        theTree.insert(new Person());
        theTree.insert(new Person());
        theTree.insert(new Person());

        while(true){
            System.out.print("Enter first letter of show, ");
            System.out.print("insert, find, delete, or traverse: ");
            int choice = getChar();
            switch(choice){
                case 's':
                    theTree.displayTree();
                    break;
                case 'i':
                    System.out.print("Enter value to insert: ");
                    value = getInt();
                    theTree.insert(new Person());
                    break;
                case 'f':
                    System.out.print("Enter value to find: ");
                    value = getInt();
                    Node found = theTree.find(value);
                    if(found != null){
                        System.out.print("Found: ");
                    }
                    found.display();
                    System.out.print("\n");
                    break;
            }
        }
    }

    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }

    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }

}


