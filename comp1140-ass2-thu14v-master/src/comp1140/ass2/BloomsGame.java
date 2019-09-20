package comp1140.ass2;

import comp1140.ass2.gui.SinglePlayer;

import java.util.*;
import java.util.stream.Collectors;


// getBloom, generateMove, allValidMoves written by Yihan Zhou
// isFenced writen by Jinming Dong
public class BloomsGame {
    public static final String PLACEMENT_SKIP = ".";

    /**
     * Get a string representing the set of all piece placements that form the bloom
     * which includes the given space, on a board of the given size.
     * For example, getBloom(4, "a0a1b6a7", 0) should return "a0a1".
     * If there is no bloom covering the given space (i.e. the space is empty),
     * return the empty string.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board the current board setup, representing every currently placed piece
     * @param space the number of the space
     * @return a String listing all pieces that are connected to the given piece
     * Each element of the array contains two integers, representing the q and r coordinates of the connected square.
     */

    public static String getBloom(int size, String board, int space) {
        // Task 6 written by Yihan Zhou
        // FIXME Task 6: compute the set of spaces in the bloom which includes the given space

        // seprate the given board to letters and numbers
        String[] numbers = board.split("[a-d|f]");
        String[] letters = board.split("\\d+");

        // get the given space's color
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int i = 1; i < numbers.length; i++) al.add(Integer.parseInt(numbers[i]));
        int indexOfGivenSpce = al.indexOf(space);
        if (indexOfGivenSpce == -1) {
            return "";
        }
        String colorOfGivenSpce = letters[indexOfGivenSpce];

        // collect all the spaces where color is equal to the give space
        ArrayList<Integer> oneColorList = new ArrayList<>();
        for (int i = 1; i < numbers.length; i++) {
            if (letters[i - 1].equals(colorOfGivenSpce)) oneColorList.add(Integer.parseInt(numbers[i]));
        }

        ArrayList<Integer> resultList = new ArrayList<Integer>();
        resultList.add(space); // add given space first

        // ---------------------------- checking area start
        int index = 1;
        int originalL = oneColorList.size();

        /*  main idea:
         *  check element in resultList, weather it have neignhours in the oneColorList, if it have add it in resultList and remove that element from oneColorList.
         *  loop again after checked elements and add new element in the resultList.
         *  The worse case is every loop will add one new element in, hence the terminate condition is after each element in the onColorList have been checked.
         */
        while (index <= originalL) {
            for (int i = 0; i < resultList.size(); i++) {
                for (Integer a : resultList) oneColorList.remove(a); // remove object not by index

                // chaneg int array to an Integer array list
                int[] neighbourArr = Placement.getNeighbours(size, resultList.get(i));
                ArrayList<Integer> neighbourArrList = new ArrayList<Integer>();
                for (int k : neighbourArr) neighbourArrList.add(k);

                for (int j = 0; j < oneColorList.size(); j++) {
                    // add neighbours
                    if (neighbourArrList.contains(oneColorList.get(j))) resultList.add(oneColorList.get(j));
                }

            }
            // if the list size is 0, no point loop again and check.
            if (oneColorList.size() == 0) break;
            index++;
        }
        // ---------------------------- checking area finished

