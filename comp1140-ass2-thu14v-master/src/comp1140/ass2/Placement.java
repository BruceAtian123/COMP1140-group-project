package comp1140.ass2;

import java.util.*;
import java.util.stream.Collectors;

// isPiecePlacementWellFormed, isPlacementValid, emptySpaceBloom, isEmpty written by Yihan Zhou
// getNeighbours writen by Jinming Dong
public class Placement {
    public static final String PLACEMENT_SKIP = ".";

    /**
     * Determine whether a piece placement is well-formed according to the following rules:
     * - it consists of two or more characters
     * - the first character is a letter representing a colour [a-d];
     * - the remaining characters represent a natural number.
     *
     * @param piecePlacement a piece placement string
     * @return true if the piece placement is well-formed
     */

    public static boolean isPiecePlacementWellFormed(String piecePlacement) {
        // FIXME Task 3: determine whether the given piece placement is well-formed
        boolean b1, b2, b3 = true;
        String checkString = piecePlacement;
        if (piecePlacement.equals(PLACEMENT_SKIP)) {
            // "." skip checked
            return true;
        }else if (piecePlacement.contains(PLACEMENT_SKIP)){
            // single placement eg. d14.
            checkString = piecePlacement.substring(0,piecePlacement.length()-1);
        }

        // 1.check whether it consists of two or more characters
        b1 = checkString.length() >= 2;
        // 2.check the first character is a letter between a-d
        try {
            b2 = checkString.substring(0, 1).matches("[a-d]");
        }catch (StringIndexOutOfBoundsException e){
            return false;
        }

        // 3.the remaining characters represent a natural number
        String[] strArr = checkString.split("[a-d]");
        for (int i = 1; i < strArr.length; i++) {
            if (!strArr[i].matches("\\d*")) {
                b3 = false;
            }
        }

        return b1 && b2 && b3;
    }

    //Written by Jinming Dong
    /**
     * Get the list of spaces that are neighbours of the given space, on a board of the given size.
     * For example, getNeighbours(4, 2) should return the array {1, 3, 6, 7}.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param space the number of the space
     * @return an N-by-2 array containing the set of spaces connected to the given space.
     * Each element of the array contains two integers, representing the q and r coordinates of the connected square.
     */
    public static int[] getNeighbours(int size, int space) {
        // FIXME Task 4: get the list of spaces which are neighbours to the given space
        // url:https://www.redblobgames.com/grids/hexagons/
        // if it's in size of 1 or less then it doesn't have any neighbours
        if (size <= 1) return new int[0];
        // build an two-dimensional array to give coordinates
        int[][] map = new int[size * 2 - 1][size * 2 - 1];
        int s = 0, e = size, c = 0, _i = -size, _j = -size, i = 0, j = 0;
        for (i = 0; i < size * 2 - 1; ++i) {
            for (j = 0; j < size * 2 - 1; ++j) {
                if (j < s || j >= e) {
                    map[i][j] = -1;
                } else {
                    // while recording the index, also capture the
                    // coordinates
                    if ((map[i][j] = c++) == space) {
                        _i = i; _j = j;
                    }
                }
            }
            if (i < size - 1) e++;
            if (i >= size - 1) s++;
        }
        ArrayList<Integer> a = new ArrayList<>();
        // to save space
        i = _i; j = _j;
        // eliminate the points that are on the diagonals
        for (_i = -1; _i <= 1; ++_i) {
            for (_j = -1; _j <= 1; ++_j) {
                if (_i + _j == 0) continue;
                s = i + _i; e = j + _j;
                if (0 <= s && s < size * 2 - 1 && 0 <= e && e < size * 2 - 1) {
                    if (map[s][e] >= 0) {
                        a.add(map[s][e]);
                    }
                }
            }
        }
        // Arraylist to int[]
        int[] b = new int[a.size()];
        for (i = 0; i < a.size(); ++i)
            b[i] = a.get(i);
        return b;
    }

    /**
     * Return true if the chosen placement is valid, given the current board string.
     * A placement string consists of zero, one or two piece placements.
     * A piece placement consists of a colour character, followed by an integer which is the space number.
     * A valid placement for player one may include either or both colours [a,b];
     * a valid placement for player two may include either or both colours [c,d].
     * The character "." represents a skipped move.
     *
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @return true if the given placement is valid
     */
    public static boolean isPlacementValid(int size, String board, String placement) {
        // FIXME Task 8: determine if the given placement is valid
        // firstly check the form standard of given placement
        if (!isPiecePlacementWellFormed(placement)) {
            return false;
        }
        String[] numbers = board.split("[a-d]");
        ArrayList<String> numbersOnBoard = new ArrayList<>(Arrays.asList(numbers));
        int numberOfPlacement = 1; // 0 1 2
        // one or no placement
        if (placement.contains(PLACEMENT_SKIP)) {
            numberOfPlacement = (placement.length() == 1) ? 0 : 1;
        } else if(placement.length()>3){ // two placement and make sure the placement contain two placement by length
            numberOfPlacement = 2;
        }
        String space1, space2;
        int spaceInt1, spaceInt2;

        boolean b1, b2 = false, b3 = true, b4 = false;
        boolean isFencedSpace1,isFencedSpace2;



        switch (numberOfPlacement) {
            case 0:
                return true;
            case 1:
                space1 = placement.substring(1, placement.length() - 1);
                spaceInt1 = Integer.parseInt(space1);
                // check overlap
                b1 = numbersOnBoard.contains(space1);
                board += placement.substring(0, placement.length() - 1);
                isFencedSpace1 =  BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, spaceInt1));


