package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 7
 * <p>
 * Given a board string, and a String representing the pieces in a bloom,
 * determine whether or not the bloom is fenced i.e. entirely surrounded
 * by pieces of different colours.
 */
public class IsFencedTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);


    @Test
    public void testCentre4() {
        checkIsFenced(4, "a11c10d17a30b18c5d6a29b31c16d12", "a11");
        checkIsFenced(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "d17");
        checkIsNotFenced(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "c1c5c10c16");
        checkIsNotFenced(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "b18");
        checkIsNotFenced(4, "c1c5d6c10a11d12c16d17b18d19b22b23a24a29a30b31", "a24a29a30");
    }

    @Test
    public void testEdge4() {
        checkIsFenced(4, "c5d6c10d12c16d17b18b19c22b26c28a29a30b31a33d34a35", "d34");
        checkIsNotFenced(4, "c5d6c10d12c16d17b18b19c22b26c28a29a30b31a33d34a35", "a29a30a33a35");
        checkIsNotFenced(4, "c5d6c10d12c16d17b18b19c22b26c28a29a30b31a33d34a35", "d17");
    }

    @Test
    public void testCentre5() {
        checkIsFenced(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b21b29");
        checkIsFenced(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "b23b32");
        checkIsNotFenced(5, "a11d12d13c18a19c20a26b27b28", "c20");
        checkIsNotFenced(5, "a20c22d14a13b21c30d15a28b29c38d7a12b23c31d16a36b32c40d24a46b39c41d6a48b47c37d33", "a12a13a20a28a36");
    }

    @Test
    public void testEdge5() {
        checkIsFenced(5, "a11d12d13c18a19c20a26b27b28", "c18");
        checkIsFenced(5, "d0a5a6d11a12d18c19a20b26c27b28c35b36", "d11d18");
        checkIsNotFenced(5, "a11d12d13c18a19c20a26b27b28", "c20");
        checkIsNotFenced(5, "a11d12d13c18a19c20a26b27b28", "a11a19");
        checkIsNotFenced(5, "d0a5a6d11a12d18c19a20b26c27b28c35b36", "c19c27c35");
        checkIsNotFenced(5, "d0a5a6d11a12d18c19a20b26c27b28c35b36", "a5a6a12a20");
    }

    private void checkIsFenced(int size, String board, String bloom) {
        assertTrue("BloomsGame.isFenced(size=" + size + ", board=\"" + board + "\", bloom=\"" + bloom + "\") returned false; expected true", BloomsGame.isFenced(size, board, bloom));
    }
    private void checkIsNotFenced(int size, String board, String bloom) {
        assertFalse("BloomsGame.isFenced(size=" + size + ", board=\"" + board + "\", bloom=\"" + bloom + "\") returned true; expected false", BloomsGame.isFenced(size, board, bloom));
    }
}
