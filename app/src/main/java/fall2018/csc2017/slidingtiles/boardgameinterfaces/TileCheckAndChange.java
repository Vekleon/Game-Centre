package fall2018.csc2017.slidingtiles.boardgameinterfaces;

import fall2018.csc2017.slidingtiles.ProxyBitmap;

/**
 * An interface to check for the number of blank tiles on the board and to change the Tile
 * background
 */
public interface TileCheckAndChange {
    int numBlank();
    void changeTileBackground(int row, int col, int background);
}
