package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /** The board manager for testing. */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 16;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private SlidingBoard setUpCorrect() {
        List<Tile> tiles = makeTiles();
        SlidingBoard board = new SlidingBoard(tiles, 4);
        boardManager = new BoardManager(new SlidingTilesGameState(board, 0));
        return board;
    }



    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testGameOver(){
        setUpCorrect();
        assertEquals(true, boardManager.gameOver());
        boardManager.touchMove(14, false);
        assertEquals(false, boardManager.gameOver());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getBackgroundId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getBackgroundId());
        ((SlidingBoard) boardManager.getBoard()).swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getBackgroundId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getBackgroundId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getBackgroundId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getBackgroundId());
        ((SlidingBoard) boardManager.getBoard()).swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getBackgroundId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getBackgroundId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(15));
        assertEquals(false, boardManager.isValidTap(10));
    }
}

