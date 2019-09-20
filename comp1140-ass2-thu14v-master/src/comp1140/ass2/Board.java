package comp1140.ass2;


import comp1140.ass2.gui.MultiPlayer;
import comp1140.ass2.gui.SinglePlayer;


import java.util.*;

// Whole class written by Yihan Zhou
public class Board {
    public static final String PLACEMENT_SKIP = ".";

    // current board string
    public static Board MAINBOARD;
    public String board;
    public int size;

    // Corresponding color-space list
    public List<String> boardColorList = new LinkedList<>();
    public List<Integer> boardSpaceList = new LinkedList<>();

    public String toString() {
        String output = "";
        for (int i = 0; i < this.boardSpaceList.size(); i++) {
            output += this.boardColorList.get(i);
            output += this.boardSpaceList.get(i);
        }
        return output;
    }


    // update boardColorList and boardSpaceList
    public void updateBoardLists() {
        String[] numbers = board.split("[a-d]"); // one more than letters
        String[] letters = board.split("\\d+");

        // add numbers to the boardSpaceList add from the actual second term
        for (int i = 1; i < numbers.length; i++) {
            boardSpaceList.add(Integer.parseInt(numbers[i]));
        }
        // add letters to boardSpaceList
        boardColorList.addAll(Arrays.asList(letters));

    }


    public void setBoard(String board) {
        this.board = board;
        // update boardLists every time set board string
        this.updateBoardLists();
    }

    /**
     * Compute the score for each player, based on the current board string.
     * For example, getScore(4, "b2b3a4a10a16d19d20c31c32") should return the array {5, 4}.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @return an array of two elements, containing the score for player 1 and player 2 in order
     */
    public static int[] getScore(int size, String board) {
        // Task 9 written by Yihan Zhou
        // FIXME Task 9: compute the score for each player

        // set main board
        Board mainBoard = new Board();
        mainBoard.setBoard(board);


        // get all empty space bloom from the board where color is f
        ArrayList<String> emptyBlooms = Placement.emptySpaceBloom(size, board);
        int player1 = 0, player2 = 0;

        for (String emptyBloom : emptyBlooms) {
            String[] numbers = emptyBloom.split("[f]");
            // get space number for this bloom
            ArrayList<String> numberList = new ArrayList<>(Arrays.asList(numbers));
            numberList.remove(0); // remove first element which is empty string


            /* check idea:
             * check what color is surrounded by only one player's color
             * if is then add amount of spaces to player 1
             */
            // check for player1
            int flag1 = 0;
            for (String s : numberList) {
                int[] spaceNeighbours = Placement.getNeighbours(size, Integer.parseInt(s));
                for (int spaceNeighbour : spaceNeighbours) {
                    // surrounded by other player's space which mean it is not a territory for player 1
                    if (getColor(size, board, spaceNeighbour).equals("c") || getColor(size, board, spaceNeighbour).equals("d")) {
                        flag1++;
                        break;
                    }
                }
                // as soon as find out it is not a territory break loop check next empty bloom
                if (flag1 != 0) break;
            }
            // if it is a territory then add spaces' amount to player1 as score;
            if (flag1 == 0) player1 += numberList.size();

            // check for player2 similar to palyer 1
            int flag2 = 0;
            for (String s : numberList) {
                int[] spaceNeighbours = Placement.getNeighbours(size, Integer.parseInt(s));
                for (int spaceNeighbour : spaceNeighbours) {
                    if (getColor(size, board, spaceNeighbour).equals("a") || getColor(size, board, spaceNeighbour).equals("b")) {
                        flag2++;
                        break;
                    }
                }
                if (flag2 != 0) break;
            }
            if (flag2 == 0) player2 += numberList.size();


        }

        // checking current spaces amount on the board
        for (String letter : mainBoard.boardColorList) {
            if (letter.equals("a") || letter.equals("b")) {
                player1++;
            } else {
                player2++;
            }
        }

        int[] output = new int[2];
        output[0] = player1;
        output[1] = player2;

        return output;
    }

    /**
     * get color of the input space
     *
     * @param board the size of the board i.e. the number of hexes on a side
     * @param space the number of the space
     * @return color of the space if it is an empty space return "f"
     */

    public static String getColor(int size, String board, int space) {
        // Function getColor written by Yihan Zhou

        // check weather index out of bound
        if (space < 0 || space > getMax(size)) {
            return "No such space";
        }


        // set main board
        Board mainBoard = new Board();
        mainBoard.setBoard(board);

        int index = mainBoard.boardSpaceList.indexOf(space);
        if (index == -1) {
            return "f"; // if it is an empty space then will return f
        } else {
            return mainBoard.boardColorList.get(index);
        }
    }


