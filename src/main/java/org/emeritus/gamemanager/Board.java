package org.emeritus.gamemanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.var;

/* This class keeps track of the board and the game state.  
 * You should not need to change this code!!
 */

public class Board {

    @Getter
    private ArrayList<Square> boardList; //This contains the board(its a collection of Squares)

    @Getter @Setter 
    private Color turn; //here we keep track of whose turn it is

    private void connectTheSquares() {
        List<Integer> evenRowSquares = Arrays.asList(1,2,3,4,9,10,11,12,17,18,19,20,25,26,27,28); //squares that are on even rows.
        List<Integer> rightEdgeSquares = Arrays.asList(4,12,20,28); //squares that are on the right edge of the board
        List<Integer> leftEdgeSquares = Arrays.asList(5,13,21,29); //squares that are on the left edge of the board.
        for(int i=1; i<33; i++){
            //even rows use different offsets for calculating the squares they connect to than odd rows use.
            if(evenRowSquares.contains(i)){   //we're on an even row square
                this.square(i).setTopRight(connectSquare(rightEdgeSquares, i, -3, 0));
                this.square(i).setTopLeft(connectSquare(Arrays.asList(),i, -4, 0));
                this.square(i).setBottomRight(connectSquare(rightEdgeSquares, i, 5, 33));
                this.square(i).setBottomLeft(connectSquare(Arrays.asList(), i, 4, 33));
            } else {  // we're on an odd row square
                this.square(i).setTopRight(connectSquare(Arrays.asList(), i, -4, 0));
                this.square(i).setTopLeft(connectSquare(leftEdgeSquares, i, -5, 0));
                this.square(i).setBottomRight(connectSquare(Arrays.asList(), i, 4, 33));
                this.square(i).setBottomLeft(connectSquare(leftEdgeSquares, i, 3, 33));
            }
        }
    }

    private Square connectSquare(List<Integer> edgeSquares, int i, int offset, int boundary) {
        //this helper method checks to see if our potential connection is on the board or not
        //and returns a null square if it is not.
        if(boundary == 0 && edgeSquares.isEmpty()) return (i+offset>boundary) ? this.square(i+offset) : null;
        if(boundary == 0 && !edgeSquares.isEmpty()) return (i+offset>boundary && !edgeSquares.contains(i)) ? this.square(i+offset) : null;
        if(boundary == 33 && edgeSquares.isEmpty()) return (i+offset<boundary) ? this.square(i+offset) : null;
        return (boundary == 33 && i+offset<boundary && !edgeSquares.contains(i)) ? this.square(i+offset) : null;
    }

    private void populateBoard(String boardState) {
        //this helper method puts the checkers on the board
        Checker checker;
        for(int i=1; i<33; i++){
            if(boardState.equals("")) {
                //if we're here we're setting up a default start board.
                if(i<13) checker = new Checker();
                else if(i>20) checker = new Checker(Color.RED);
                else checker = null;
            } else {
                //if we're here we're setting up a board state based on a string of characters.
                //'b' is black regular | 'B' is black king | 'r' is red regular | 'R' is red King
                switch(boardState.charAt(i)) {
                    case 'b': checker = new Checker();
                              break;
                    case 'B': checker = new Checker(Color.BLACK,true);
                              break;
                    case 'r': checker = new Checker(Color.RED);
                              break;
                    case 'R': checker = new Checker(Color.RED,true);
                              break;
                    default : checker = null;
                              break;
                }
            }
            if(checker == null) this.boardList.add(i, new Square(i));
            else this.boardList.add(i,new Square(i,null,null, null, null, checker));
        }
    }
    private char getPrintableSquare(Checker currentChecker) {
        char square;
        char blackSquare = '_';
        char redChecker = 'r';
        char blackChecker = 'b';
        char redKingChecker = 'R';
        char blackKingChecker = 'B';
        if(currentChecker == null) square = blackSquare;
        else if(currentChecker.getColor()==Color.BLACK && !currentChecker.isKing()) square = blackChecker;
             else if(currentChecker.getColor()==Color.BLACK && currentChecker.isKing()) square = blackKingChecker;
                  else if(currentChecker.getColor()==Color.RED && currentChecker.isKing()) square = redKingChecker;
                       else square = redChecker;
        return square;
    }

