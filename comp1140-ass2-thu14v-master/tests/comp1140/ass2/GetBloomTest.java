package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

/**
 * Task 6
 * <p>
 * Get a string representing the set of all piece placements that form the bloom
 * which includes the given space, on a board of the given size.
 * If there is no bloom covering the given space (i.e. the space is empty),
 * return the empty string.
 */
public class GetBloomTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);


    @Test
    public void testCentre4() {
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 18, "a5a11a18a19a26");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 12, "b12");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 17, "b17b24b25");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 23, "d23d29");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 13, "");
    }

    @Test
    public void testEdge4() {
        testOne(4, "a0b1c7d8c13", 7, "c7c13");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 2, "d1d2d6");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 7, "c7c8");
        testOne(4, "d1d2a5d6c7c8c10a11b12c16b17a18a19d23b24b25a26d29c30", 3, "");
    }

    @Test
    public void testCycles4() {
        testOne(4, "d2a5d6c7c8c10a11c13d16b17a18a19d23b24b25a26d28d29c30", 13, "c7c8c13");
        testOne(4, "d2a5d6c7c8c10a11c13d16b17a18a19d23b24b25a26d28d29c30", 28, "d16d23d28d29");
    }

    @Test
    public void testCentre5() {
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 12, "d6d12d20d29");
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 13, "a13a14");
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 15, "c8c15c21c22");
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 30, "a30a31a38");
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 40, "b39b40b46b47");
        testOne(5, "d6c8d12a13a14c15d20c21c22d29a30a31a38b39b40b46b47", 41, "");
    }

    @Test
    public void testEdge5() {
        testOne(5, "a0b1c3c4c7d8c10", 3, "c3c4c10");
        testOne(5, "d1d2d6a7d8a9c10d12a13b14d15c16c17d20b21a22d23c24c25d29d30d31a38b43b44b45a46a47c48b52b53a54c55a59c60", 46, "a38a46a47a54a59");
        testOne(5, "d1d2d6a7d8a9c10d12a13b14d15c16c17d20b21a22d23c24c25d29d30d31a38b43b44b45a46a47c48b52b53a54c55a59c60", 44, "b43b44b45b52b53");
        testOne(5, "d1d2d6a7d8a9c10d12a13b14d15c16c17d20b21a22d23c24c25d29d30d31a38b43b44b45a46a47c48b52b53a54c55a59c60", 60, "c48c55c60");
    }

    @Test
    public void testCycles5() {
        testOne(5, "d1d2d6a7d8a9c10d12a13b14d15c16c17d20b21a22d23c24c25d29d30d31a38b43b44b45a46a47c48b52b53a54c55a59c60", 2, "d1d2d6d8d12d15d20d23d29d30d31");
        testOne(5, "d1d2d6a7d8a9c10d12a13b14d15c16c17d20b21a22d23c24c25d29d30d31a38b43b44b45a46a47c48b52b53a54c55a59c60", 17, "c10c16c17c24c25");
    }

    @Test
    public void testNoBloom() {
        testOne(4, "a0b1c7d8c13", 6, "");
    }

    private void testOne(int size, String board, int space, String expected) {
        String result = BloomsGame.getBloom(size, board, space);
        assertEquals("BloomsGame.getBloom(size=" + size + ", board=\"" + board + "\", space=" + space + ") returned incorrect result:", expected, result);
    }
}
