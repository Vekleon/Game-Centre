package fall2018.csc2017.slidingtiles.gamestates;


import android.util.Log;

import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Tile;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightBoard;

public class TwentyFourtyEightGameState extends GameState implements Stack<TwentyFourtyEightBoard>{
    
    ArrayList<TwentyFourtyEightBoard> moves;
    private int score;

    /**
     * Initialises the slidingBoard for 2048
     *
     * @param board,   the slidingBoard of which the moves you are keeping track of.
     * @param seconds, as defined.
     **/
    public TwentyFourtyEightGameState(TwentyFourtyEightBoard board, int seconds) {
        super(board, seconds);
        moves = new ArrayList<>();
    }

    /**
     * Checks if there is still possible moves remaining.
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     *
     * @return whether or not the gameState is still playable
     */
    public boolean canMove() {
        //If any blank tiles exist, a move must exist
        if (((TwentyFourtyEightBoard) this.getBoard()).numBlank() != 0) {
            return true;
        }
        Board board = getBoard();
        //Check if any merges exist
        for (int x = 0; x < board.getComplexity(); x++) {
            for (int y = 0; y < board.getComplexity(); y++) {
                Tile t = board.getTile(x, y);
                if ((x < (board.getComplexity() - 1) && t.getBackgroundId() == board.getTile(x + 1, y).getBackgroundId())
                        || (y < (board.getComplexity() - 1) && t.getBackgroundId() == board.getTile(x, y + 1).getBackgroundId())) {

                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Sets the current score of the game into the game state
     * @param score the score of the game at the given save point.
     */
    public void setScore(int score){
        this.score = score;
    }

    /**
     * Get the score of the current save state
     */
    public int getScore(){
        return score;
    }


    /**
     * Getter for the stack of moves.
     *
     * @return the arraylist (stack) of moves.
     */
    public ArrayList<TwentyFourtyEightBoard> getMoves() {
        return this.moves;
    }

    /**
     * Push a move onto the stack.
     *
     * @param i, push the TwentyFourtyEightBoard representing the move done onto the stack.
     */
    public void push(TwentyFourtyEightBoard i) {
        moves.add(i);
    }

    /**
     * Pop a move off the stack and return the value of the popped move.
     *
     * @return the most recently pushed move.
     */
    public TwentyFourtyEightBoard pop() {
        Log.d("Undoing", "popping");
        return moves.remove(moves.size() - 1);
    }

    /**
     * Method to check if the stack is empty.
     *
     * @return returns true if there are no more moves in the stack.
     */
    public boolean isEmpty() {
        return moves.size() == 0;
    }

}
