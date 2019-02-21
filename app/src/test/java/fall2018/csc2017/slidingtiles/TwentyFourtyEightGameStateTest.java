package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.TwentyFourtyEightGameState;
import fall2018.csc2017.slidingtiles.twentyfourtyeight.TwentyFourtyEightBoard;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class for 2048 gamestate
 **/
public class TwentyFourtyEightGameStateTest {

    /**
     * The gamestate for testing.
     */
    private TwentyFourtyEightGameState gameState;

    private static final int BLANKTILE = 0;
    private static final int TILE_2 = 1;
    private static final int TILE_4 = 2;
    private static final int TILE_8 = 3;
    private static final int TILE_16 = 4;
    private static final int TILE_32 = 5;
    private static final int TILE_64 = 6;
    private static final int TILE_128 = 7;
    private static final int TILE_256 = 8;

    /**
     * Make an array of tiles that are non reducible, with a blank tile as the first three entries
     **/
    private List<Tile> makeTilesWithBlankRoom(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        Tile blankTile = new Tile(BLANKTILE);
        tiles.add(blankTile);
        tiles.add(blankTile);
        tiles.add(blankTile);

        for (int tileNum = 1; tileNum != 11; tileNum++) {
            tiles.add(new Tile(2^tileNum));
        }

        tiles.add(new Tile(TILE_2));
        tiles.add(new Tile(TILE_4));
        tiles.add(new Tile(TILE_8));
        tiles.add(new Tile(TILE_16));
        tiles.add(new Tile(TILE_32));
        return tiles;
    }

    /** Make an array of tiles that is full, with possible move**/
    private List<Tile> makeTilesWithNoBlankCanMove(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;

        tiles.add(new Tile(TILE_2));
        tiles.add(new Tile(TILE_2));
        tiles.add(new Tile(TILE_2));

        for (int tileNum = 3; tileNum != 11; tileNum++) {
            tiles.add(new Tile(2^tileNum));
        }
        // tiles.add(2)
        //tiles.add(4)
        //tiles.add(8)
        //tiles.add(16)
        //tiles.add(32)
        return tiles;
    }

    /** Make an array of tiles that is full, with no possible move**/
    private List<Tile> makeTilesWithNoMoves(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != 4; tileNum++) {
            if (tileNum % 2 == 0) {
                tiles.add(new Tile(TILE_2));
                tiles.add(new Tile(TILE_4));
                //tiles.add(6) fix this
                tiles.add(new Tile(TILE_8));
            } else {
                tiles.add(new Tile(TILE_32));
                tiles.add(new Tile(TILE_64));
                tiles.add(new Tile(TILE_128));
                tiles.add(new Tile(TILE_256));
            }
        }
        return tiles;
    }

    private void setUpCorrectStateBlankRoom() {
        TwentyFourtyEightBoard board = new TwentyFourtyEightBoard(makeTilesWithBlankRoom(4), 4);
        gameState = new TwentyFourtyEightGameState(board, 0);
    }

    private void setUpCorrectStateNoBlankCanMove() {
        TwentyFourtyEightBoard board = new TwentyFourtyEightBoard(makeTilesWithNoBlankCanMove(4), 4);
        gameState = new TwentyFourtyEightGameState(board, 0);
    }

    private void setUpCorrectStateNoMoves() {
        TwentyFourtyEightBoard board = new TwentyFourtyEightBoard(makeTilesWithNoMoves(4), 4);
        gameState = new TwentyFourtyEightGameState(board, 0);
    }


    /**
     * Test whether canMove works with blank tile available
     */
    @Test
    public void testCanMoveWithBlank() {
       setUpCorrectStateBlankRoom();
       assertTrue(gameState.canMove());
    }

    /** Test whether canMove works with moves on a full board**/
    @Test
    public void testCanMoveNoBlank() {
        setUpCorrectStateNoBlankCanMove();
        assertTrue(gameState.canMove());
    }

    /** Test whether canMove works with no moves**/
    @Test
    public void testCanMoveNoMovesPossible() {
        setUpCorrectStateNoMoves();
        assertFalse(gameState.canMove());
    }

}