package org.emeritus.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SquareTests {
    @Test
    public void NoArgsConstructorTest(){
        Square square = new Square(0);
        assertEquals(null, square.getTopRight());
        assertEquals(null, square.getTopLeft());
        assertEquals(null, square.getBottomRight());
        assertEquals(null, square.getBottomLeft());
        assertEquals(null, square.getChecker());
        assertEquals(0, square.getName());
    }
    @Test
    public void AllArgsConstructorTest() {
        Square square = new Square(1, null, null, new Square(5), new Square(6), new Checker());
        assertEquals(1, square.getName());
        assertEquals(null, square.getTopLeft());
        assertEquals(null, square.getTopRight());
        assertEquals(5,square.getBottomLeft().getName());
        assertEquals(6,square.getBottomRight().getName());
        assertEquals(Color.BLACK, square.getChecker().getColor());
    }

}
