package AVLTree;

public class AVLTree<T extends Comparable<T>> {

    public class Node {
        T key; // the key
        int height; // the height of subtree
        Node left; // the left node
        Node right; // the right node

        public Node(T key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return "" + key;
        }
    }

    private Node root;

    // return height of subtree or -1 for an empty tree
    private int height(Node node) {
        return node != null ? node.height : -1;
    }

    // set node height to the max height of child plus 1
    private void updateHeight(Node node) {
        int leftChildHeight = height(node.left);
        int rightChildHeight = height(node.right);
        node.height = Math.max(leftChildHeight, rightChildHeight) + 1;
    }

    // balance factor = height of right subtree - height of left subtree
    private int balanceFactor(Node node) {
        return height(node.right) - height(node.left);
    }

    // right rotation
    private Node rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        updateHeight(node);
        updateHeight(leftChild);
        return leftChild;
    }

    // left rotation
    private Node rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        updateHeight(node);
        updateHeight(rightChild);
        return rightChild;
    }

    // re-balancing the nodes
    private Node rebalance(Node node) {
        int balanceFactor = balanceFactor(node);
        // left heavy
        if (balanceFactor < -1) {
            if (balanceFactor(node.left) <= 0) {
                // rotate right
                node = rotateRight(node);
            } else {
                // rotate left-right
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }
        // right heavy
        if (balanceFactor > 1) {
            if (balanceFactor(node.right) >= 0) {
                // rotate left
                node = rotateLeft(node);
            } else {
                // rotate right-left
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }
        return node;
    }

    // insertion
    public void insert(T key) {
        root = insert(key, root);
    }

    private Node insert(T key, Node node) {
        // if no node at the current position, create a new node
        if (node == null) {
            node = new Node(key);
        }
        // otherwise, transverse the tree depending on the value of the key
        else if (key.compareTo(node.key) < 0){
            node.left = insert(key, node.left);
        } else if (key.compareTo(node.key) > 0){
            node.right = insert(key, node.right);
        } else {
            throw new RuntimeException("The node with key " + key + " is already existed.");
        }
        updateHeight(node);
        return rebalance(node);
    }

    public void delete(T key){
        root = delete(key, root);
    }

    private Node delete(T key, Node node){
        // if no node at the current position, stop the recursion
        if(node == null){
            return null;
        }
        // transverse the tree depending on the value of the key
        if(key.compareTo(node.key) < 0){
            node.left = delete(key, node.left);
        } else if (key.compareTo(node.key) > 0){
            node.right = delete(key, node.right);
        }
        // node to be deleted
        // if the node has no child, just delete the node
        else if(node.left == null && node.right == null){
            node = null;
        }
        // if the node has one child, replace the node with its child
        else if(node.left == null){
            node = node.right;
        } else if(node.right == null){
            node = node.left;
        }
        // if the node has two child, replace it with the left most leaf node in the right subtree
        else{
            Node successor = findMinimum(node.right);
            node.key = successor.key;
            node.right = delete(successor.key, node.right);
        }
        return node;
    }

    // helper to find the minimum node
    private Node findMinimum(Node node){
        if(node.left != null){
            node = node.left;
        }
        return node;
    }

    // check if the tree contains certain key
    public boolean contains(T key){
        return contains(key, root);
    }

    private boolean contains(T key, Node node){
        if(node == null) return false;
        if(key.equals(node.key)) return true;
        return key.compareTo(node.key) < 0 ? contains(key, node.left) : contains(key, node.right);
    }

    // printing methods
    private void printInOrder(Node node){
        if(node == null) return;
        printInOrder(node.left);
        System.out.print(node.key + " ");
        printInOrder(node.right);
    }

    private void printPreOrder(Node node){
        if(node == null) return;
        System.out.print(node.key + " ");
        printPreOrder(node.left);
        printPreOrder(node.right);
    }

    private void printPostOrder(Node node){
        if(node == null) return;
        printPostOrder(node.left);
        printPostOrder(node.right);
        System.out.print(node.key + " ");
    }

    public void print(){
        System.out.println("In order:");
        printInOrder(root);
        System.out.println("\nPre order:");
        printPreOrder(root);
        System.out.println("\nPost order:");
        printPostOrder(root);
    }
}
