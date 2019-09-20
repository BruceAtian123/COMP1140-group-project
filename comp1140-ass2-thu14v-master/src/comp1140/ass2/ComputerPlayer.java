package comp1140.ass2;


import java.util.*;

// Whole class written by Yihan Zhou
public class ComputerPlayer {
    public static int score(int size, String board) {
        // return score is player2 score - player1 score
        int[] scoreArr = Board.getScore(size, board);
        return scoreArr[1] - scoreArr[0]; // player2 score - player1 score

    }


    public static String makeMove1(int size, String board) {
        // FIXME Task 11: generate a valid move
        //Task 11: difficulty 2
        // greedy bot

        // player 2 is the bot so isPlayer1 input false

        ArrayList<String> moveList = BloomsGame.allValidMoves(size, board, false);
        // score, move
        // hashMap do not have duplication, instead of make move key and score value is
        // because I just need pick the best one, hence later move will replace early move with same score
        HashMap<Integer, String> scoreMove = new HashMap<>();
        Collections.shuffle(moveList);

        // try to let bot skip
        if (moveList.size() <= 3) {
            return ".";
        }
        for (String move : moveList) {
            scoreMove.put(score(size, Board.updateBoard(size, board, move)), move);
        }

        // find the biggest key in the hashMap
        int maxScore = -70;
        for (Integer score : scoreMove.keySet()) {
            if (score >= maxScore) {
                maxScore = score;
            }
        }

        return scoreMove.get(maxScore).replaceAll("[.]", "");
    }

    public static String makeMove2(int size, String board) {
        // FIXME Task 11: generate a valid move
        //Task 11: difficulty 3
        // alpha beta pruning

        // player 2 is the bot
        Node<Integer> initNode = new Node<>(0, "initNode");

        // when board is quite empty(short length) generate 2 layers, when there are only a few available movements left generate 3 layers.
        if(board.length() <= 60) {
            generateTree(initNode, 2, false, size, board);
        }else {
            generateTree(initNode, 3, false, size, board);

        }
        String move = Node.abPruning(initNode, initNode.depth(), Integer.MIN_VALUE, Integer.MAX_VALUE, true).getPlacement();
        return move;
    }

    public static void generateTree(Node<Integer> givenNode, int layer, boolean isPlayer1, int size, String board) {

        ArrayList<String> oldMoveList = BloomsGame.allValidMoves(size, board, isPlayer1);
        // shuffle list and take first 1/3 list to speed up generate tree
        Collections.shuffle(oldMoveList);
        List<String> moveList = oldMoveList.size() >= 100 ? oldMoveList.subList(0, oldMoveList.size() / 3 + 1) : oldMoveList;

        LinkedList<Node<Integer>> nodes = new LinkedList<>();
        List<Integer> scoreList = new LinkedList<>();
        ArrayList<String> newBoardList = new ArrayList<>();
        // bot is player2 so when isPlayer1 flase mean player2 take move


        scoreList.add(Integer.MIN_VALUE);
        nodes.add(new Node<Integer>(0, "0 node"));
        newBoardList.add("");

        HashSet<Integer> neighbours = allNeighbourSpaces(size, board);
        int moveListSize = moveList.size();
        // go through all the moves
        for (String placement : moveList) {
            // get score after the placement
            String newBoard = Board.updateBoard(size, board, placement);
            int score = score(size, newBoard);

            // increase scores when the placement is near any placements are current on the board
            String[] numberInPlacement = board.split("[a-d]");
            // start from 1 becasue first one is ""
            for (int i = 1; i < numberInPlacement.length; i++) {
                if (neighbours.contains(Integer.parseInt(numberInPlacement[i]))) {
                    score += 1;
                }
            }


            // player1 score negate
            score = isPlayer1 ? -score : score;

            // pruning method: pick top 5 score's move if movelist have more than 5 scores
            int wantedSize = moveListSize > 5 ? 5 : moveListSize;


            int minimum = Collections.min(scoreList);
            int indexOfMin = scoreList.indexOf(minimum);

            // idea: if scoreList is bigger than wantedSize, then compare the cureent node's score if it is greater than list's minimum score
            // replace the minimum node with the current node otherwise break and run the next loop
            // if scoreList is smaller than wantedSize then add the nodes in.
            if (score > minimum) {
                Node<Integer> childNode = new Node<>(score, placement);
                // if list size is bigger than the wannted size swap the minimum score with cureent score otherwise add score to list
                if (scoreList.size() >= wantedSize) {
                    scoreList.set(indexOfMin, score);
                    // not only score need be swap node need to be swap as well
                    // scoreList and nodes index should be consistant
                    nodes.set(indexOfMin, childNode);
                    newBoardList.set(indexOfMin, newBoard);

                } else {
                    scoreList.add(score);
                    nodes.add(childNode);
                    newBoardList.add(newBoard);
                }
            }
        }

        // root node is not count as one layer

        if (layer - 1 > 0) {
            for (int i = 0; i < nodes.size(); i++) {
                generateTree(nodes.get(i), layer - 1, !isPlayer1, size, newBoardList.get(i));

            }
        }

        givenNode.addChildren(nodes);
    }
    // only used for generateTree();
    private static HashSet<Integer> allNeighbourSpaces(int size, String board) {
        Board mainBoard = new Board();
        mainBoard.setBoard(board);
        HashSet<Integer> neighbourSet = new HashSet<>();
        for (Integer space : mainBoard.boardSpaceList) {
            for (int neighbours : Placement.getNeighbours(4, space)) {
                neighbourSet.add(neighbours);
            }
        }
        return neighbourSet;
    }

    // ------ test start
    public static void main(String[] args) {


        long start = System.currentTimeMillis();


        System.out.println("start");


//        System.out.println("player 2 make move by function 1: " + makeMove1(4, "a6c35d25a18b31c24d14a19b30c12d8a17b26"));
//        System.out.println("player 2 make move by function 2: " + makeMove2(4, "a6c35d25a18b31c24d14a19b30c12d8a17b26"));
        System.out.println("a34.a24b25a23b20a33b35b12a11".length() +
                "c16d3c32d5c31d19c0d2c22d6".length());
        System.out.println("end");
        long time = System.currentTimeMillis() - start;
        System.out.println("time " + time);

    }
    // ------ test end


}
