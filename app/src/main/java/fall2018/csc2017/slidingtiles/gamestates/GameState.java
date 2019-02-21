package fall2018.csc2017.slidingtiles.gamestates;

import java.io.Serializable;

import fall2018.csc2017.slidingtiles.Board;

/**
 * Keeps track of the properties of the saved game session
 *
 * @author Thomas Leung
 * @version 1.0
 * @since 2018-10-30
 */
public class GameState implements Serializable {
    private Board board;
    private int seconds;

    /**
     * Initializes the gamestate with a given board and time
     *
     * @param board   the board the state is to be initialized to
     * @param seconds the time elapsed for the given board to be
     */
    public GameState(Board board, int seconds) {
        this.board = board;
        this.seconds = seconds;
    }

    /**
     * Sets the new state of the board and the new time that has elapsed
     *
     * @param board   the state of the current board
     * @param seconds the amount of seconds that has elapsed
     */
    public void changeState(Board board, int seconds) {
        this.board = board;
        this.seconds = seconds;
    }

    /**
     * Returns the state of the board at the given state
     *
     * @return the board at the provided save point
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the time elapsed at the given saved state
     *
     * @return the time elapsed at the provided save point
     */
    public int getTimeElapsed() {
        return seconds;
    }
}
