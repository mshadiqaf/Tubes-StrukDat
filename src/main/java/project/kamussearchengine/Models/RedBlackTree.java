package project.kamussearchengine.Models;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    private Node root;
    private final Node TNULL;

    public RedBlackTree() {
        TNULL = new Node();
        root = TNULL;
    }

    public void insert(String word, String wordTranslated, String description, String descriptionTranslated, Runnable gimmick) {

        if (findHelper(root, word) != TNULL) {
            System.out.println("Node dengan kata '" + word + "' sudah ada dalam tree.");
            return;
        }

        Node node = new Node(word, wordTranslated, description, descriptionTranslated);
        node.left = TNULL;
        node.right = TNULL;
        node.red = true;
        node.gimmick = gimmick;

        Node y = null;
        Node x = root;

        while (x != TNULL) {
            y = x;
            if (node.word.compareTo(x.word) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.word.compareTo(y.word) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.red = false;
            return;
        }

        if (node.parent.parent == null) return;

        fixInsert(node);
    }


    private void fixInsert(Node k) {
        Node u;
        while (k.parent != null && k.parent.red) {
            if (k.parent == k.parent.parent.right) {
                u = k.parent.parent.left;
                if (u.red) {
                    u.red = false;
                    k.parent.red = false;
                    k.parent.parent.red = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.red = false;
                    k.parent.parent.red = true;
                    leftRotate(k.parent.parent);
                }
            } else {
                u = k.parent.parent.right;
                if (u.red) {
                    u.red = false;
                    k.parent.red = false;
                    k.parent.parent.red = true;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.red = false;
                    k.parent.parent.red = true;
                    rightRotate(k.parent.parent);
                }
            }
            if (k == root) break;
        }
        root.red = false;
    }

    public void deleteNode(String key) {
        deleteNodeHelper(root, key);
    }

    private void deleteNodeHelper(Node node, String key) {
        key = key.toLowerCase();
        Node z = TNULL;
        Node x, y;

        while (node != TNULL) {
            if (key.equals(node.word.toLowerCase())) {
                z = node;
                break;
            }
            node = key.compareTo(node.word.toLowerCase()) < 0 ? node.left : node.right;
        }

        if (z == TNULL) {
            System.out.println("Node dengan kata '" + key + "' tidak ditemukan!");
            return;
        }

        y = z;
        boolean yOriginalColor = y.red;
        if (z.left == TNULL) {
            x = z.right;
            redBlackTransplant(z, z.right);
        } else if (z.right == TNULL) {
            x = z.left;
            redBlackTransplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.red;
            x = y.right;
            if (y.parent != z) {
                redBlackTransplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            redBlackTransplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.red = z.red;
        }

        if (!yOriginalColor) {
            fixDelete(x);
        }
    }

    private void fixDelete(Node x) {
        Node s;
        while (x != root && !x.red) {
            if (x == x.parent.left) {
                s = x.parent.right;
                if (s.red) {
                    s.red = false;
                    x.parent.red = true;
                    leftRotate(x.parent);
                    s = x.parent.right;
                }
                if (!s.left.red && !s.right.red) {
                    s.red = true;
                    x = x.parent;
                } else {
                    if (!s.right.red) {
                        s.left.red = false;
                        s.red = true;
                        rightRotate(s);
                        s = x.parent.right;
                    }
                    s.red = x.parent.red;
                    x.parent.red = false;
                    s.right.red = false;
                    leftRotate(x.parent);
                    x = root;
                }
            } else {
                s = x.parent.left;
                if (s.red) {
                    s.red = false;
                    x.parent.red = true;
                    rightRotate(x.parent);
                    s = x.parent.left;
                }
                if (!s.left.red && !s.right.red) {
                    s.red = true;
                    x = x.parent;
                } else {
                    if (!s.left.red) {
                        s.right.red = false;
                        s.red = true;
                        leftRotate(s);
                        s = x.parent.left;
                    }
                    s.red = x.parent.red;
                    x.parent.red = false;
                    s.left.red = false;
                    rightRotate(x.parent);
                    x = root;
                }
            }
        }
        x.red = false;
    }

    private Node minimum(Node node) {
        while (node.left != TNULL) {
            node = node.left;
        }
        return node;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != TNULL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != TNULL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public Node find(String word) {
        Node node = findHelper(root, word.toLowerCase());
        return node;
    }

    private Node findHelper(Node node, String word) {
        if (node == TNULL) return node;

        String nodeWord = (node.word != null) ? node.word.toLowerCase() : "";
        if (word.equals(nodeWord)) {
            return node;
        }
        if (word.compareTo(nodeWord) < 0) {
            return findHelper(node.left, word);
        }
        return findHelper(node.right, word);
    }

    public boolean isEmpty() {
        return root == TNULL;
    }

    public void clear() {
        root = TNULL;
    }

    private void redBlackTransplant(Node u, Node v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    public void printTree() {
        printTreeHelper(root, "", true);
    }

    private void printTreeHelper(Node node, String indent, boolean last) {
        if (node != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R---- ");
                indent += "   ";
            } else {
                System.out.print("L---- ");
                indent += "|  ";
            }

            String redColor = "\u001B[31m";
            String blackColor = "\u001B[30m";
            String resetColor = "\u001B[0m";

            String color = (node.red) ? redColor : blackColor;
            System.out.println(color + node.word + resetColor);

            printTreeHelper(node.left, indent, false);
            printTreeHelper(node.right, indent, true);
        }
    }

    public void preOrder() {
        preOrderHelper(this.root);
        System.out.println();
    }

    public void inOrder() {
        inOrderHelper(this.root);
        System.out.println();
    }

    public void postOrder() {
        postOrderHelper(this.root);
        System.out.println();
    }

    private void preOrderHelper(Node node) {
        if (node != TNULL) {
            System.out.print(node.word + " ");
            preOrderHelper(node.left);
            preOrderHelper(node.right);
        }
    }

    private void inOrderHelper(Node node) {
        if (node != TNULL) {
            inOrderHelper(node.left);
            System.out.print(node.word + " ");
            inOrderHelper(node.right);
        }
    }

    private void postOrderHelper(Node node) {
        if (node != TNULL) {
            postOrderHelper(node.left);
            postOrderHelper(node.right);
            System.out.print(node.word + " ");
        }
    }

    public List<Node> getData() {
        List<Node> dataList = new ArrayList<>();
        collectData(root, dataList);
        return dataList;
    }

    private void collectData(Node node, List<Node> dataList) {
        if (node != TNULL) {
            collectData(node.left, dataList);
            dataList.add(node);
            collectData(node.right, dataList);
        }
    }

}
