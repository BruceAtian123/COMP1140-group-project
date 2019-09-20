package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertArrayEquals;

/**
 * Task 9
 * <p>
 * Compute the score for each player, based on the current board string.
 */
public class GetScoreTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testOnlyPieces4() {
        checkScore(4, "b2b3a4a10a16d19d20c31c32", new int[]{5, 4});
        checkScore(4, "b2d3a6c7a11d12a17c18b23c24d29", new int[]{5, 6});
    }

    @Test
    public void testOnlyPieces5() {
        checkScore(5, "b6b7b8a12c14c15a20a21d22c23c24a30d32d36c37a39d45d52", new int[]{8, 10});
        checkScore(5, "c2c5c6c7c8d11d12b14a15d19a20b21b22a23d28a31d36a39", new int[]{8, 12});
    }

    @Test
    public void testMultipleTerritories4() {
        checkScore(4, "a0a2a5b6a7c8a9b10d11c12d13b16c18c20d21a23a24d25c26d27b28b30d31b34c35", new int[]{19, 16});
        checkScore(4, "b1b2d3a6c7a11d12a17c18b23c24a28d29d33", new int[]{15, 22});
    }

    @Test
    public void testMultipleTerritories5() {
        checkScore(5, "a2a4c5d6a8b9d13b14b16a17c20c21a22b23d28c30a32b33b34d35c36c37d38a41d46", new int[]{18, 19});
        checkScore(5, "a1c8d2a6b5c9d4a12b19c16d17a21b13c24d33a29b28", new int[]{11, 10});
    }

    private void checkScore(int size, String board, int[] expected) {
        int[] actual = Board.getScore(size, board);
        assertArrayEquals("BloomsGame.getScore(size=" + size + ", board=\"" + board + "\") returned incorrect result: ", expected, actual);
    }
}