    private int isJump(int start, int end) {
        Square startSquare = this.boardList.get(start);
        if(startSquare.getTopLeft() != null &&
           startSquare.getTopLeft().getTopLeft() != null &&
           startSquare.getTopLeft().getTopLeft().getName() == end ) return startSquare.getTopLeft().getName(); //Move is to jump the top Left
        else if (startSquare.getTopRight() != null &&
                 startSquare.getTopRight().getTopRight() != null &&
                 startSquare.getTopRight().getTopRight().getName() == end) return startSquare.getTopRight().getName(); //Move is to jump the top Right
        if(startSquare.getBottomLeft() != null &&
           startSquare.getBottomLeft().getBottomLeft() != null &&
           startSquare.getBottomLeft().getBottomLeft().getName() == end ) return startSquare.getBottomLeft().getName(); //Move is jumping lower left
        else if(startSquare.getBottomRight() != null &&
                startSquare.getBottomRight().getBottomRight() != null &&
                startSquare.getBottomRight().getBottomRight().getName() == end) startSquare.getBottomRight().getName(); //Move is jumping lower right
        return -1;
    }

    private boolean endIsLegal(int start, int end) {
        Checker startChecker = this.boardList.get(start).getChecker();
        Square startSquare = this.boardList.get(start);
        Square endSquare = this.boardList.get(end);
        if(startChecker != null && startChecker.isKing()){
            Color oppositeColor = startChecker.getColor() == Color.RED ? Color.BLACK : Color.RED;
            if((startSquare.getTopLeft() != null && startSquare.getTopLeft().getName() == end) || 
               (startSquare.getTopRight() != null && startSquare.getTopRight().getName() == end) || 
               (startSquare.getBottomLeft() != null && startSquare.getBottomLeft().getName() == end) ||
               (startSquare.getBottomRight() != null && startSquare.getBottomRight().getName() == end) &&
               endSquare.getChecker() == null) return true;
            else if(startSquare.getTopLeft() != null && 
                    startSquare.getTopLeft().getTopLeft() != null && 
                    startSquare.getTopLeft().getTopLeft().getName() == end && 
                    endSquare.getChecker() == null && 
                    startSquare.getTopLeft().getChecker()!= null &&
                    startSquare.getTopLeft().getChecker().getColor() == oppositeColor) return true; //Move is to jump the top Left
            else if(startSquare.getTopRight() != null && 
                    startSquare.getTopRight().getTopRight() != null &&
                    startSquare.getTopRight().getTopRight().getName() == end &&
                    endSquare.getChecker() == null &&
                    startSquare.getTopRight().getChecker() != null &&
                    startSquare.getTopRight().getChecker().getColor() == oppositeColor) return true; //Move is to jump the top Right
            else if(startSquare.getBottomLeft() != null &&
                    startSquare.getBottomLeft().getBottomLeft() != null &&
                    startSquare.getBottomLeft().getBottomLeft().getName() == end &&
                    endSquare.getChecker() == null &&
                    startSquare.getBottomLeft().getChecker() != null &&
                    startSquare.getBottomLeft().getChecker().getColor() == oppositeColor) return true; //Move is jumping lower left
            else if(startSquare.getBottomRight() != null &&
                    startSquare.getBottomRight().getBottomRight() != null && 
                    startSquare.getBottomRight().getBottomRight().getName() == end &&
                    endSquare.getChecker() == null && 
                    startSquare.getBottomRight().getChecker() != null && 
                    startSquare.getBottomRight().getChecker().getColor() == oppositeColor) return true; // Move is jumping lower right
            else return false;
        }
        if(this.getTurn() == Color.RED) { //Deal with differences due to color
            if(((startSquare.getTopLeft() != null && startSquare.getTopLeft().getName() == end ) ||(startSquare.getTopRight() != null && startSquare.getTopRight().getName() == end)) && endSquare.getChecker() == null) return true; //Move is to the top left or top right
            else if(startSquare.getTopLeft() != null && 
                    startSquare.getTopLeft().getTopLeft() != null && 
                    startSquare.getTopLeft().getTopLeft().getName() == end && 
                    endSquare.getChecker() == null && 
                    startSquare.getTopLeft().getChecker() != null && 
                    startSquare.getTopLeft().getChecker().getColor() == Color.BLACK) return true; // Move is to jump the top Left.
            else if(startSquare.getTopRight() != null && 
                    startSquare.getTopRight().getTopRight() != null &&
                    startSquare.getTopRight().getTopRight().getName() == end &&
                    endSquare.getChecker() == null &&
                    startSquare.getTopRight().getChecker() != null &&
                    startSquare.getTopRight().getChecker().getColor() == Color.BLACK) return true; //Move is to jump the top Right.
            else return false; //Move isn't legal
        } else { //Turn is Black
            if((startSquare.getBottomLeft() != null && startSquare.getBottomLeft().getName() == end) ||
                (startSquare.getBottomRight() != null && startSquare.getBottomRight().getName() == end) &&
                endSquare.getChecker() == null) return true; //Move is to the lower left or lower right
            else if(startSquare.getBottomLeft() != null &&
                    startSquare.getBottomLeft().getBottomLeft() != null &&
                    startSquare.getBottomLeft().getBottomLeft().getName() == end &&
                    endSquare.getChecker() == null &&
                    startSquare.getBottomLeft().getChecker() != null  &&
                    startSquare.getBottomLeft().getChecker().getColor() == Color.RED) return true; //Move is jumping lower left
            else if(startSquare.getBottomRight() != null &&
                    startSquare.getBottomRight().getBottomRight() != null &&
                    startSquare.getBottomRight().getBottomRight().getName() == end &&
                    endSquare.getChecker() == null &&
                    startSquare.getBottomRight().getChecker() != null &&
                    startSquare.getBottomRight().getChecker().getColor() == Color.RED) return true; //Move is jumping lower right
            else return false; //Move isn't legal
        }
    }

