package org.emeritus.gamemanager;

import lombok.Getter;
import lombok.Setter;

/* This class defines a square.   A collection of these makes a board.
 * You should not need to edit this code!!
 */

public class Square {
    @Getter @Setter private Square topRight;
    @Getter @Setter private Square topLeft;
    @Getter @Setter private Square bottomRight;
    @Getter @Setter private Square bottomLeft;
    @Getter private int name;
    @Getter @Setter private Checker checker;
    public Square(int name) {
        this.bottomLeft = null;
        this.bottomRight = null;
        this.topLeft = null;
        this.topRight = null;
        this.name = name;
        this.checker = null; 
    }
    public Square(int name, Square topLeft, Square topRight, Square bottomLeft, Square bottomRight, Checker checker) {
        this.name = name;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.checker = checker;
    }
}
