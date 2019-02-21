package fall2018.csc2017.slidingtiles;

import java.util.Observable;

import fall2018.csc2017.slidingtiles.gamestates.GameState;

/**
 * Manage a general game, including starting a new game, checking for a win, and making moves
 */
public abstract class GameManager extends Observable implements java.io.Serializable {

    /**
     * The gamestate being managed
     **/
    protected GameState gameState;

    /**
     * The save index under game-center loaded games
     **/
    private int saveId;

    protected Board board;

    /**
     * Create a new game, with a new gamestate, saved into saveId
     **/
    protected GameManager() {

    }

    public void setGameState(GameState state){
        this.gameState = state;
    }

    /**
     * Return the current gamestate
     **/
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Return the game's saveId
     **/
    public int getSaveId() {
        return this.saveId;
    }

    /**
     * Return whether the game is over.
     *
     * @return whether the game is over
     */
    public abstract boolean gameOver();

    /**
     * @return Whether the user won the game
     */
    public abstract boolean hasWon();

    /**
     * Return whether the move is a valid move to make
     *
     * @param move - the move to make
     * @return whether the move can be made
     */
    public abstract boolean isValidTap(int move);



    /**
     * Process a move and change the game state accordingly
     *
     * @param move - the move to be made
     */
    public abstract void touchMove(int move, boolean undo);


    /**
     * Return the score associated with the current gamestate
     **/
    public abstract int getScore();

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }
}