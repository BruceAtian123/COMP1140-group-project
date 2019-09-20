package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 11
 * <p>
 * Get a string representing the set of all piece placements that form the bloom
 * which includes the given space, on a board of the given size.
 * If there is no valid moves
 * return the empty move.
 */

public class GenerateMoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    //skip should always be valid
    @Test
    public void testSkip() {
        checkIsValid(4,"b2b3a4a10a16d19d20c31c32","a6.");
        checkIsValid(4,"c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31","c4.");
        checkIsNotValid(4,"b2b3a4a10a16d19d20c31c32","b2b3");
        checkIsNotValid(4,"c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31","c1c5");
    }

    //test simple case when the board is in size 4
    @Test
    public void testSimple4() {
        checkIsValid(4,"b2b3a4a10a16d19d20c31c32","a5.");
        checkIsValid(4,"c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31","c3c4");
        checkIsNotValid(4,"b2b3a4a10a16d19d20c31c32","b2b3");
        checkIsNotValid(4,"c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31","c1c5");
    }

    //test simple case when the board is in size 5
    @Test
    public void testSimple5() {
        checkIsValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b5.");
        checkIsValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "a11a34");
        checkIsNotValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b20b29");
        checkIsNotValid(5,"a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33","a15b22");
    }

    //test fenced case when the board is in size 4
    @Test
    public void testFenced4() {
        checkIsValid(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "c15.");
        checkIsValid(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "c2c3");
        checkIsNotValid(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "b20.");
        checkIsNotValid(4,"a11c10d17a30b18c5d6a29b31c16d12","a11");
        checkIsNotValid(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "d17");
    }

    //test fenced case when the board is in size 5
    @Test
    public void testFenced5() {
        checkIsValid(5, "a11d12d13c18a19c20a26b27b28", "c21.");
        checkIsValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "a3.");
        checkIsNotValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b21b29");
        checkIsNotValid(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b23b32");
    }

    private void checkIsValid(int size, String board, String move) {
        boolean isPlayer1;
        String playerString = board.substring(board.length()-3,board.length());
        isPlayer1 = playerString.contains("c") || playerString.contains("d");
        ArrayList<String> allmove = BloomsGame.allValidMoves(size,board,isPlayer1);
        boolean actual;
        if(allmove.contains(move)){
                actual = true;
        }else{
                actual = false;
            }
        assertTrue("BloomsGame.allValidMoves(size=" + size + ", board=\"" + board + "\") returned false; expected true", actual);
    }

    private void checkIsNotValid(int size, String board, String move) {
        boolean isPlayer1;
        String palyerString = board.substring(board.length()-3,board.length());
        isPlayer1 = palyerString.contains("c") || palyerString.contains("d");
        ArrayList<String> allmove = BloomsGame.allValidMoves(size,board,isPlayer1);
        boolean actual;
        if(allmove.contains(move)){
            actual = true;
        }else{
            actual = false;
        }
        assertFalse("BloomsGame.allValidMoves(size=" + size + ", board=\"" + board + "\") returned true; expected false", actual);
    }
}
