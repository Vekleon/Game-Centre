package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * A test class for sliding tiles gamestate
 **/
public class SlidingTilesGameStateTest {

    /**
     * The gamestate for testing.
     */
    private SlidingTilesGameState gameState;

    /**
     * Make a tile array for setup
     **/
    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
//            tiles.add(new Tile(tileNum + 1, ));
        }
        return tiles;
    }

    /**
     * Setup the slidingTile state appropriately
     **/
    private void setUpCorrectState() {
        SlidingBoard board = new SlidingBoard(makeTiles(4), 4);
        gameState = new SlidingTilesGameState(board, 0);
    }


    /**
     * Test whether getMoves returns an empty list when initialized.
     */
    @Test
    public void testGetMovesEmpty() {
        setUpCorrectState();
        assertEquals(0, gameState.getMoves().size());
    }

    /**
     * Test whether moves are getting pushed correctly
     **/
    @Test
    public void testPushMove() {
        setUpCorrectState();
        gameState.push(1);
        assertEquals(1, gameState.getMoves().size());
        gameState.push(2);
        assertEquals(2, gameState.getMoves().size());
        assertSame(1, gameState.getMoves().get(0));
        assertSame(2, gameState.getMoves().get(1));
    }

    /**
     * Test whether moves are getting popped correctly
     **/
    @Test
    public void testPopMove() {
        setUpCorrectState();
        gameState.push(1);
        gameState.push(2);
        gameState.push(3);
        assertSame(3, gameState.pop());
        assertSame(2, gameState.pop());
        assertSame(1, gameState.pop());
        assertEquals(0, gameState.getMoves().size());

    }

    /**
     * Test whether the check for no moves is correct
     **/
    @Test
    public void testIsEmpty() {
        setUpCorrectState();
        assertTrue(gameState.isEmpty());
        gameState.push(1);
        assertFalse(gameState.isEmpty());
        gameState.pop();
        assertTrue(gameState.isEmpty());

    }

}