    /**
     * get the maximum space value from size
     *
     * @param size the size of the board i.e. the number of hexes on a side
     * @return the maximum space value
     */
    public static int getMax(int size) {
        // function getMax written by Yihan Zhou
        int output = 0;
        for (int i = 0; i <= size - 2; i++) {
            output += size + i;
        }
        return 2 * output + 2 * size - 2;
    }


    /**
     * update the board, should be used after every new placement. Update player's move list, remove fenced placement, stored new board string.
     *
     * @param size      the size of the board i.e. the number of hexes on a side
     * @param oldBoard  a board string representing every currently placed piece
     * @param placement a piece placement string
     * @return the new board string
     */

    public static String updateBoard(int size, String oldBoard, String placement) {
        // function updateBoard written by Yihan Zhou

        if (!Placement.isPlacementValid(size, oldBoard, placement)) {
            // not valid move
            return "";
        }
        if (oldBoard.isEmpty()) { // while board is empty add the placement
            // check skip
            if (placement.equals(".")) return "";
            // check one placement or two placement
            placement = placement.contains(".") ? placement.replace(".", "") : placement;
            return placement;
        }


        placement = placement.contains(".") ? placement.replace(".", "") : placement;
        // remove fenced placements
        oldBoard += placement;
        oldBoard = oldBoard.replaceAll("[.]", "");
        // set main board
        Board mainBoard = new Board();
        mainBoard.setBoard(oldBoard);

        // idea check every soace bloom
        // if it is fenced then remove whole bloom form list
        for (int i = 0; i < mainBoard.boardSpaceList.size(); i++) {
            String bloom = BloomsGame.getBloom(size, oldBoard, mainBoard.boardSpaceList.get(i));
            // if bloom is not fenced go to next loop
            if (!BloomsGame.isFenced(size, oldBoard, bloom)) continue;
            // remove spaces in bloom
            Board bloomString = new Board();
            bloomString.setBoard(bloom);

            for (int j = 0; j < bloomString.boardSpaceList.size(); j++) {
                int index = mainBoard.boardSpaceList.indexOf(bloomString.boardSpaceList.get(j));
                if (index != -1) {
                    mainBoard.boardSpaceList.remove(index);
                    mainBoard.boardColorList.remove(index);
                }
            }
        }
        return mainBoard.toString();


    }


    /**
     * update the board, should be used after every new placement. Update player's move list, remove fenced placement, stored new board string.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @return the string that can only be "Player1" "Player2" or "None"
     */
    public static String isFinished(int size, String board, boolean isSingle) {
//        function written by Yihan Zhou
        Board currentBoard = new Board();
        currentBoard.setBoard(board);
        // all place have been filled
        int[] scores = getScore(size, board);

        int place1Length;
        int place2Length;
        if (isSingle) {
            place1Length = SinglePlayer.place1.length();
            place2Length = SinglePlayer.place2.length();
        } else {
            place1Length = MultiPlayer.place1.length();
            place2Length = MultiPlayer.place2.length();
        }
        // make sure player have placed at least two move
        if (place1Length < 3 || place2Length < 3) return "None";
        // if player make consecutive two skips then game finish
        if (isSingle) {
            if (SinglePlayer.place1.substring(place1Length - 2).equals("..") || SinglePlayer.place2.substring(place2Length - 2).equals(".."))
                return winner(scores, true);

        } else {
            if (MultiPlayer.place1.substring(place1Length - 2).equals("..") || MultiPlayer.place2.substring(place2Length - 2).equals(".."))
                return winner(scores, false);
        }

        return "None";

    }

    private static String winner(int[] scores, boolean isSingle) {
        // function written by Yihan Zhou
        // game draw
        if (scores[0] == scores[1]) {
            // find out who passed first
            int place1Length;

            place1Length = isSingle ? SinglePlayer.place1.length() : MultiPlayer.place1.length();

            if (isSingle) {
                if (SinglePlayer.place1.substring(place1Length - 2).equals("..")) {
                    // who ever skip twice consecutively fisrt wins when scores are same
                    return "Player1";
                } else {
                    return "Player2";
                }
            } else {
                if (MultiPlayer.place1.substring(place1Length - 2).equals("..")) {
                    // player1 skipped later than player2
                    return "Player1";
                } else {
                    return "Player2";
                }
            }
        }
        // compare scoring
        return scores[0] - scores[1] > 0 ? "Player1" : "Player2";
    }


}

