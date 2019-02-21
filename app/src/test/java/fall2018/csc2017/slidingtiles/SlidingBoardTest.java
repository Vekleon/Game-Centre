package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * A test class for SlidingTiles board
 **/
public class SlidingBoardTest {
    /**
     * The board for testing.
     */
    private SlidingBoard board;

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
     * Create a new board for testing purposes
     **/
    private void setUpBoard() {
        board = new SlidingBoard(makeTiles(4), 4);
    }


    /**
     * Create a solvable board
     **/
    private void setUpSolvable() {
        int[] solvableTiles = {13, 2, 10, 3, 1, 12, 8, 4, 5, 16, 9, 6, 15, 14, 11, 7};
        ArrayList<Tile> solvableConfiguration = new ArrayList<>();
        for (int i = 0; i < solvableTiles.length; i++) {
            solvableConfiguration.add(new Tile(solvableTiles[i]));
        }
        board = new SlidingBoard(solvableConfiguration, 4);
    }

    /**
     * Create a nonsolvable board
     **/
    private void setUpNonSolvable() {
        int[] nonSolvableTiles = {3, 9, 1, 15, 14, 11, 4, 6, 13, 16, 10, 12, 2, 7, 8, 5};
        ArrayList<Tile> nonSolvableConfiguration = new ArrayList<>();
        for (int i = 0; i < nonSolvableTiles.length; i++) {
            nonSolvableConfiguration.add(new Tile(nonSolvableTiles[i]));
        }
        board = new SlidingBoard(nonSolvableConfiguration, 4);
    }

    /**
     * Test whether swap tiles correctly swaps tiles
     */
    @Test
    public void testSwapTiles() {
        setUpBoard();
        Tile firsTile = board.getTile(1, 1);
        Tile secondTile = board.getTile(2, 2);
        board.swapTiles(1, 1, 2, 2);
        assertEquals(secondTile, board.getTile(1, 1));
        assertEquals(firsTile, board.getTile(2, 2));
    }

    /**
     * Test whether isSolvable correctly identifies nonSolvable board
     */
    @Test
    public void testisSolvableFalse() {
        setUpNonSolvable();
        assertFalse(board.isSolvable());
    }


    /**
     * Test whether isSolvable correctly identifies solvable board
     */
    @Test
    public void testisSolvableTrue() {
        setUpSolvable();
        assertTrue(board.isSolvable());
    }

    /**
     * Test if the board is correctly converted into a one dimensional array
     **/
    @Test
    public void testToOneDimensionalArray() {
        setUpSolvable();
        int[] tileIdOrder = {13, 2, 10, 3, 1, 12, 8, 4, 16, 9, 6, 15, 14, 11, 7};
        Tile[] actualTiles = new Tile[16];
        for (int i = 0; i < tileIdOrder.length; i++) {
            actualTiles[i] = new Tile(tileIdOrder[i]);
        }
        assertArrayEquals(actualTiles, board.toOneDimensionalArray());


    }

    /**
     * Test if blankisEvenRow correctly return true
     **/
    @Test
    public void testBlankIsEvenRowTrue() {
        setUpSolvable();
        try {
            assertTrue(board.blankIsEvenRow());
        } catch (NoBlankTileException e) {
            fail("No Blank Tile");
        }
    }

    /**
     * Test if blankisEvenRow correctly returns false
     **/
    @Test
    public void testBlankIsEvenRowFalse() {
        setUpBoard();
        try {
            assertFalse(board.blankIsEvenRow());
        } catch (NoBlankTileException e) {
            fail("No Blank Tile");
        }
    }

    /**
     * Test if numInversions works on a scrammbled board
     **/
    @Test
    public void testNumInversions() {
        setUpSolvable();
        assertEquals(44, board.numInversions());
    }

    /**
     * Test if numInversions works on a solved board
     **/
    @Test
    public void testNumInversionsSolvedBoard() {
        setUpBoard();
        assertEquals(0, board.numInversions());
    }


}
