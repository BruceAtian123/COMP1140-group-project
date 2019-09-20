package comp1140.ass2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class getMaxTest {

    /**
     * getMaxTest
     * <p>
     * get the biggest number in the board with size given
     * start count from 0
     */

    @Test
    public void testBoardSize(){
        assertEquals(18,Board.getMax(3));
        assertEquals(36,Board.getMax(4));
        assertEquals(60,Board.getMax(5));
    }
}