        // sort a arraylist
        // url: https://stackoverflow.com/questions/16252269/how-to-sort-an-arraylist
        Collections.sort(resultList);
        // concat integer arraylist to a string
        // url: https://stackoverflow.com/questions/599161/best-way-to-convert-an-arraylist-to-a-string
        String output = resultList.stream().map(Object::toString).collect(Collectors.joining(colorOfGivenSpce));
        output = colorOfGivenSpce + output;
        return output;
    }


    /**
     * Given a board string, and a String representing the pieces in a bloom,
     * determine whether or not the bloom is fenced i.e. entirely surrounded
     * by pieces of different colours.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @param bloom a String representing the placement of each piece in the bloom
     * @return true if the bloom is fenced
     */
    public static boolean isFenced(int size, String board, String bloom) {
        // written by Jinming Dong
        // FIXME Task 7: determine whether a given bloom is fenced

        Board currentBoard = new Board();
        currentBoard.setBoard(board);
        // bc = the letter that we would like to get
        String bc = bloom.substring(0, 1);
        // bns = the bloom that we would like to get
        String[] bna = bloom.split("[a-d]");
        ArrayList<Integer> bns = new ArrayList<>();
        for (int i = 1; i < bna.length; ++i) {
            bns.add(Integer.parseInt(bna[i]));
        }
        // build map
        int[][] map = new int[size * 2 - 1][size * 2 - 1];
        // add an array of string(to be more convenient)
        String[][] cmap = new String[size * 2 - 1][size * 2 - 1];
        int s = 0, e = size, c = 0, i = 0, j = 0;
        for (i = 0; i < size * 2 - 1; ++i) {
            for (j = 0; j < size * 2 - 1; ++j) {
                // . suggest that this space is not dominated by opponent yet
                cmap[i][j] = PLACEMENT_SKIP;
                if (j < s || j >= e) {
                    // the space outside our map, use letter except [a-d]
                    cmap[i][j] = "e";
                    map[i][j] = -1;
                } else {
                    map[i][j] = c++;
                    if (currentBoard.boardSpaceList.contains(map[i][j])) {
                        String x = currentBoard.boardColorList.get(currentBoard.boardSpaceList.indexOf(map[i][j]));
                        // if the space is in the bloom that we would like to get
                        // then we mark it
                        if (!x.equals(bc) || bns.contains(map[i][j])) {
                            cmap[i][j] = x;
                        }
                    }
                }
            }
            if (i < size - 1) e++;
            if (i >= size - 1) s++;
        }
        // put the space around bloom into ns
        currentBoard.boardSpaceList.clear();
        for (int _i : bns) {
            int[] neighbours = Placement.getNeighbours(size, _i);
            for (int _j : neighbours) {
                if (currentBoard.boardSpaceList.contains(_j)) continue;
                currentBoard.boardSpaceList.add(_j);
            }
        }
        boolean b = true;
        for (i = 0; i < size * 2 - 1; ++i) {
            for (j = 0; j < size * 2 - 1; ++j) {
                if (currentBoard.boardSpaceList.contains(map[i][j]) && !bns.contains(map[i][j])) {
                    // if the surrounding spaces have our color or empty
                    // then it suggests we are not fenced
                    if (cmap[i][j].equals(bc) || cmap[i][j].equals(PLACEMENT_SKIP)) {
                        b = false;
                        break;
                    }
                }
            }
            if (!b) break;
        }
        return b;
    }


    /**
     * Generate a valid move, given the preceding move sequence.
     *
     * @param size         the size of the board i.e. the number of hexes on a side
     * @param moveSequence a valid sequence of moves, representing the game so far
     * @return a valid move for the player whose turn it is next, or the empty string if the move should be skipped
     */
    public static String generateMove(int size, String moveSequence) {
        // written by Yihan Zhou
        // real Task 11 is in ComputerPalyer !!!!!!!!!!!
        Random random = new Random();


        boolean isPalyer1sTurn;
        // check next player's turn ******************

        // by last placement on board
        String palyerString = moveSequence.substring(moveSequence.length() - 3, moveSequence.length());
        isPalyer1sTurn = palyerString.contains("c") || palyerString.contains("d");
        // by moves take by player, depend one which player make placement first
        // isPalyer1sTurn = (player1Movements.size() - player2Movements.size()) > 0;


        // all the possible moves
        ArrayList<String> moves = allValidMoves(size, moveSequence, isPalyer1sTurn);
        int listSize = moves.size();
        // random pick one from moves list
        int pick = random.nextInt(listSize);
        return moves.get(pick);
    }

    public static ArrayList<String> allValidMoves(int size, String board, boolean isPlayer1) {
        // function allValidMoves written by Yihan Zhou

        /*  main idea:
         *  put the move into given condition
         *  try skip, one placement, two placement with no duplications
         */
        ArrayList<String> result = new ArrayList<>();
        String[] numbers = board.split("[a-d]");
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 1; i < numbers.length; i++) {
            numberList.add(Integer.parseInt(numbers[i]));
        }
        // for skip moves
        result.add(".");
        // for player 1
        if (isPlayer1) {
            // for single placement
            for (int i = 0; i < Board.getMax(size) + 1; i++) {
                String p1 = "a" + i + ".";
                String p2 = "b" + i + ".";
                if (Placement.isPlacementValid(size, board, p1)) result.add(p1);
                if (Placement.isPlacementValid(size, board, p2)) result.add(p2);
            }

            // for two placements
            for (int i = 0; i < Board.getMax(size) + 1; i++) {
                if (numberList.contains(i)) continue;
                String p1 = "a" + i;
                for (int j = 0; j < Board.getMax(size) + 1; j++) {
                    if (j == i || numberList.contains(j)) continue;
                    if (Placement.isPlacementValid(size, board, p1 + "b" + j)) result.add(p1 + "b" + j);
                }
            }

        } else { // player 2 similar steps with a b -> d c
            for (int i = 0; i < Board.getMax(size) + 1; i++) {
                String p3 = "c" + i + ".";
                String p4 = "d" + i + ".";
                if (Placement.isPlacementValid(size, board, p3)) result.add(p3);
                if (Placement.isPlacementValid(size, board, p4)) result.add(p4);
            }
            // for two placements
            for (int i = 0; i < Board.getMax(size) + 1; i++) {
                if (numberList.contains(i)) continue;
                String p3 = "c" + i;
                for (int j = 0; j < Board.getMax(size) + 1; j++) {
                    if (j == i || numberList.contains(j)) continue;
                    if (Placement.isPlacementValid(size, board, p3 + "d" + j)) result.add(p3 + "d" + j);
                }
            }
        }


        return result;
    }



}
