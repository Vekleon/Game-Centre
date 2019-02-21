package fall2018.csc2017.slidingtiles;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.GameState;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * A test class for generic gamestate
 **/
public class GameStateTest {

    /**
     * The gamestate for testing.
     */
    private GameState gameState;

    /**
     * Make a tile list for setup
     **/

    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1));
        }
        return tiles;
    }

    /**
     * Create a new gamestate for testing purposes
     **/
    private void setUpCorrectState() {
        Board board = new SlidingBoard(makeTiles(4), 4);
        gameState = new GameState(board, 0);
    }

    /**
     * Create a different board for testing purposes
     **/
    private Board setUpNewBoard() {
        return new SlidingBoard(makeTiles(5), 5);
    }

    /**
     * Test whether getSlidingBoard returns the correct board.
     */
    @Test
    public void testGetSlidingBoard() {
        setUpCorrectState();
        assertNotNull(gameState.getBoard());
        Board board = new SlidingBoard(makeTiles(4), 4);
        assertSame(board, gameState.getBoard());
    }

    /**
     * Test whether the correct time is returned
     */
    @Test
    public void testGetTimeElapsed() {
        setUpCorrectState();
        int correctTime = 0;
        Assert.assertEquals(correctTime, gameState.getTimeElapsed());
    }


    /**
     * Test whether changeState changes the current state correctly
     */
    @Test
    public void testChangeState() {
        setUpCorrectState();
        gameState.changeState(setUpNewBoard(), 10);
        assertSame(setUpNewBoard(), gameState.getBoard());
        int correctNewTime = 10;
        Assert.assertEquals(correctNewTime, gameState.getTimeElapsed());

    }

}


