package fall2018.csc2017.slidingtiles;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A test class for SlidingTilesManager
 **/
public class BoardManagerTest {

    /**
     * The manager for testing.
     */
    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private SlidingBoard setUpCorrect() {
        List<Tile> tiles = makeTiles(4);
        SlidingBoard board = new SlidingBoard(tiles, 4);
        boardManager = new BoardManager(new SlidingTilesGameState(board, 0));
        return board;
    }

    /**
     * Make a list of tiles that are not in row major order
     **/
    private List<Tile> makeTilesNotOver(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = complexity * complexity;
        tiles.add(new Tile(boardManager.getBlankId()));
        for (int tileNum = 1; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1));
        }

        return tiles;
    }

    /**
     * Make a unsolved Board
     */
    private void setUpNotOver() {
        List<Tile> tiles = makeTilesNotOver(4);
        SlidingBoard board = new SlidingBoard(tiles, 4);
        boardManager = new BoardManager(new SlidingTilesGameState(board, 0));
    }


    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testGameOver() {
        setUpCorrect();
        assertEquals(true, boardManager.gameOver());
        boardManager.touchMove(14, false);
        assertEquals(false, boardManager.gameOver());
    }

    /**
     * Test whether autosave automatically stores data
     **/
    @Test
    public void autoSave() {
        setUpCorrect();
        boardManager.autoSave(30);
        int actual = 30;
        Assert.assertEquals(actual, boardManager.getGameState().getTimeElapsed());

    }

    /**
     * Test whether a valid tap is correctly identified
     **/
    @Test
    public void testIsValidTap() {
        setUpNotOver();
        assertTrue(boardManager.isValidTap(1));
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(15));
        assertEquals(false, boardManager.isValidTap(10));
    }

    /**
     * Test to see if the board is gotten correctly
     **/
    @Test
    public void testGetBoard() {
        SlidingBoard board = setUpCorrect();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                assertEquals(board.tiles[i][j].getBackgroundId(),
                        boardManager.getBoard().tiles[i][j].getBackgroundId());
            }
        }
    }

    /**
     * Test if the correct score is gotten
     **/
    @Test
    public void testGetScore() {
        setUpCorrect();
        assertEquals(0, boardManager.getScore());
    }

    /**
     * Test if undo correctly restores the board to previous state
     **/
    @Test
    public void testUndo() {
        SlidingBoard board = setUpCorrect();
        boardManager.touchMove(15, true);
        boardManager.undo(1);
        int[] correctTileId = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 25};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(correctTileId[4 * i + j],
                        boardManager.getBoard().tiles[i][j].getBackgroundId());
            }
        }

    }


    /**
     * Test of touchMove correctly makes a move
     **/
    @Test
    public void testTouchMove() {
        SlidingBoard board = setUpCorrect();
        boardManager.touchMove(15, false);
        int[] correctTileId = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 25, 15};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(correctTileId[4 * i + j],
                        boardManager.getBoard().tiles[i][j].getBackgroundId());
            }
        }

    }


}