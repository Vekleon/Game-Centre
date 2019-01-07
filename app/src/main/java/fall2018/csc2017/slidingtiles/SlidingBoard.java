package fall2018.csc2017.slidingtiles;

import java.util.Arrays;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class SlidingBoard extends Board{

    final int blankId;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    SlidingBoard(List<Tile> tiles, int complexity) {
        super(tiles, complexity);
        blankId = complexity * complexity - 1;
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {

        Tile tempTileHolder = getTile(row1, col1);
        tiles[row1][col1] = getTile(row2, col2);
        tiles[row2][col2] = tempTileHolder;

        setChanged();
        notifyObservers();
    }

    /**
     * Return true iff the current board is solavable
     * @Todo move into sliding tiles gamestate
     **/
    boolean isSolvable() {
        if (this.complexity % 2 == 1) {
            return this.numInversions() % 2 == 0;
        }
        try {
            return (this.blankIsEvenRow() && (this.numInversions() % 2) == 1) || (!this.blankIsEvenRow() && (this.numInversions() % 2 == 0));
        } catch (NoBlankTileException e) {
            return false;

        }
    }

    /**
     * Turn tiles into a one dimensional array
     **/
    Tile[] toOneDimensionalArray() {
        Tile[] oneDimensionalTileArray = new Tile[complexity * complexity];
        int index = 0;
        for (Tile[] tileArr : tiles) {
            for (Tile tile : tileArr) {
                oneDimensionalTileArray[index] = tile;
                index++;
            }
        }

        return oneDimensionalTileArray;
    }

    /**
     * Return the number of swaps needed to sort the tiles in row major order
     **/
    int numInversions() {
        int count = 0;
        Tile[] tileArr = this.toOneDimensionalArray();
        for (int i = 0; i < tileArr.length; i++) {
            for (int j = i + 1; j < tileArr.length; j++) {
                if (tileArr[i].compareTo(tileArr[j]) < 0 && tileArr[i].getBackgroundId() != blankId) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Return if the blank tile is on an even row from the bottom (second last, fourth last etc)
     *
     **/
     boolean blankIsEvenRow() throws NoBlankTileException {
        for (int i = 0; i < tiles.length; i++)
            for (int j = 0; j < tiles[i].length; j++)
                if (tiles[i][j].getBackgroundId() == blankId)
                    return (this.complexity - i) % 2 == 0;
        throw new NoBlankTileException("No blank tile on the board");

    }

    @Override
    public String toString() {
        return "SlidingBoard{" + "tiles=" + Arrays.toString(tiles) + '}';
    }
}
