package org.emeritus.gamemanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GameTests {
    
    @Test
    void constuctorTest() {
        UUID redPlayer = UUID.randomUUID();
        UUID blackPlayer = UUID.randomUUID();
        Game game = new Game(redPlayer, blackPlayer);
        assertNotNull(game);
        assertEquals(redPlayer, game.getRedPlayer());
        assertEquals(blackPlayer, game.getBlackPlayer());
    }
}
