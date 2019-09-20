package comp1140.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class emptySpaceBloomTest {


/**
 * emptySpaceBloomTest
 * <p>
 * an ArrayList<String> contain all empty space
 * and similar to bloom connected empty space will be a string
 */
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testBoardSize4(){
        ArrayList<String> e1 = new ArrayList<>();
        e1.add("f0");
        e1.add("f3f7");
        e1.add("f9f15f22f28f29f33f34");
        e1.add("f11");
        e1.add("f14f20f21f26f27f32f36");
        String board = "a5c17d6a1b24c18d2a10b23c25d12a4b30c31d13a16b35c19d8";
        testOne(4,board,e1);

        board += "b3d20a21";
        ArrayList<String> newe1 = new ArrayList<>();

        newe1.add("f0");
        newe1.add("f7");
        newe1.add("f9f15f22f28f29f33f34");
        newe1.add("f11");
        newe1.add("f14");
        newe1.add("f26f27f32f36");

        testOne(4,board,newe1);


        ArrayList<String> e2 = new ArrayList<>();
        e2.add("f1");
        e2.add("f4");
        e2.add("f7f8");
        e2.add("f15f22f23f28");
        e2.add("f19f20f21");
        e2.add("f30");
        e2.add("f32f36");
        testOne(4,"c0d2d3a5d6a9d10a11c12c13c14a16a17a18b24b25a26b27b29c31a33c34d35",e2);

    }

    @Test
    public void testBoardSize5(){
        ArrayList<String> e1 = new ArrayList<>();
        e1.add("f0f1f2f3f4f8f9f10");
        e1.add("f11f18f19f26f27f35");
        e1.add("f25f34f42");
        e1.add("f44f45");
        e1.add("f53f54f58f59f60");
        testOne(5,"a5c17a20d43c50c22d14c52d57a13b21d49a55c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",e1);


        ArrayList<String> e2 = new ArrayList<>();
        e2.add("f0f1f2f3f4f5f8f9f10f11f17f18f19f25f26f27f34f35f42f43f44f45f49f50f53f54f55f58f59f60");
        testOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",e2);

    }


    private void testOne(int size, String board, ArrayList<String> expected) {
        ArrayList<String> result = Placement.emptySpaceBloom(size, board);

        assertEquals("Placement.emptySpaceBloom(size=" + size + ", board=\"" + board + ") returned incorrect result:", expected, result);
    }
}

