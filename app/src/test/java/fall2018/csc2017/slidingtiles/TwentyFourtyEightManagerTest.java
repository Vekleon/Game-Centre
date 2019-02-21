package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightBoard;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightManager;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightTile;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class for TwentyFourtyEightManager
 **/
public class TwentyFourtyEightManagerTest {

    /**
     * The manager for testing.
     */
    private TwentyFourtyEightManager twentyFourtyEightManager;

    /**
     * The id's for tiles in 2048
     */
    private static final int TILE_2_ID = 1;
    private static final int TILE_4_ID = 2;
    private static final int TILE_8_ID = 3;
    private static final int TILE_16_ID = 4;
    private static final int WINNING_TILE_ID = 11;

    /**
     * Make a manager for setup
     **/

    private void setUpManager() {
        //twentyFourtyEightManager = new TwentyFourtyEightManager(4, ArrayList<ProxyBitmap> backgrounds, Bitmap blankBitmap)
        twentyFourtyEightManager = new TwentyFourtyEightManager(4);
    }

    /**
     * Set up a board with  2048 tile
     **/
    private TwentyFourtyEightBoard setUpWinningBoard() {
        List<Tile> tiles = new ArrayList<>();
        Tile winningTile = new Tile(WINNING_TILE_ID);
        Tile otherTile = new Tile(TILE_2_ID);

        tiles.add(winningTile);

        // Fill the rest of the board with a "2" tile
        for (int i = 0; i < 16; i++) {
            tiles.add(otherTile);
        }
        return new TwentyFourtyEightBoard(tiles, 4);
    }

    /**
     * Set up a line with no merge
     **/
    private TwentyFourtyEightTile[] setUpLineNoMerge() {
        TwentyFourtyEightTile[] tiles = new TwentyFourtyEightTile[4];
        tiles[0] = new TwentyFourtyEightTile(TILE_2_ID);
        tiles[1] = new TwentyFourtyEightTile(TILE_4_ID);
        tiles[2] = new TwentyFourtyEightTile(TILE_8_ID);
        tiles[3] = new TwentyFourtyEightTile(TILE_16_ID);
        return tiles;
    }

    /**
     * Set up a line that will get merged
     **/
    private Tile[] setUpLineMerge() {
        Tile[] tiles = new Tile[4];
        tiles[0] = new Tile(TILE_2_ID);
        tiles[1] = new Tile(TILE_2_ID);
        tiles[2] = new Tile(TILE_8_ID);
        tiles[3] = new Tile(TILE_16_ID);
        return tiles;
    }

    /**
     * Test whether gameOver returns true on a win
     */
    @Test
    public void testGameOverTwentyFourtyEightOnBoard() {
        setUpManager();
        TwentyFourtyEightBoard board = setUpWinningBoard();
        twentyFourtyEightManager.setBoard(board);
        assertTrue(twentyFourtyEightManager.gameOver());
    }

    /**
     * Test whether a tile is added if there is room
     */
    @Test
    public void testAddTile() {
        setUpManager();
        twentyFourtyEightManager.addTile();
        Board newBoard = twentyFourtyEightManager.getBoard();
        assertEquals(13, ((TwentyFourtyEightBoard) newBoard).numBlank());

    }

    /**
     * Test to see if addTile preserves the board if there is no room for new Tiles
     */
    @Test
    public void testAddTileFullBoard() {
        setUpManager();
        List<Tile> tiles = new ArrayList<>();
        // A "2048" tile.
        Tile winningTile = new Tile(WINNING_TILE_ID);
        // A "2" tile.
        Tile otherTile = new Tile(TILE_2_ID);

        tiles.add(winningTile);
        for (int i = 0; i < 16; i++) {
            tiles.add(otherTile);
        }
        TwentyFourtyEightBoard newBoard = new TwentyFourtyEightBoard(tiles, 4);
        twentyFourtyEightManager.setBoard(newBoard);
        twentyFourtyEightManager.addTile();
        Board changedBoard = twentyFourtyEightManager.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(newBoard.tiles[i][j], changedBoard.tiles[i][j]);
            }
        }

    }

    /**
     * Test whether addScore adds correctly
     */
    @Test
    public void testaddScore() {
        setUpManager();
        twentyFourtyEightManager.addScore(100);
        assertEquals(100, twentyFourtyEightManager.getScore());
    }

    /**
     * Test whether isValidTap returns True on a correct move
     */
    @Test
    public void testisValidTapCorrect() {
        setUpManager();
        assertTrue(twentyFourtyEightManager.isValidTap(2));
    }


    /**
     * Test whether isValidTap returns False on an incorrect move
     */
    @Test
    public void testisValidTapFalse() {
        setUpManager();
        assertFalse(twentyFourtyEightManager.isValidTap(6));
    }

    /**
     * Test if mergeLine preserves line if no merge occurs
     */
    @Test
    public void testMergeLineNoChange() {
        setUpManager();
        TwentyFourtyEightTile[] original = setUpLineNoMerge();
        TwentyFourtyEightTile[] merged = twentyFourtyEightManager.mergeLine(original, 0, true);
        assertEquals(0, twentyFourtyEightManager.getScore());
        assertArrayEquals(original, merged);
    }

    /**
     * Test if mergeLine appropriately merges a line
     */
    @Test
    public void testMergeLineChange() {
        setUpManager();
        TwentyFourtyEightTile[] original = setUpLineNoMerge();
        TwentyFourtyEightTile[] merged = twentyFourtyEightManager.mergeLine(original, 0, false);
        // Check that score is updated correctly
        assertEquals(2, twentyFourtyEightManager.getScore());

        assertEquals(4, merged[0].getBackgroundId());
        assertEquals(8, merged[1].getBackgroundId());
        assertEquals(16, merged[2].getBackgroundId());
        assertEquals(25, merged[3].getBackgroundId());
    }


}
