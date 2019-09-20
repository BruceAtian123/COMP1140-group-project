package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.function.Consumer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Task 3
 * <p>
 * Determine whether a piece placement is well-formed according to the following:
 * - it consists of two or more characters
 * - the first character is a letter representing a colour [a-d];
 * - the remaining characters represent a natural number.
 */
public class PiecePlacementWellFormedTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testSimple() {
        Consumer<String> tester = (String piecePlacement) -> {
            assertTrue("Piece placement " + piecePlacement + " is well formed, but isPiecePlacementWellFormed returned false", Placement.isPiecePlacementWellFormed(piecePlacement));
        };
        tester.accept("a0");
        tester.accept("b1");
        tester.accept("c4");
        tester.accept("d5");
        tester.accept("a7");
        tester.accept("b11");
        tester.accept("c55");
        tester.accept("d0");
        tester.accept("a12");
    }

    @Test
    public void testBad() {
        Consumer<String> bad = (String piecePlacement) -> {
            assertFalse("Piece placement " + piecePlacement + " is not well formed, but isPiecePlacementWellFormed returned true", Placement.isPiecePlacementWellFormed(piecePlacement));
        };
        bad.accept("e0");
        bad.accept("f1");
        bad.accept("A4");
        bad.accept("B5");
        bad.accept("C7");
        bad.accept("D11");
        bad.accept("55");
        bad.accept("a9.9");
        bad.accept("b-1");
        bad.accept("c,0");
        bad.accept("d.1");
    }

}