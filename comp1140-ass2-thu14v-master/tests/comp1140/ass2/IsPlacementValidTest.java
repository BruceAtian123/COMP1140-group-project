package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

/**
 * Task 8
 * <p>
 * Return true if the chosen placement is valid, given the current board string.
 * A placement string consists of zero, one or two piece placements.
 * A piece placement consists of a colour character, followed by an integer which is the space number.
 * A valid placement for player one may include either or both colours [a,b];
 * a valid placement for player two may include either or both colours [c,d].
 * The character "." represents a skipped move.
 */
public class IsPlacementValidTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testCorrect() {
        checkOne(4, "c0d2d3d6a9d10a11c12c13c14a16a17", "a5b4", true);
        checkOne(4, "a1b3a6b7a10a11b12c13c14d16d17c19d24", "c26d25", true);
        checkOne(4, "a1d2a4a5d6d8a10d12d13a16c17c18c19b23b24c25b30c31b35", "a15b29", true);
    }

    @Test
    public void testSkip() {
        checkOne(4, "c0d2d3d6a9d10a11c12c13c14a16a17", ".", true);
        checkOne(4, "a1b3a6b7a10a11b12c13c14d16d17c19d24d25c26", "c27.", true);
    }

    @Test
    public void testNotEmpty() {
        checkOne(4, "a0a1d3a4a6d8b10a11d12d13c14b16b17c19b23c26c27", "c19d25", false);
        checkOne(4, "a0a1d3a4a6d8b10a11d12d13c14b16b17c19b23c26c27", "c11.", false);
        checkOne(4, "a1b3a6b7a10a11b12c13c14d16d17c19d24d25c26", "a1.", false);
    }

    @Test
    public void testFenced() {
        checkOne(4, "a0a1d3a4a6d8b10a11d12d13c14b16b17c19a22b23c26c27", "c5d18", false);
        checkOne(4, "b1d2d3b4b5d6a9c10a11c12c13c14a16a17d18", "c10d18", false);
        checkOne(4, "a5c17d6a1b24c18d2a10b23c25d12a4b30c31d13a16b35c19d8", "a7b3", false);
        checkOne(4, "a5c17d6a1b24c18d2a10b23c25d12a4b30c31d13a16b35c19d8", "a26b11", false);
    }

    @Test
    public void testCaptureRemovesFence() {
        checkOne(4, "b1d2d3b4b5d6a9a11c12c13c14a16a17d18", "c0d10", true);
        checkOne(4, "a1d2a4a5d6d8a10c11d12d13a15a16c17c18c19d22b23b24c25a27b29b30c31b34b35", "c9d0", true);
    }

    private void checkOne(int size, String board, String placement, boolean expected) {
        boolean actual = Placement.isPlacementValid(size, board, placement);
        assertTrue("BloomsGame.isPiecePlacementWellFormed(size=" + size + ", board=\"" + board + "\", placement=\"" + placement + "\") returned " + actual + " but expected " + expected, actual == expected);
    }
}
