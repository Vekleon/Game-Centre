package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.memorytiles.MemoryTile;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTileBoard;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTilesManager;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class for MemoryTilesManager
 **/
public class MemoryTilesManagerTest {

    /**
     * The manager for testing.
     */
    private MemoryTilesManager memoryTilesManager;

    /**
     * Make a manager for setup
     **/

    private void setUpManager() {
        memoryTilesManager = new MemoryTilesManager(4);
    }


    /**
     * Create a board that is completely flipped up
     **/
    private MemoryTileBoard setUpFinishedGame() {
        List<Tile> tiles = new ArrayList<>();
        MemoryTile tile = new MemoryTile(3);
        tile.flip();
        for (int i = 0; i < 16; i++) {
            tiles.add(tile);
        }
        return new MemoryTileBoard(tiles, 4);
    }

    /**
     * Create a board full of matches, flipped down
     **/
    private MemoryTileBoard setUpGame() {
        List<Tile> tiles = new ArrayList<>();
        MemoryTile tile = new MemoryTile(3);
        for (int i = 0; i < 16; i++) {
            tiles.add(tile);
        }
        return new MemoryTileBoard(tiles, 4);
    }


    /**
     * Test if score is being added correctly
     **/
    @Test
    public void testAddScore() {
        setUpManager();
        memoryTilesManager.addScore(100);
        assertEquals(100, memoryTilesManager.getScore());
    }


    /**
     * Test that a successful match is recognized
     **/
    @Test
    public void testSuccessfulMatchTrue() {
        setUpManager();
        Tile tileOne = new Tile(3);
        Tile tileTwo = new Tile(3);
        assertTrue(memoryTilesManager.tilesMatch(tileOne, tileTwo));
    }

    /**
     * Test that an unsuccessful match is recognized
     **/
    @Test
    public void testSuccessfulMatchFalse() {
        setUpManager();
        Tile tileOne = new Tile(3);
        Tile tileTwo = new Tile(5);
        assertEquals(false, memoryTilesManager.tilesMatch(tileOne, tileTwo));
    }


    /**
     * Test that getposition correctly converts ints into coordinates
     **/
    @Test
    public void testGetPosition() {
        setUpManager();
        int[] actual = memoryTilesManager.getPosition(7);
        int[] expected = {1, 3};
        assertArrayEquals(expected, actual);
    }

    /**
     * Test if an invalid tap is recognized
     **/
    @Test
    public void testIsValidTapFalse() {
        setUpManager();
        Board memoryTileBoard = memoryTilesManager.getBoard();
        Tile currTileTapped = ((MemoryTileBoard) memoryTileBoard).tiles[0][0];
        boolean isFlipped = ((MemoryTile) currTileTapped).isFlipped();
        assertEquals(!isFlipped, memoryTilesManager.isValidTap(0));
    }

    /**
     * Test if a non finished game is recognized
     **/
    @Test
    public void testGameOverFalse() {
        setUpManager();
        assertFalse(memoryTilesManager.gameOver());
    }

    /**
     * Test if a finished game is recognized
     **/
    @Test
    public void testGameOver() {
        setUpManager();
        memoryTilesManager.setBoard(setUpFinishedGame());
        assertTrue(memoryTilesManager.gameOver());
    }

    /**
     * Test of a two sequence matching move correctly flips matched tiles
     **/
    @Test
    public void testTouchMove() {
        setUpManager();
        memoryTilesManager.setBoard(setUpGame());
        memoryTilesManager.touchMove(0, false);
        memoryTilesManager.touchMove(1, false);

        Board memoryTileBoard = memoryTilesManager.getBoard();
        assertTrue(((MemoryTileBoard) memoryTileBoard).getNumFlipped() == 2);

    }


}
