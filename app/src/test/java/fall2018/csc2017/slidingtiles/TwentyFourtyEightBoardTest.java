package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightBoard;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightTile;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * A test class for TwentyFourtyEightBoard
 **/
public class TwentyFourtyEightBoardTest {
    /**
     * The board for testing.
     */
    private TwentyFourtyEightBoard board;

    /**
     * The id's for tiles in 2048
     */
    private static final int BLANKTILE = 0;
    private static final int TILE_2_ID = 1;
    private static final int TILE_4_ID = 2;
    private static final int TILE_8_ID = 3;
    private static final int TILE_16_ID = 4;
    private static final int TILE_32_ID = 5;
    private static final int WINNING_TILE_ID = 11;

    /**
     * Make an array of tiles that is full, with possible move
     **/
    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        tiles.add(new Tile(TILE_2_ID));
        tiles.add(new Tile(TILE_2_ID));
        tiles.add(new Tile(TILE_2_ID));

        for (int tileNum = 3; tileNum != 11; tileNum++) {
            tiles.add(new Tile(2^tileNum));
        }
        tiles.add(new Tile(TILE_2_ID));
        tiles.add(new Tile(TILE_4_ID));
        tiles.add(new Tile(TILE_8_ID));
        tiles.add(new Tile(TILE_16_ID));
        tiles.add(new Tile(TILE_32_ID));
        return tiles;
    }

    /**
     * Setup a row of tiles with no blanktile
     **/
    private TwentyFourtyEightTile[] makeLineNomove() {
        TwentyFourtyEightTile[] tileLine = new TwentyFourtyEightTile[4];
        tileLine[0] = new TwentyFourtyEightTile(TILE_2_ID);
        tileLine[1] = new TwentyFourtyEightTile(TILE_2_ID);
        tileLine[2] = new TwentyFourtyEightTile(TILE_2_ID);
        tileLine[3] = new TwentyFourtyEightTile(TILE_2_ID);
        return tileLine;

    }

    /**
     * Setup a row of tiles with a blanktile
     **/
    private TwentyFourtyEightTile[] makeLineWithMove() {
        TwentyFourtyEightTile[] tileLine = new TwentyFourtyEightTile[4];
        tileLine[0] = new TwentyFourtyEightTile(BLANKTILE);
        tileLine[1] = new TwentyFourtyEightTile(TILE_2_ID);
        tileLine[2] = new TwentyFourtyEightTile(TILE_2_ID);
        tileLine[3] = new TwentyFourtyEightTile(TILE_2_ID);

        return tileLine;
    }
    /**
     * The rotated tile list corresponding to the board created
     **/
    private List<TwentyFourtyEightTile> rotatedTiles() {
        int[] rotatedNumbers = {8, 128, 2, 32, 2, 64, 1024, 16, 2, 32, 512, 8, 2, 16, 256, 4};
        List<TwentyFourtyEightTile> tiles = new ArrayList<>();
        for (int i = 0; i < rotatedNumbers.length; i++) {
            tiles.add(new TwentyFourtyEightTile(rotatedNumbers[i]));
        }
        return tiles;
    }


    /**
     * Create a new board for testing purposes
     **/
    private void setUpBoard() {
        board = new TwentyFourtyEightBoard(makeTiles(4), 4);
    }


    /**
     * Test if rotate right correctly modifies the board
     **/
    @Test
    public void testRotateRight() {
        setUpBoard();
        List<TwentyFourtyEightTile> rotatedTiles = rotatedTiles();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(rotatedTiles.get(4 * i + j), board.tiles[i][j]);
            }
        }
    }

    /**
     * Test that change row correctly mutates the board
     **/
    @Test
    public void testChangeRow() {
        setUpBoard();
        TwentyFourtyEightTile newTile = new TwentyFourtyEightTile(WINNING_TILE_ID);
        TwentyFourtyEightTile[] newRow = {newTile, newTile, newTile, newTile};
        board.changeRow(0, newRow);
        assertArrayEquals(newRow, board.tiles[0]);

    }

    /**
     * Test that the correct row is extracted from the board
     **/
    @Test
    public void testGetLine() {
        setUpBoard();
        Tile twoTile = new Tile(TILE_2_ID);
        Tile eightTile = new Tile(TILE_8_ID);
        Tile[] expectedFirstRow = {twoTile, twoTile, twoTile, eightTile};
        Tile[] actualFirstRow = board.getLine(0);
        assertArrayEquals(expectedFirstRow, actualFirstRow);

    }

    /**
     * Test that the moveline returns the same line if no movement occurs
     **/
    @Test
    public void testMoveLineNomove() {
        TwentyFourtyEightTile[] original = makeLineNomove();
        TwentyFourtyEightTile[] modified = board.moveLine(original);
        assertArrayEquals(original, modified);

    }

    /**
     * Test that the moveline returns the same line if movement occurs
     **/
    @Test
    public void testMoveLineMove() {
        TwentyFourtyEightTile[] original = makeLineNomove();
        TwentyFourtyEightTile[] modified = board.moveLine(original);
        int[] expected = {2, 2, 2, 25};
        for (int i = 0; i < 4; i++) {
            assertEquals(expected[i], modified[i].getBackgroundId());

        }
    }

    /**
     * Test that ensure size correctly adjusts size and content of a row of tiles
     **/
    @Test
    public void testEnsureSize() {
        List<TwentyFourtyEightTile> original = new ArrayList<>();
        // original.add(new Tile(2, );
        board.ensureSize(original, 4);
        assertEquals(4, original.size());
        for (int i = 1; i < 4; i++) {
            assertEquals(BLANKTILE, original.get(i).getBackgroundId());
        }

    }

    /**
     * Test that compare returns true for the same row
     **/
    @Test
    public void testCompareTrue() {
        TwentyFourtyEightTile[] firstRow = makeLineNomove();
        TwentyFourtyEightTile[] secondRow = makeLineNomove();
        assertTrue(board.compare(firstRow, secondRow));

    }

    /**
     * Test that compare returns false for different
     **/
    @Test
    public void testCompareFalse() {
        TwentyFourtyEightTile[] firstRow = makeLineNomove();
        TwentyFourtyEightTile[] secondRow = makeLineWithMove();
        assertFalse(board.compare(firstRow, secondRow));

    }

}
