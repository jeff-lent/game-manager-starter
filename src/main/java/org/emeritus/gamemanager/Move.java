package org.emeritus.gamemanager;

import lombok.Getter;
import lombok.Setter;

public class Move {
    @Getter @Setter private int start;

    @Getter @Setter private int end;

    @Getter @Setter private int jump;

    @Getter @Setter private boolean gameOver;

    @Getter @Setter private boolean additionalJumps;

    @Getter @Setter private String color;

    public Move() { 
        start = -1;
        end = -1;
        jump = -1;
        gameOver = false;
        additionalJumps  = false;
        color = "BLACK";;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Move) {
            Move move = (Move) obj;
            if(move.getStart() == this.getStart() &&
               move.getEnd() == this.getEnd() &&
               move.getJump() == this.getJump() &&
               move.getColor() == this.getColor() &&
               move.isGameOver() == this.isGameOver() &&
               move.isAdditionalJumps() == this.isAdditionalJumps()){
                return true;
            }
        }
        return false;       
    }
}
