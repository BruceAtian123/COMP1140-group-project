package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

/**
 * Task 4
 * <p>
 * Get the list of spaces that are neighbours of the given space, on a board of the given size.
 */
public class GetNeighboursTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testCentre4() {
        checkOne(4, 5, new int[]{0, 1, 4, 6, 10, 11});
        checkOne(4, 10, new int[]{4, 5, 9, 11, 16, 17});
        checkOne(4, 18, new int[]{11, 12, 17, 19, 24, 25});
        checkOne(4, 23, new int[]{16, 17, 22, 24, 28, 29});
        checkOne(4, 31, new int[]{25, 26, 30, 32, 35, 36});
    }

    @Test
    public void testEdge4() {
        checkOne(4, 0, new int[]{1, 4, 5});
        checkOne(4, 2, new int[]{1, 3, 6, 7});
        checkOne(4, 9, new int[]{4, 10, 15, 16});
        checkOne(4, 21, new int[]{14, 20, 27});
        checkOne(4, 34, new int[]{29, 30, 33, 35});
    }

    @Test
    public void testCentre5() {
        checkOne(5, 9, new int[]{3, 4, 8, 10, 15, 16});
        checkOne(5, 21, new int[]{13, 14, 20, 22, 29, 30});
        checkOne(5, 23, new int[]{15, 16, 22, 24, 31, 32});
        checkOne(5, 46, new int[]{38, 39, 45, 47, 52, 53});
        checkOne(5, 52, new int[]{45, 46, 51, 53, 57, 58});
    }

    @Test
    public void testEdge5() {
        checkOne(5, 10, new int[]{4, 9, 16, 17});
        checkOne(5, 18, new int[]{11, 19, 26, 27});
        checkOne(5, 26, new int[]{18, 27, 35});
        checkOne(5, 49, new int[]{41, 42, 48, 55});
        checkOne(5, 60, new int[]{54, 55, 59});
    }

    private void checkOne(int size, int space, int[] expected) {
        int[] result = Placement.getNeighbours(size, space);
        Arrays.sort(result);
        assertArrayEquals("BloomsGame.getNeighbours(size=" + size + ", space=" + space + ") returned incorrect result. Expected:" + Arrays.toString(expected) + " but got: " + Arrays.toString(result), expected, result);
    }
}
