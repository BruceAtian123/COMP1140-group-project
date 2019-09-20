package comp1140.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


// Whole class written by Yihan Zhou
// tree structure from online whith addChild and addChildren function
// url: https://www.javagists.com/java-tree-data-structure
public class Node<T> {
    private boolean isMaxPlayer;
    // only node in layer 2 need to store placement
    private String placement;

    // data used to store score
    private T data = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node() {

    }


    public Node(T data) {
        this.data = data;
    }

    public Node(T data, String placement) {
        this.data = data;
        this.placement = placement;
    }

    // url: https://www.javagists.com/java-tree-data-structure
    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    // url: https://www.javagists.com/java-tree-data-structure
    public void addChildren(List<Node<T>> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    //---------------------------------------- test start, will be deleted
    private static Node<Integer> minimaxTree2() {
        Node<Integer> root = new Node<Integer>(1234, "abc");
        Node<Integer> node1 = root.addChild(new Node<Integer>(1, "a"));
        Node<Integer> node11 = node1.addChild(new Node<Integer>(2, "b"));
        Node<Integer> node111 = node11.addChild(new Node<Integer>(3, "e"));
        Node<Integer> node112 = node11.addChild(new Node<Integer>(7, "f"));
        Node<Integer> node12 = node1.addChild(new Node<Integer>(9, "c"));
        Node<Integer> node121 = node12.addChild(new Node<Integer>(2, "g"));
        Node<Integer> node2 = root.addChild(new Node<Integer>(5, "d"));
        Node<Integer> node21 = node2.addChild(new Node<Integer>(1, "h"));
        Node<Integer> node22 = node2.addChild(new Node<Integer>(0, "i"));
        return root;
    }


    public static void main(String[] args) {
        Node<Integer> root2 = minimaxTree2();
        printTree(root2, " ");

        System.out.println("true sample values: " + minimax(root2, root2.depth(), false).data);
        System.out.println("true sample values: " + minimax(root2, root2.depth(), false).getPlacement()); // "a" or "d"
        System.out.println("sample values ab: " + abPruning(root2, root2.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false).getData()); // "a" or "d"        System.out.println("sample values: " + minimax(root2, root.depth(), false).getPlacement()); // "a" or "d"
        System.out.println("sample values ab: " + abPruning(root2, root2.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false).getPlacement()); // "a" or "d"

    }


    static <T> void printTree(Node<T> node, String appender) {
        System.out.println(appender + node.getData());
        node.getChildren().forEach(each -> printTree(each, appender + appender));
        System.out.println();
    }

    //---------------------------------------- test end

    private LinkedList<Integer> depthList = new LinkedList<>();

    // depth of the node(tree) include itself
    public int depth() {
        if (this.children.size() <= 0) {
            return 1;
        }
        for (Node<T> child : children) {
            depthList.add(child.depth() + 1);
        }

        return Collections.max(depthList);
    }



    // minimax for node which can get placement from
    public static Node<Integer> minimax(Node<Integer> node, int depth, boolean isMaxPlayer) {
        if (depth - 1 == 0 || node.getChildren().size() <= 0) return node;

        //return the heuristic value of node
        Node<Integer> bestValue = new Node<Integer>();
        if (isMaxPlayer) {
            bestValue.setData(Integer.MIN_VALUE);
            for (Node<Integer> child : node.children) {
                Node<Integer> v = minimax(child, depth - 1, false);
                bestValue.data = bestValue.data > v.data ? bestValue.data : v.data;
                bestValue.placement = bestValue.data > v.data ? bestValue.placement : child.placement;
            }

        } else {
            bestValue.setData(Integer.MAX_VALUE);
            for (Node<Integer> child : node.children) {
                Node<Integer> v = minimax(child, depth - 1, true);
                bestValue.data = bestValue.data < v.data ? bestValue.data : v.data;
                bestValue.placement = bestValue.data < v.data ? bestValue.placement : child.placement;
            }
        }

        return bestValue;
    }


    // return node data is the scoring to the board to the bottom(layer at the end of the tree) and placement is the next step(layer2)
    public static Node abPruning(Node<Integer> node, int depth, int alpha, int beta, boolean isMaxPlayer) {
        if (depth - 1 == 0 || node.getChildren().size() <= 0) return node;

        //return the heuristic value of node
        Node<Integer> bestValue = new Node<Integer>();
        if (isMaxPlayer) {
            bestValue.setData(Integer.MIN_VALUE);
            for (Node<Integer> child : node.children) {
                Node<Integer> val = abPruning(child, depth - 1, alpha, beta, false);
                bestValue.data = bestValue.data > val.data ? bestValue.data : val.data;
                bestValue.placement = bestValue.data > val.data ? bestValue.placement : child.placement;
                alpha = Math.max(alpha, bestValue.data);
                if (alpha >= beta) break;
            }
            return bestValue;
        } else {
            bestValue.setData(Integer.MAX_VALUE);
            for (Node<Integer> child : node.children) {
                Node<Integer> val = abPruning(child, depth - 1, alpha, beta, true);
                bestValue.data = bestValue.data < val.data ? bestValue.data : val.data;
                bestValue.placement = bestValue.data < val.data ? bestValue.placement : child.placement;
                beta = Math.min(beta, bestValue.data);
                if (alpha >= beta) break;
            }
            return bestValue;
        }
    }



    public boolean equals(Node other){
        boolean b1,b2;
        b1 = other.data == this.data;
        b2 = other.placement.equals(this.placement);
        return b1 && b2;
    }
}



