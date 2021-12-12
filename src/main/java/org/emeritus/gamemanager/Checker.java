package org.emeritus.gamemanager;

import lombok.Getter;
import lombok.Setter;

/* This class holds the information for a checker. 
 * You should not need to modify this code!!
 */

public class Checker {
    @Getter private Color color;
    @Getter @Setter private boolean isKing;
    public Checker() {
        this.color = Color.BLACK;
        this.isKing = false;
    }
    public Checker(Color color) {
        this.color = color;
        this.isKing = false;
    }
    public Checker(Color color, boolean isKing) {
        this.color = color;
        this.isKing = isKing;
    }    
}
