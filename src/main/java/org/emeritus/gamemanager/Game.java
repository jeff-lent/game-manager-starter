package org.emeritus.gamemanager;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

/* This class holds the state for a particular game including
 * who the players are, the gameID and the board.
 */

public class Game {
    public Game(UUID redPlayer, UUID blackPlayer){
        this.setRedPlayer(redPlayer);
        this.setBlackPlayer(blackPlayer);
        this.setBoard(new Board());
        this.setGameId(UUID.randomUUID());
    }

    @Getter @Setter
    private UUID gameId;

    @Getter @Setter
    private UUID redPlayer;

    @Getter @Setter
    private UUID blackPlayer;

    @Getter @Setter
    private Board board;
}
