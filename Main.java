class RBTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    class Node {
        int key, value;
        Node left, right;
        boolean color;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.color = RED;
        }
    }

    private Node root;

    public RBTree() {
        root = null;
    }

    public void insert(int key, int value) {
        root = insertNode(root, key, value);
        root.color = BLACK;
    }

    private Node insertNode(Node node, int key, int value) {
        if (node == null)
            return new Node(key, value);

        if (key < node.key)
            node.left = insertNode(node.left, key, value);
        else if (key > node.key)
            node.right = insertNode(node.right, key, value);
        else
            node.value = value;

        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        return node;
    }

    public int get(int key) {
        Node node = getNode(root, key);
        return node != null ? node.value : -1;
    }

    private Node getNode(Node node, int key) {
        while (node != null) {
            if (key < node.key)
                node = node.left;
            else if (key > node.key)
                node = node.right;
            else
                return node;
        }
        return null;
    }

    public int remove(int key) {
        int value = get(key);
        root = removeNode(root, key);
        return value;
    }

    private Node removeNode(Node node, int key) {
        if (node == null)
            return null;

        if (key < node.key)
            node.left = removeNode(node.left, key);
        else if (key > node.key)
            node.right = removeNode(node.right, key);
        else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            Node minNode = findMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = deleteMin(node.right);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null)
            return node.right;
        node.left = deleteMin(node.left);
        return node;
    }

    private boolean isRed(Node node) {
        if (node == null)
            return false;
        return node.color == RED;
    }

    private Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = node.color;
        node.color = RED;
        return temp;
    }

    private Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = node.color;
        node.color = RED;
        return temp;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null)
            return -1;
        else {
            int leftHeight = height(node.left);
            int rightHeight = height(node.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RBTree rbTree = new RBTree();

        // Wstawianie elementów
        rbTree.insert(10, 100);
        rbTree.insert(20, 200);
        rbTree.insert(5, 50);
        rbTree.insert(15, 150);
        rbTree.insert(25, 250);
        rbTree.insert(7, 70);

        // Testowanie metody get
        System.out.println("Wartosc dla klucza 20: " + rbTree.get(20));
        System.out.println("Wartosc dla klucza 5: " + rbTree.get(5));
        System.out.println("Wartosc dla klucza 15: " + rbTree.get(15));
        System.out.println("Wartosc dla klucza 30: " + rbTree.get(30));

        // Testowanie metody remove
        System.out.println("Removed wartosc dla klucza 20: " + rbTree.remove(20));
        System.out.println("Removed wartosc dla klucza 5: " + rbTree.remove(5));

        // Testowanie metody height
        System.out.println("Wysokosc drzewa: " + rbTree.height());
    }
}
