package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.memorytiles.MemoryTile;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTileBoard;

import static org.junit.Assert.assertEquals;


/**
 * A test class for memoryTile board
 **/
public class MemoryTileBoardTest {
    /**
     * The board for testing.
     */
    private MemoryTileBoard board;

    /**
     * Make a tile list for setup
     **/

    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        int numPictures = numTiles / 2;

        for (int tileNum = 0; tileNum != numPictures; tileNum++) {
            tiles.add(new Tile(tileNum));
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    /**
     * Make a tile list with flipped tiles for testing
     **/
    private List<Tile> makeTilesWithFlippedTile(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        int numPictures = numTiles / 2;

        for (int tileNum = 0; tileNum != numPictures; tileNum++) {
            MemoryTile flippedTile = new MemoryTile(tileNum);
            flippedTile.flip();
            tiles.add(flippedTile);
            tiles.add(new Tile(tileNum));
        }
        return tiles;
    }

    /**
     * Create a new board for testing purposes
     **/
    private void setUpBoard() {
        board = new MemoryTileBoard(makeTiles(4), 4);
    }

    /**
     * Create a new board with some flipped tiles
     **/
    private void setUpBoardWithFlip() {
        board = new MemoryTileBoard(makeTilesWithFlippedTile(4), 4);
    }


    /**
     * Test whether update numFlipped preserves numFlipped when no change occurs.
     */
    @Test
    public void testUpdateNumFlippedNoChange() {
        setUpBoard();
        board.updateNumFlipped();
        assertEquals(0, board.getNumFlipped());

    }

    /**
     * Test whether update numFlipped correctly identifies flipped tiles on a board
     */
    @Test
    public void testUpdateNumFlipped() {
        setUpBoardWithFlip();
        board.updateNumFlipped();
        assertEquals(8, board.getNumFlipped());

    }


}