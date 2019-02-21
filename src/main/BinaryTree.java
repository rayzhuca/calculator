package main;

/**
 * <h1>Makes a binary tree for ordering calculations</h1>
 * <p>
 * <b>Note:</b> This class is only intended to be used by Separator
 *
 * @author Ray
 * @since 2019-02-10
 */
public class BinaryTree {

    Node root;

    /**
     * Adds a node to the binary tree
     * 
     * @param key the position of the node
     * @param name the name of the node
     */
    protected void add(int key, String name) {

        Node node = new Node(key, name);

        if (root == null) {
            root = node;
        } else {
            
            Node target = root;
            Node parent;

            while (true) {
                parent = target;
                if (target.key > key) {
                    target = target.leftChild;
                    if (target == null) {
                        parent.leftChild = node;
                    }
                } else {
                    target = target.rightChild;
                    if (target == null) {
                        parent.rightChild = node;
                    }
                }
            }
        }
    }

    /**
     * Finds a node of the binary tree
     * 
     * @param key node key
     * @return the node
     */
    protected Node find(int key) {
        Node target = root;

        while (key != target.key) {
            if (key < target.key) {
                target = target.leftChild;
            } else {
                target = target.rightChild;
            }
        }
        return target != null ? target : null;
    }

    /**
     * Searches through the binary tree in in order traversal starting from the root
     */
    protected void run() {
        if (root == null) {
            return;
        }
        run(root.leftChild);
        run(root.rightChild);
    }

    /**
     * Searches through the binary tree in in order traversal
     * 
     * @param node The node to be searched
     */
    protected void run(Node node) {
        if (root == null) {
            return;
        }
        run(root.leftChild);
        run(root.rightChild);
    }

    /**
     * The Node represented for the binary tree
     */
    protected class Node {
        private int key;
        private String name;
        private Node leftChild, rightChild;

        /**
         * The constuctor of the Node class
         * 
         * @param key the position of the node
         * @param name the name of the node
         */
        protected Node(int key, String name) {
            this.key = key;
            this.name = name;
        }

        /**
         * Gets the key of the node
         * 
         * @return the key of the node
         */
        protected int getKey() {
            return key;
        }

        /**
         * Gets the name of the node
         * 
         * @return the name of the node
         */
        protected String getName() {
            return name;
        }

        /**
         * Gets the left child of the node
         * 
         * @return the left child of the node
         */
        protected Node getLeftChild() {
            return leftChild;
        }

        /**
         * Gets the right child of the node
         * 
         * @return the right child of the node
         */
        protected Node getRightChild() {
            return rightChild;
        }

        /**
         * Sets the left child of the node
         * 
         * @param child the left child of the node to be set
         */
        protected void setLeftChild(Node child) {
            leftChild = child;
        }

        /**
         * Sets the right child of the node
         * 
         * @param child the right child of the node to be set
         */
        protected void setRightChild(Node child) {
            rightChild = child;
        }
    }
}