    private boolean startIsLegal(int start) {
        if(this.boardList.get(start).getChecker() != null && this.boardList.get(start).getChecker().getColor().equals(this.turn)) return true;
        else return false;
    }

    private boolean moveIsInRange(int start, int end) {
        if(start>0 && start<33 && end>0 && end<33) return true;
        else return false;
    }

    public Square square(int name) {
        return boardList.get(name);
    }

    public Board() {
        this("");
    }

    public Board(String boardState) {
        boardList = new ArrayList<>();
        boardList.add(new Square(0)); //add a place holder for the 0 position (since checkers boards are numbered from 1)
        this.turn = Color.RED;
        populateBoard(boardState); //place checkers on the appropriate squares
        connectTheSquares(); // connect the corners of the squares to each other correctly.
    }

    @Override
    public String toString(){
        char redSquare = ' ';
        char square;
        StringBuilder output = new StringBuilder();
        for(int row = 0; row<8; row++){
            for(int column = 1; column<5; column++){
                Checker currentChecker = this.boardList.get(row*4+column).getChecker();
                square = getPrintableSquare(currentChecker);
                if(row % 2 == 0 ) output.append(redSquare).append(square);
                else output.append(square).append(redSquare);
            }
            output.append('\n');
        }
        return output.toString();
    }

    public Map<String,Boolean> move(int start, int end) {
        final String legalMove = "legalMove";
        final String additionalJumps = "additionalJumps";
        final String gameOver = "gameOver";
        ArrayList<Integer> thisMove = new ArrayList<>();
        HashMap<String, Boolean> returnData = new HashMap<>();
        thisMove.add(start);
        thisMove.add(end); 
        ArrayList<ArrayList<Integer>> legalJumps = getLegalJumps();
        returnData.put(legalMove, false);
        returnData.put(additionalJumps, false);
        returnData.put(gameOver, false);
        // If there are legalJumps, and this move isn't one of them then return Illegal Move
        if(!legalJumps.isEmpty() && !legalJumps.contains(thisMove)) {
            return returnData;
        } 
        // If we're here than either thisMove is not a jump or its a legalJump.
        if(moveIsInRange(start, end) &&
           startIsLegal(start) &&
           endIsLegal(start,end)) {
               //If we're inside this if, then thisMove is legal, so let's make our move.
               int jumped = isJump(start,end); //check and see if thisMove is a jump, if it is then jumped holds the square we jumped over.
               boardList.get(end).setChecker(boardList.get(start).getChecker()); //put the checker on the ending square.
               boardList.get(start).setChecker(null); //remove the checker from the beginning square.
               if(jumped != -1) boardList.get(jumped).setChecker(null); //if a jump happened then remove the opponents checker.
            if(turn == Color.RED && end<=4) boardList.get(end).getChecker().setKing(true); //if we end on the promotion row for Red then king the checker
            if(turn == Color.BLACK && end>=29) boardList.get(end).getChecker().setKing(true); //if we end on the promotion row for Black then king the checker
            //check if there are any legal jumps from our ending location
            if(jumped != -1) {
                legalJumps = getLegalJumps();
                for(var jump:legalJumps) {
                    if(jump.get(0) == end){
                        returnData.replace(additionalJumps, true);
                        returnData.replace(legalMove, true);
                        return returnData;
                    } 

                }
            }            
            if(turn==Color.BLACK) turn = Color.RED; //flip the turn to the other player.
            else turn = Color.BLACK;
            boolean checkerFound = false;
            for(var square:boardList){
                if(square.getChecker() != null && square.getChecker().getColor() == turn) {
                    checkerFound = true;
                }
            }
            if(!checkerFound) returnData.replace(gameOver, true);
            returnData.replace(legalMove, true);
            return returnData;
        }
        else {
            returnData.put("legalMove", false);
            returnData.put("additionalJumps", false);
            returnData.put("gameOver", false);
            return returnData;
        } 
    }

