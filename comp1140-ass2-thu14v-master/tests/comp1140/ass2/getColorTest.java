//package comp1140.ass2;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.Timeout;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//public class getColorTest {
//
//    /**
//     * getColor
//     * <p>
//     *  return the color of space on given board
//     *  if the there is no such space then will return f
//     */
//    @Rule
//    public Timeout globalTimeout = Timeout.millis(2000);
//
//
//    @Test
//    public void testColor(){
//        checkOne(4,"a1d2a4a5d6d8a10c11d12d13a15a16c17c18c19d22b23b24c25a27b29b30c31b34b35",23,"b");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",30,"c");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",21,"b");
//    }
//
//
//    // test no placement color "f"
//    @Test
//    public void testNoSuchPlacement() {
//        checkOne(4,"a1d2a4a5d6d8a10c11d12d13a15a16c17c18c19d22b23b24c25a27b29b30c31b34b35",20,"f");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",60,"f");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",0,"f");
//    }
//
//    // test no such space
//    @Test
//    public void testIndexOutBound(){
//        checkOne(4,"a1d2a4a5d6d8a10c11d12d13a15a16c17c18c19d22b23b24c25a27b29b30c31b34b35",40,"No such space");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",61,"No such space");
//        checkOne(5,"a20c22d14c52d57a13b21c30d15a28b29c38d7a12b23c31d16a36b51a56b32c40d24a46b39c41d6a48b47c37d33",-1,"No such space");
//
//    }
//    private void checkOne(int size, String board, int space, String expected) {
//        String actual = Board.getColor(size, board, space,);
//        assertEquals("Board.getColor(board=\"" + board + "\", space=\"" + space + "\") returned " + actual + " but expected " + expected,expected,actual);
//    }
//}
