package fall2018.csc2017.slidingtiles.memorytiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.slidingtiles.GameManager;
import fall2018.csc2017.slidingtiles.Tile;
import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.gamestates.MemoryTileGameState;

public class MemoryTilesManager extends GameManager {

    /**
     * Variable that holds the tile another tile is to match
     */
    private MemoryTile tileToMatchTo = null;

    private MemoryTile otherTile = null;

    /**
     * The current score
     */
    private int score = 0;

    /**
     * Manage a Memory Tile board that has been pre-populated
     *
     * @param gameState the state to load from
     */
    public MemoryTilesManager(GameState gameState) {
        this.board = gameState.getBoard();
        setGameState(gameState);
    }

    /**
     * Generate and populate a memory tile board
     *
     * @param complexity complexity of the board
     */
    public MemoryTilesManager(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;

        for (int tileNum = 0; tileNum < numTiles; tileNum++)
            tiles.add(new MemoryTile(tileNum / 2));

        Collections.shuffle(tiles);
        board = new MemoryTileBoard(tiles, complexity);
        this.gameState = new MemoryTileGameState((MemoryTileBoard) board, 0);
    }

    /**
     * Return whether the game is over.
     *
     * @return whether the game is over
     */
    public boolean gameOver() {
        return ((MemoryTileBoard) board).getNumFlipped() == board.getNumTiles();
    }


    /**
     * Return true iff the tapped tile is blank
     **/
    public boolean isValidTap(int move) {
        if(otherTile != null){
            reset();
        }
        int[] pos = getPosition(move);
        return !((MemoryTile) board.getTile(pos[0], pos[1])).isFlipped();
    }

    /**
     * Get the position of a given move, split into x and y co-ords.
     *
     * @param move the integer representation of a move
     * @return the move split into x and y co-ords
     */
    public int[] getPosition(int move) {
        int[] result = new int[2];
        result[0] = move / board.getComplexity();
        result[1] = move % board.getComplexity();
        return result;
    }

    /**
     * @param move - the move to be made
     * @param undo whether or not we are undoing the move
     */
    public void touchMove(int move, boolean undo) {
        int[] pos = getPosition(move);
        MemoryTile touchedTile = (MemoryTile) board.getTile(pos[0], pos[1]);
        touchedTile.flip();
        if (tileToMatchTo != null) {
            if (tilesMatch(touchedTile, tileToMatchTo)) {
                this.addScore(200);
                tileToMatchTo = null;
            } else {
                this.addScore(-20);
                otherTile = touchedTile;
            }

        } else {
            tileToMatchTo = touchedTile;
        }
        ((MemoryTileBoard) board).updateNumFlipped();
        setChanged();
        notifyObservers();
    }

    private void reset(){
        otherTile.flip();
        tileToMatchTo.flip();
        otherTile = null;
        tileToMatchTo = null;
    }

    /**
     * Checks if the two given tile are a pair
     *
     * @param tile1 first tile
     * @param tile2 second tile
     * @return whether the two tiles have the same background
     */
    public boolean tilesMatch(Tile tile1, Tile tile2) {
        return tile1.getBackgroundId() == tile2.getBackgroundId();
    }

    /**
     * Increment the score counter by a passed inn int
     *
     * @param score the amount the score is to be increased
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Getter for the score
     *
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Check if the game is complete
     *
     * @return whether or not the game is over
     */
    public boolean hasWon() {
        return gameOver();
    }
}

