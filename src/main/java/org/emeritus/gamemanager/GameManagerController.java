package org.emeritus.gamemanager;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;

/* This class is the controller for th GameManager API
 * It should map to "api/play"
 * All your edits should occur here.
 */

@RestController
@RequestMapping("/api/play")
public class GameManagerController {
    //this is a list of all the games that are being played.
    @Getter private final ArrayList<Game> games = new ArrayList<>();
    
    /* This endpoint is called by GameMatcher to post a new game.
     * it should map to "/register"
     * it should wrap the String response in a ResponseEntity
     * and it should respond with 200 when it creates a new game.
     * The response string shoudl be the GUID of the created game.
     * The map that it takes as an argument should contain the two player Guids
     * You can see sample behavior by posting to https://checker-game-manager.herokuapp.com/api/play/register
     * The RequestBody should contain json with two GUIDS, like this:
     * {"redPlayerString": "fd16249a-9bc5-4bd6-9745-87d93418e011","blackPlayerString": "fd16249a-9bc5-4bd6-9745-87d93418e012"}
     */
    public String postMatch(Map<String,String> json) {
        //Annotate this method as needed and add code to match the behavior at the URL above.
        return json.get("");
    }

    /* This endpoint is called by the client to get the status of the game. 
     * it should map to "/status/{game_id}"
     * it should wrap the String response in a ResponseEntity
     * and it should respond with 200 when it finds a game to return or 404 if it doesn't.
     * The response string should be a call to the ToString method of the Board.
     * It takes a gameID guid as its argument.
     * You can see sample behavior by getting from 
     * https://checker-game-manager-prod.herokuapp.com/api/play/status/{gameID}
     */
    public String getBoard(String gameID) {
        //Annotate this method as needed and add code to match the behavior at the URL above.
        Game game = new Game(null, null);
        return game.getBoard().toString();
    }
    
    /* This endpoint is called by the client make a move. 
     * it should map to "/move"
     * it should wrap the Map<String,Boolean> response in a ResponseEntity
     * and it should respond with 200 when the move is legal and  400 (bad request) if it isn't.
     * The Map<String, String> that is returned is the return value from a call to
     * game.getBoard().move(start, end)
     * It takes a Map<String,String> as its argument, those strings are the number of the start and end squares.
     * You can see sample behavior by posting to
     * https://checker-game-manager.herokuapp.com/api/play/move
     * The request body is:
     * {
     *  "gameId": "838eeef6-8147-46aa-9306-12da318db362",
     *  "start": "22",
     *  "end":"18"
     *  }
     */
    public Map<String, String> postMove(Map<String,String> json) {
        //Annotate this method as needed and add code to match the behavior at the URL above.
        Game game = new Game(null, null);
        return game.getBoard().move(Integer.parseInt(json.get("start")),Integer.parseInt(json.get("end")));
    }

    /* This endpoint is called by the client to get the lastMove and is used to wait for the opponents move. 
     * it should map to "/lastmove/{game_id}"
     * it should wrap the Move response in a ResponseEntity
     * and it should respond with 200 when gameId is valid and  404 (not found) if it isn't.
     * The Move object that is returned is the return value from a call to
     * game.getBoard().getLastMove()
     * It takes no arguments.
     * You can see sample behavior by getting
     * https://checker-game-manager.herokuapp.com/api/play/lastmove/{gameId}
     */
    public Move getLastMove(UUID gameId) {
        Game game = new Game(null, null);
        //Annotate this method as needed and add code to match the behavior at the URL above.
        return game.getBoard().getLastMove();
    }
}