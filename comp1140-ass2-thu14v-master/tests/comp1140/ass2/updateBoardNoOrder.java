package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class updateBoardNoOrder {
    /**
     * updateBoard
     * <p>
     * update currentBoard return a string of new board that added the placement
     * new board will be in order.
     */
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    // test no change no fenced space
    @Test
    public void testNoChange(){

//        no order version
        testOne(4,"c1c5d6c10d12c16b18d19b22b23a24a29a30b31","d15.","c1c5d6c10d12c16b18d19b22b23a24a29a30b31d15");
        testOne(4,"c1c5d6c10d12c16b18d19b22b23a24a29a30b31","a0b20","c1c5d6c10d12c16b18d19b22b23a24a29a30b31a0b20");
        testOne(5,"a20c22d14c52d57a13b21c30d15a28c38d7a12c31d16a36b51a56c40d24a46b39c41d6a48b47c37d33","c17d43",
                "a20c22d14c52d57a13b21c30d15a28c38d7a12c31d16a36b51a56c40d24a46b39c41d6a48b47c37d33c17d43");
    }
    // check whether fenced have been removed
    @Test
    public void testFenced(){

//        no order version

        testOne(4,"c1c5d6c10a11d12d15c16d17b18d19b22b23a24a29a30b31","a0.","c1c5d6c10d12d15c16b18d19b22b23a24a29a30b31a0");
        testOne(5,"a11d12d13c18a19c20a26b27b28","a0b5","d12d13c20a26b27b28a0b5");
        testOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33","c17d43",
                "a20c22d14c52d57a13c30d15a28c38d7a12c31d16a36b51a56c40d24a46b39c41d6a48b47c37d33c17d43");
    }


    private void testOne(int size, String oldBoard, String placement,String expected) {
        String result = Board.updateBoard(size,oldBoard,placement);
        assertEquals("Board.updateBoard(size=" + size + ", board=\""+ oldBoard +"\", placement=\"" + placement + "\") returned incorrect result:", expected, result);
    }
}
