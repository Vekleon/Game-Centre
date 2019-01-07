package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import java.lang.Math;

/**
 * The abstract class responsible for managing a boards funnctionality
 *
 * @author Group_0645
 * @since 2018-11-23
 */
class BoardManager extends GameManager implements Serializable {

    /**
     * The gamestate of the current board
     */
    private SlidingTilesGameState gameState;

    /**
     * The tile ID of the blank tile
     */
    private final int blankId;

    /**
     * Manage a slidingBoard that has been pre-populated.
     *
     * @param gameState the game state to load from
     */
    BoardManager(GameState gameState) {
        this.board = gameState.getBoard();
        this.gameState = (SlidingTilesGameState) gameState;

        int complexity = board.getComplexity();
        blankId = complexity * complexity - 1;
    }

    /**
     * Manage a new shuffled slidingBoard.
     *
     * @param complexity the complexity of the board
     */
    BoardManager(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        blankId = complexity * complexity - 1;
        for (int tileNum = 0; tileNum != numTiles; tileNum++)
            tiles.add(new Tile(tileNum));

        Collections.shuffle(tiles);
        this.board = new SlidingBoard(tiles, complexity);
        while (!((SlidingBoard) this.board).isSolvable()) {
            Collections.shuffle(tiles);
            this.board = new SlidingBoard(tiles, complexity);
        }

        gameState = new SlidingTilesGameState((SlidingBoard) board, 0);
    }

    /**
     * Retun the score associated with current sliding tile state, outputs higher score for less moves and faster time
     **/
    public int getScore() {
        double timePenalty = this.gameState.getTimeElapsed();
        double movePenalty = this.gameState.getMoves().size();

        double combinedPenalty = Math.pow((timePenalty / 100), 2)
                + Math.pow((movePenalty / 100), 2);
        if (combinedPenalty == 0) {
            return 0;
        }

        return (int) Math.round(1000 / combinedPenalty);
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean gameOver() {
        boolean solved = true;
        Iterator<Tile> boardIter1 = board.iterator();
        Iterator<Tile> boardIter2 = board.iterator();

        // Start boardIter2 one Tile ahead of boardIter1
        boardIter2.next();

        // Check that slidingBoard satisfies row-major order
        while (boardIter1.hasNext() && boardIter2.hasNext()) {
            Tile currTile = boardIter1.next();
            Tile nextTile = boardIter2.next();

            if (currTile.compareTo(nextTile) < 0) {
                solved = false;
                break;
            }

        }

        return solved;
    }

    /**
     * Checks if the game is over
     *
     * @return whether or not the game is over
     */
    public boolean hasWon() {
        return gameOver();
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / board.getComplexity();
        int col = position % board.getComplexity();
        // Are any of the 4 the blank tile?
        Tile[] tiles = getTiles(row, col);

        return (tiles[1] != null && tiles[1].getBackgroundId() == blankId)
                || (tiles[0] != null && tiles[0].getBackgroundId() == blankId)
                || (tiles[2] != null && tiles[2].getBackgroundId() == blankId)
                || (tiles[3] != null && tiles[3].getBackgroundId() == blankId);
    }

    /**
     * Undo counnt times
     *
     * @param undoCount how many steps to undo
     */
    void undo(int undoCount) {
        for (int i = 0; i < undoCount && !gameState.isEmpty(); i++)
            touchMove(gameState.pop(), true);
    }

    /**
     * Process a touch at position in the slidingBoard, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position, boolean undo) {

        int row = position / board.getComplexity();
        int col = position % board.getComplexity();

        // tiles is the blank tile, swap by calling SlidingBoard's swap method.

        // Possible locations for blank tile
        Tile[] tiles = getTiles(row, col);

        // When tile is touched check for valid tap
        if (isValidTap(position)) {
            if (tiles[0] != null && tiles[0].getBackgroundId() == blankId) {
                // Tile above is blank tile
                ((SlidingBoard) board).swapTiles(row, col, row - 1, col);
                if (!undo)
                    gameState.push(position - board.getComplexity());
            } else if (tiles[1] != null && tiles[1].getBackgroundId() == blankId) {
                // Tile below is blank tile
                ((SlidingBoard) board).swapTiles(row, col, row + 1, col);
                if (!undo)
                    gameState.push(position + board.getComplexity());
            } else if (tiles[2] != null && tiles[2].getBackgroundId() == blankId) {
                // Tile left is blank tile
                ((SlidingBoard) board).swapTiles(row, col, row, col - 1);
                if (!undo)
                    gameState.push(position - 1);
            } else if (tiles[3] != null && tiles[3].getBackgroundId() == blankId) {
                // Tile right is blank tile
                ((SlidingBoard) board).swapTiles(row, col, row, col + 1);
                if (!undo)
                    gameState.push(position + 1);
            }
        }
    }

    /**
     * Getter for the collection of tiles
     *
     * @param row number of rows for a board
     * @param col number of columns for a board
     * @return the tiles on the board
     */
    private Tile[] getTiles(int row, int col) {
        Tile[] tiles = new Tile[4];
        tiles[0] = row == 0 ? null : board.getTile(row - 1, col);
        tiles[1] = row == board.getComplexity() - 1 ? null : board.getTile(row + 1, col);
        tiles[2] = col == 0 ? null : board.getTile(row, col - 1);
        tiles[3] = col == board.getComplexity() - 1 ? null : board.getTile(row, col + 1);
        return tiles;
    }

    /**
     * getter for the current gamestate
     *
     * @return the current gamestate
     */
    public SlidingTilesGameState getGameState() {
        return gameState;
    }

}
