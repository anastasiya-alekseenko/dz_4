class RedBlackTree {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    class Node {
        int data;
        Node left, right, parent;
        boolean color;

        Node(int data) {
            this.data = data;
            this.color = RED;
        }
    }

    private Node root;

    public void insert(int data) {
        Node newNode = new Node(data);
        root = bstInsert(root, newNode);
        fixViolation(newNode);
    }

    private Node bstInsert(Node root, Node pt) {
        if (root == null) return pt;

        if (pt.data < root.data) {
            root.left = bstInsert(root.left, pt);
            root.left.parent = root;
        } else if (pt.data > root.data) {
            root.right = bstInsert(root.right, pt);
            root.right.parent = root;
        }

        return root;
    }

    private void fixViolation(Node pt) {
        Node parentPt = null;
        Node grandParentPt = null;

        while (pt != root && pt.color != BLACK && pt.parent.color == RED) {
            parentPt = pt.parent;
            grandParentPt = pt.parent.parent;

            if (parentPt == grandParentPt.left) {
                Node unclePt = grandParentPt.right;

                if (unclePt != null && unclePt.color == RED) {
                    grandParentPt.color = RED;
                    parentPt.color = BLACK;
                    unclePt.color = BLACK;
                    pt = grandParentPt;
                } else {
                    if (pt == parentPt.right) {
                        leftRotate(parentPt);
                        pt = parentPt;
                        parentPt = pt.parent;
                    }

                    rightRotate(grandParentPt);
                    boolean t = parentPt.color;
                    parentPt.color = grandParentPt.color;
                    grandParentPt.color = t;
                    pt = parentPt;
                }
            } else {
                Node unclePt = grandParentPt.left;

                if (unclePt != null && unclePt.color == RED) {
                    grandParentPt.color = RED;
                    parentPt.color = BLACK;
                    unclePt.color = BLACK;
                    pt = grandParentPt;
                } else {
                    if (pt == parentPt.left) {
                        rightRotate(parentPt);
                        pt = parentPt;
                        parentPt = pt.parent;
                    }

                    leftRotate(grandParentPt);
                    boolean t = parentPt.color;
                    parentPt.color = grandParentPt.color;
                    grandParentPt.color = t;
                    pt = parentPt;
                }
            }
        }

        root.color = BLACK;
    }

    private void leftRotate(Node pt) {
        Node ptRight = pt.right;

        pt.right = ptRight.left;

        if (pt.right != null) pt.right.parent = pt;

        ptRight.parent = pt.parent;

        if (pt.parent == null) root = ptRight;
        else if (pt == pt.parent.left) pt.parent.left = ptRight;
        else pt.parent.right = ptRight;

        ptRight.left = pt;
        pt.parent = ptRight;
    }

    private void rightRotate(Node pt) {
        Node ptLeft = pt.left;

        pt.left = ptLeft.right;

        if (pt.left != null) pt.left.parent = pt;

        ptLeft.parent = pt.parent;

        if (pt.parent == null) root = ptLeft;
        else if (pt == pt.parent.left) pt.parent.left = ptLeft;
        else pt.parent.right = ptLeft;

        ptLeft.right = pt;
        pt.parent = ptLeft;
    }

    public void inOrderTraversal() {
        inOrderHelper(root);
    }

    private void inOrderHelper(Node root) {
        if (root != null) {
            inOrderHelper(root.left);
            System.out.print(root.data + " ");
            inOrderHelper(root.right);
        }
    }

    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(5);
        tree.insert(1);

        tree.inOrderTraversal();
    }
}
