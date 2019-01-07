package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The abstract class for board construction
 *
 * @author Group_0645
 * @since 2018-11-23
 */
public abstract class Board extends Observable implements Serializable, Iterable<Tile>{

    /**
     * The size of the board, either 3, 4, 5
     **/
    public int complexity;

    /**
     * The tiles on the board in row-major order.
     */
    public Tile[][] tiles;

    /**
     * Serialization hash
     */
    static final long serialVersionUID = -3773287799177583592L;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    public Board(List<Tile> tiles, int complexity) {
        Iterator<Tile> iter = tiles.iterator();
        this.complexity = complexity;
        this.tiles = new Tile[complexity][complexity];
        for (int row = 0; row != this.complexity; row++)
            for (int col = 0; col != this.complexity; col++)
                this.tiles[row][col] = iter.next();
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Getter for the complexity
     * @return the complexity
     */
    public int getComplexity() {
        return this.complexity;
    }

    /**
     * Getter for the number of tiles
     * @return the number of total tiles
     */
    public int getNumTiles(){ return this.complexity * this.complexity;}

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new BoardIterator();
    }

    /**
     * Iterator implementation for the board
     */
    private class BoardIterator implements Iterator<Tile> {
        private int row_index = 0;
        private int col_index = 0;

        /**
         * Returns true if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {

            return row_index < tiles.length && col_index < tiles.length;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws IndexOutOfBoundsException if the iteration has no more elements
         */
        @Override
        public Tile next() {
            Tile nextTile;
            try {
                nextTile = getTile(row_index, col_index);
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
            col_index += 1;
            if (col_index > complexity - 1) {
                row_index += 1;
                col_index = 0;
            }
            return nextTile;
        }
    }
}
