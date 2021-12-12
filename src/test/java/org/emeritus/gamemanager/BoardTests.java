package org.emeritus.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardTests {
    @Test
    public void NoArgConstructorTest(){
        Board board = new Board();
        assertEquals(1,board.square(1).getName());
        assertEquals(Color.BLACK,board.square(1).getChecker().getColor());
        assertEquals(5,board.square(1).getBottomLeft().getName());
        assertEquals(6,board.square(1).getBottomRight().getName());
        assertEquals(32,board.square(32).getName());
        assertEquals(Color.RED, board.square(32).getChecker().getColor());
        assertEquals(27,board.square(32).getTopLeft().getName());
        assertEquals(28,board.square(32).getTopRight().getName());
        assertNull(board.square(4).getTopRight());
        assertNull(board.square(4).getBottomRight());
        assertNull(board.square(5).getTopLeft());
        assertNull(board.square(5).getBottomLeft());
    }

    @Test
    public void toStringTest(){
        Board board = new Board("              b   r            RB");
        String output = board.toString();
        assertEquals(' ',output.charAt(0));
        assertEquals('b',output.charAt(29));
        assertEquals(' ',output.charAt(70));
        assertEquals('R',output.charAt(67));
        assertEquals('B',output.charAt(69));
    }

    @Test
    public void moveTest(){
        Board board = new Board();
        HashMap<String,Boolean> returnData = new HashMap<>();
        returnData.put("legalMove", true);
        returnData.put("additionalJumps", false);
        returnData.put("gameOver", false);
        assertEquals(returnData, board.move(22,18));
        assertEquals(returnData, board.move(9,14));
        assertEquals(returnData, board.move(18,9));
        assertEquals(returnData, board.move(5,14));
        assertEquals(returnData,board.move(23,18));
        assertEquals(returnData, board.move(14,23));
        assertEquals(returnData, board.move(26,19));
        assertEquals(returnData, board.move(6,13));
        assertEquals(returnData, board.move(18,15));
        assertEquals(returnData, board.move(11,18));
        returnData.replace("legalMove", false);
        assertEquals(returnData, board.move(14,18));
        returnData.replace("legalMove", true);
        assertEquals(returnData, board.move(24,20));
        returnData.replace("legalMove", false);
        assertEquals(returnData, board.move(12,15));
        returnData.replace("legalMove", true);
        board = new Board("      r                   b      ");
        assertEquals(returnData,board.move(6,2)); //Promote red checker to King
        assertTrue(board.square(2).getChecker().isKing());
        assertEquals(returnData, board.move(26,30)); //Promote black checker to King
        assertTrue(board.square(30).getChecker().isKing());
        board = new Board("      R Rbb           rr  B B    ");
        assertEquals(returnData, board.move(6,13)); //Red king jumps black checker going backwards
        assertEquals(returnData, board.move(26,17)); //Black king jumps red checker going forwards
        assertEquals(returnData, board.move(13,22)); //Red king jumps black king going backwards
        assertEquals(returnData, board.move(17,26)); //Black king moves up and left one square
        assertEquals(returnData,board.move(22,31)); //Red checker moves up and right one square
        assertEquals(returnData, board.move(26,19)); //black king moves up and left one square
        assertEquals(returnData, board.move(8,4)); //Red king moves up and right one square
    }
    @Test
    public void moveTestDoubleJump(){
        Board board = new Board(" _____b_______b___r______________");
        HashMap<String,Boolean> returnData = new HashMap<>();
        returnData.put("legalMove", true);
        returnData.put("additionalJumps", true);
        returnData.put("gameOver", false);
        assertEquals(returnData, board.move(18,9));
        returnData.replace("additionalJumps", false);
        returnData.replace("gameOver", true);
        assertEquals(returnData, board.move(9,2));
    }
}


