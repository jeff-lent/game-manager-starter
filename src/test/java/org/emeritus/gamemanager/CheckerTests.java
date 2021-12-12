package org.emeritus.gamemanager;

import static org.emeritus.gamemanager.Color.BLACK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckerTests {
    @Test
    public void NoArgConstructorTest() {
        Checker checker = new Checker();
        assertEquals(BLACK,checker.getColor());
    }
    @Test
    public void OneArgConstructorTest() {
        Checker checker = new Checker(Color.RED);
        assertEquals(Color.RED, checker.getColor());
    }
    @Test
    public void TwoArgConstructorTest() {
        Checker checker = new Checker(Color.RED, true);
        assertEquals(Color.RED, checker.getColor());
        assertEquals(true, checker.isKing());
    }
    @Test
    public void isKingSetterTest() {
        Checker checker = new Checker();
        checker.setKing(true);
        assertEquals(true, checker.isKing());
    }
}