    private ArrayList<ArrayList<Integer>> getLegalJumps() {
        ArrayList<ArrayList<Integer>> legalJumps = new ArrayList<>();
        int start = 1;
        ArrayList<Integer> possibleEnds = new ArrayList<>();
        for(int i=1; i<33; i++){
            if(boardList.get(i).getChecker() != null && //We found a checker that matches the turn color and it is not a king.
               boardList.get(i).getChecker().getColor() == this.turn &&
               !boardList.get(i).getChecker().isKing()){
                    start = i;
                    if(boardList.get(i).getChecker().getColor() == Color.RED) {
                        if(boardList.get(i).getTopLeft() != null && boardList.get(i).getTopLeft().getTopLeft() != null) possibleEnds.add(boardList.get(i).getTopLeft().getTopLeft().getName());
                        if(boardList.get(i).getTopRight() != null && boardList.get(i).getTopRight().getTopRight() != null) possibleEnds.add(boardList.get(i).getTopRight().getTopRight().getName());
                    } else {
                        if(boardList.get(i).getBottomLeft() != null && boardList.get(i).getBottomLeft().getBottomLeft() != null) possibleEnds.add(boardList.get(i).getBottomLeft().getBottomLeft().getName());
                        if(boardList.get(i).getBottomRight()!= null && boardList.get(i).getBottomRight().getBottomRight() != null) possibleEnds.add(boardList.get(i).getBottomRight().getBottomRight().getName());
                    }
                    for(int end : possibleEnds){
                        if(moveIsInRange(start, end) && startIsLegal(start) && endIsLegal(start, end)) legalJumps.add(new ArrayList<>(Arrays.asList(start,end)));
                    }
                    possibleEnds.clear();
            } 
            if(boardList.get(i).getChecker()!=null && //We found a checker that matches the turn color and it is a king
                boardList.get(i).getChecker().getColor() == turn &&
                boardList.get(i).getChecker().isKing()){
                    start = i;
                    if(boardList.get(i).getTopLeft() != null && boardList.get(i).getTopLeft().getTopLeft() != null) possibleEnds.add(boardList.get(i).getTopLeft().getTopLeft().getName());
                    if(boardList.get(i).getTopRight() != null && boardList.get(i).getTopRight().getTopRight() != null) possibleEnds.add(boardList.get(i).getTopRight().getTopRight().getName());
                    if(boardList.get(i).getBottomLeft() != null && boardList.get(i).getBottomLeft().getBottomLeft() != null) possibleEnds.add(boardList.get(i).getBottomLeft().getBottomLeft().getName());
                    if(boardList.get(i).getBottomRight() != null && boardList.get(i).getBottomRight().getBottomRight() != null) possibleEnds.add(boardList.get(i).getBottomRight().getBottomRight().getName());
                    for(int end : possibleEnds){
                        if(moveIsInRange(start, end) && startIsLegal(start) && endIsLegal(start, end)) legalJumps.add(new ArrayList<>(Arrays.asList(start,end)));
                    }
                    possibleEnds.clear();
            }
        }
        return legalJumps;
    }
}
