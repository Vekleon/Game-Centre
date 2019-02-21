package fall2018.csc2017.slidingtiles.boardgameinterfaces;

/**
 * An interface used to swap Tile positions on the game board.
 */
public interface TileMovement {
    void swapTiles(int row1, int col1, int row2, int col2);
}
