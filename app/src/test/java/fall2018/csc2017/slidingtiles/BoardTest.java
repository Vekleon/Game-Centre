package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * A test class for generic Board
 **/
public class BoardTest {
    /**
     * The Board for testing.
     */
    private Board board;

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
     * Create a 3x3 board for testing purposes
     **/
    private void setUpSmallBoard() {
        board = new SlidingBoard(makeTiles(3), 3);
    }

    /**
     * Manually change the tile on the board
     **/
    private void changeTile() {
        board.tiles[1][1] = board.tiles[2][3];
    }

    /**
     * Test whether getTile gets the correct tile
     */
    @Test
    public void testGetTile() {
        setUpBoard();
        assertEquals(6, board.getTile(1, 1).getBackgroundId());
        changeTile();
        assertEquals(12, board.getTile(1, 1).getBackgroundId());
    }

    /**
     * Test whether a 4x4 board was created properly
     **/
    @Test
    public void testConstructor() {
        setUpBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals((4 * i) + j, board.tiles[i][j].getBackgroundId());
            }
        }

    }

    /**
     * Test whether a 3x3 board was created properly
     **/
    @Test
    public void testConstructorSmall() {
        setUpSmallBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals((3 * i) + j, board.tiles[i][j].getBackgroundId());
            }
        }

    }


    /**
     * Test whether the correct complexity is returned
     */
    @Test
    public void testGetComplexity() {
        setUpBoard();
        assertEquals(4, board.getComplexity());
    }


}