                int[] spaceArr = getNeighbours(size,spaceInt1);
                // check Fenced and CaptureRemovesFence
                if (isFencedSpace1){
                    for (int aSpaceArr : spaceArr){
                        if (aSpaceArr == spaceInt1){continue;}
                        if (BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, aSpaceArr))) b2=true;break;
                    }
                }else b2 = true;

                // b1 test whether space is not empty
                // b2 test whether is fenced other space(valid move) or it will be fenced(not valid move)
                return !b1 && b2;
            case 2:
                switch (placement.charAt(0)) {
                    // two placement have to have one player's two different color
                    case 'a':
                        b3 = placement.contains("c") || placement.contains("d")|| placement.substring(1).contains("a");
                        break;
                    case 'b':
                        b3 = placement.contains("c") || placement.contains("d")|| placement.substring(1).contains("b");
                        break;
                    case 'c':
                        b3 = placement.contains("a") || placement.contains("b")|| placement.substring(1).contains("c");
                        break;
                    case 'd':
                        b3 = placement.contains("a") || placement.contains("b")|| placement.substring(1).contains("d");
                        break;
                }

                String[] spacesInPlacement = placement.split("[a-d]");
                // get two number space
                space1 = spacesInPlacement[1];
                space2 = spacesInPlacement[2];
                // check whether two placement is the same space
                if (space1.equals(space2)){
                    return false;
                }
                //
                spaceInt1 = Integer.parseInt(space1);
                spaceInt2 = Integer.parseInt(space2);
                board += placement;

                // check overlap
                b1 = numbersOnBoard.contains(space1) || numbersOnBoard.contains(space2);
                isFencedSpace1 =  BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, spaceInt1));
                isFencedSpace2 =  BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, spaceInt2));
                int[] space1Arr = getNeighbours(size, spaceInt1);
                int[] space2Arr = getNeighbours(size, spaceInt2);

                // check Fenced and CaptureRemovesFence
                // go through the spaces around given placement if any of them is fenced then the placement is valid  (CaptureRemovesFence)
                // otherwise is it is fenced then the placement is not valid
                if (isFencedSpace1){
                    for (int aSpace1Arr : space1Arr){
                        if (aSpace1Arr == spaceInt2){continue;}
                        if (BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, aSpace1Arr))) {b2=true;break;}
                    }
                }else b2 = true;
                if (isFencedSpace2){
                    for (int aSpace2Arr : space2Arr){
                        if (aSpace2Arr == spaceInt1){continue;}
                        if (BloomsGame.isFenced(size, board, BloomsGame.getBloom(size, board, aSpace2Arr))) {b4=true;break;}
                    }
                }else b4 = true;

                // b1 test whether space is not empty
                // b3 make sure two placement have to have one player's two different color
                // b2 test first placement is fenced other space(valid move) or it will be fenced(not valid move)
                // b4 test second placement is fenced other space(valid move) or it will be fenced(not valid move)
                return !b1 && !b3 && b4 && b2;
            default:
                return false;
        }
    }

    /**
     * get all empty space blooms
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @return an ArrayList<String> contain all empty space blooms
     * */

    public static ArrayList<String> emptySpaceBloom(int size, String board){
        ArrayList<Integer> emptySpaceList = new ArrayList<>();
        for (int i = 0; i < Board.getMax(size) + 1 ; i++) {
            if (isEmpty(size,board,i)){
                emptySpaceList.add(i);
            }
        }
        ArrayList<String> resultList = new ArrayList<>();
        String colorOfEmptySpace = "f";
        String output = emptySpaceList.stream().map(Object::toString).collect(Collectors.joining(colorOfEmptySpace));
        board += colorOfEmptySpace + output;
        // set all into the board with color e
        String currentBloom;

        // empty space blooms, recorded like a matrix
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> existNumbers = new ArrayList<>();
        for (Integer integer : emptySpaceList) {
            if (existNumbers.contains(integer)){continue;}
            currentBloom = BloomsGame.getBloom(size,board,integer);
            resultList.add(currentBloom);

            String[] numbers = currentBloom.split("[a-d|f]");
            for (int i = 1; i < numbers.length ; i++) {
                existNumbers.add(Integer.parseInt(numbers[i]));
            }

        }
        // get bloom of all these and make into a list of int
        return resultList;
    }

    /**
     * check the space in board is empty space or not
     * @param size  the size of the board i.e. the number of hexes on a side
     * @param board a board string representing every currently placed piece
     * @param space the number of the space
     * @return true if the given placement is empty
     */
    public static boolean isEmpty(int size, String board, int space){
        String[] numbers = board.split("[a-d]");
        ArrayList<String> numList = new ArrayList<>(Arrays.asList(numbers));
        return !numList.contains(Integer.toString(space));

    }

}

