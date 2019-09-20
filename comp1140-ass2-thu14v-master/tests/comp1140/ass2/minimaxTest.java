package comp1140.ass2;

import org.junit.Test;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class minimaxTest {

    private static Node<Integer> minimaxTree() {
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

    }    private static Node<Integer> minimaxTree2() {
        Node<Integer> root2 = new Node<Integer>(1234, "abc");
        Node<Integer> node2_1 = root2.addChild(new Node<Integer>(1, "aaa"));
        Node<Integer> node2_11 = node2_1.addChild(new Node<Integer>(2, "b"));
        Node<Integer> node2_111 = node2_11.addChild(new Node<Integer>(3, "e"));
        Node<Integer> node2_1111 = node2_111.addChild(new Node<Integer>(8, "l"));
        Node<Integer> node2_1112 = node2_111.addChild(new Node<Integer>(2, "m"));
        Node<Integer> node2_112 = node2_11.addChild(new Node<Integer>(7, "f"));
        Node<Integer> node2_12 = node2_1.addChild(new Node<Integer>(9, "c"));
        Node<Integer> node2_121 = node2_12.addChild(new Node<Integer>(2, "g"));
        Node<Integer> node2_1211 = node2_121.addChild(new Node<Integer>(6, "n"));
        Node<Integer> node2_1212 = node2_121.addChild(new Node<Integer>(8, "ab"));
        Node<Integer> node2_1213 = node2_121.addChild(new Node<Integer>(10, "ac"));
        Node<Integer> node2_2 = root2.addChild(new Node<Integer>(5, "ddd"));
        Node<Integer> node2_21 = node2_2.addChild(new Node<Integer>(1, "h"));
        Node<Integer> node2_212 = node2_21.addChild(new Node<Integer>(6, "ad"));
        Node<Integer> node2_213 = node2_21.addChild(new Node<Integer>(3, "ae"));
        Node<Integer> node2_211 = node2_21.addChild(new Node<Integer>(19,"j"));
        Node<Integer> node2_22 = node2_2.addChild(new Node<Integer>(0, "i"));
        Node<Integer> node2_222 = node2_22.addChild(new Node<Integer>(5, "i"));
        Node<Integer> node2_221 = node2_22.addChild(new Node<Integer>(4, "k"));
        Node<Integer> node2_2211 = node2_221.addChild(new Node<Integer>(14, "li"));
        return root2;

    }

    @Test
    public void TestRightPossibleresult(){
        Node<Integer> root = minimaxTree();

        List<Node<Integer>> possibles = root.getChildren();
        LinkedList<String> checkList = new LinkedList<>();
        for (Node<Integer> possible : possibles) {
            checkList.add(possible.getPlacement());
        }
        String check = Node.minimax(root,root.depth(),true).getPlacement();
        String check2 = Node.minimax(root,root.depth(),false).getPlacement();
        assertTrue(checkList.contains(check));
        assertTrue(checkList.contains(check2));

        Node<Integer> root2 = minimaxTree2();
        List<Node<Integer>> possibles2 = root.getChildren();
        LinkedList<String> checkList2 = new LinkedList<>();
        for (Node<Integer> possible : possibles2) {
            checkList2.add(possible.getPlacement());
        }
        String check3 = Node.minimax(root,root.depth(),true).getPlacement();
        String check4 = Node.minimax(root,root.depth(),false).getPlacement();
        assertTrue(checkList.contains(check3));
        assertTrue(checkList.contains(check4));
    }

        @Test
    public void TestMax(){
        Node<Integer> root = minimaxTree();
        testPlacementMinimax(root,root.depth(),true,"a");

        Node<Integer> root2 = minimaxTree2();
        testPlacementMinimax(root2,root2.depth(),true,"ddd");
    }

    @Test
    public void TestMin(){
        Node<Integer> root = minimaxTree();
        testPlacementMinimax(root,root.depth(),false,"d");

        Node<Integer> root2 = minimaxTree2();
        testPlacementMinimax(root2,root2.depth(),false,"ddd");
    }

    @Test
    public void TestminimaxNabpruning(){
        Node<Integer> root = minimaxTree();
        String minimax1 = Node.minimax(root,root.depth(),true).getPlacement();
        String abpruning1 = Node.abPruning(root, root.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, true).getPlacement();
        assertTrue(minimax1.equals(abpruning1));


        String minimax2 = Node.minimax(root,root.depth(),false).getPlacement();
        String abpruning2 = Node.abPruning(root,root.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false).getPlacement();
        assertTrue(minimax2.equals(abpruning2));


        Node<Integer> root2 = minimaxTree2();
        String minimax3 = Node.minimax(root2,root2.depth(),true).getPlacement();
        String abpruning3 = Node.abPruning(root2, root2.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, true).getPlacement();
        assertTrue(minimax3.equals(abpruning3));


        String minimax4 = Node.minimax(root2,root2.depth(),false).getPlacement();
        String abpruning4 = Node.abPruning(root2,root2.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, false).getPlacement();
        assertTrue(minimax4.equals(abpruning4));


    }

    private void testPlacementMinimax(Node<Integer> node, int depth, boolean isMaxPlayer, String expected) {
        Node<Integer> result = Node.minimax(node,depth,isMaxPlayer);
        assertEquals("Node.minimax(node =" + node + ", depth=\""+ depth +"\", isMaxPlayer=\"" + isMaxPlayer + "\") returned incorrect result:", expected, result.getPlacement());
    }

}
