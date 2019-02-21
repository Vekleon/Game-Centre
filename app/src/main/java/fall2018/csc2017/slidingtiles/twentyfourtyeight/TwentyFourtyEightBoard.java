package fall2018.csc2017.slidingtiles.twentyfourtyeight;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.ProxyBitmap;
import fall2018.csc2017.slidingtiles.Tile;
import fall2018.csc2017.slidingtiles.boardgameinterfaces.TileCheckAndChange;
import fall2018.csc2017.slidingtiles.boardgameinterfaces.TileMovement;

/**
 * The board representation for the game 2048
 * @author Thomas Leung
 * @since 2018-11-28
 */
public class TwentyFourtyEightBoard extends Board implements TileMovement, TileCheckAndChange {

    /**
     * Initializer for the 2048 game board
     * @param tiles the tiles that will be stored on the board
     * @param complexity the dimensions of the board
     */
    public TwentyFourtyEightBoard(List<Tile> tiles, int complexity){
        super(tiles, complexity);
    }

    /**
     * Returns the number of blank tiles present on the board
     * @return the number of blank tiles present on the board
     */
    public int numBlank(){
        int numBlank = 0;

        for(int i= 0; i < this.tiles.length; i++){
            for(int j = 0; j < this.tiles[i].length; j++){
                if(this.tiles[i][j].getBackgroundId() == 0){
                    numBlank++;}
            }
        }
        return numBlank;
    }

    /**
     * Changes the desired tiles background to reflect the change in the board
     * @param row the row of the desired tile is located
     * @param col the column the desired tile is located
     * @param background the background value that the tile is to be changed to
     */
    public void changeTileBackground(int row, int col, int background){
        tiles[row][col].setBackgroundId(background);
    }

    public void swapTiles(int row1, int col1, int row2, int col2){
        return;
    }

    /** 
     * Rotate the board board 90 degrees counterclockwise, swapping tiles appropriately*
     * CITE: https://www.geeksforgeeks.org/inplace-rotate-square-matrix-by-90-degrees
     **/
    void rotateLeft() {
        // Consider all squares one by one
        TwentyFourtyEightTile[][] replacementBoard = new TwentyFourtyEightTile[4][4];
        for(int x = 0; x < this.complexity; x++)
            for(int y = 0; y < this.complexity; y++)
                replacementBoard[3-y][x] = (TwentyFourtyEightTile) tiles[x][y];

        tiles = replacementBoard;
        setChanged();
        notifyObservers();
    }

    /**
     * Left shift a line so that all tiles preserve order, and any blank tiles appear on the right
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    public TwentyFourtyEightTile[] moveLine(TwentyFourtyEightTile[] oldLine) {
        LinkedList<TwentyFourtyEightTile> tileShifts = new LinkedList<>();
        for (int i = 0; i < this.getComplexity(); i++) {
            if (!(oldLine[i].getBackgroundId() == 0))
                tileShifts.addLast(oldLine[i]);
        }
        if (tileShifts.size() == 0) {
            return oldLine;
        } else {
            TwentyFourtyEightTile[] newLine = new TwentyFourtyEightTile[this.getComplexity()];
            ensureSize(tileShifts, this.getComplexity());
            for (int i = 0; i < this.getComplexity(); i++) {
                newLine[i] = tileShifts.removeFirst();
            }
            return newLine;
        }
    }
    /**
     * Change the current row of the board to a new row
     **/
    public void changeRow(int row, TwentyFourtyEightTile[] newRow) {
        System.arraycopy(newRow, 0, tiles[row], 0, this.complexity);
    }

    /**
     * Extract a row from the slidingBoard
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    public TwentyFourtyEightTile[] getLine(int index) {
        TwentyFourtyEightTile[] result = new TwentyFourtyEightTile[this.complexity];
        for (int i = 0; i < this.complexity; i++) {
            result[i] = ((TwentyFourtyEightTile) this.getTile(index, i)).clone();
        }
        return result;
    }

    /**
     * Append any blank tiles onto the tileList untill the length of tileList matches size
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    public void ensureSize(List<TwentyFourtyEightTile> tileList, int size) {
        while (tileList.size() != size) {
            tileList.add(new TwentyFourtyEightTile(0));
        }
    }

    /**
     * Compares to see if each line contains the same tile values
     **/
    public boolean compare(TwentyFourtyEightTile[] line1, TwentyFourtyEightTile[] line2) {
        if (line1.length != line2.length) {
            return false;
        }

        for (int i = 0; i < line1.length; i++) {
            if (line1[i].getBackgroundId() != line2[i].getBackgroundId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a new tile with the desired id and background
     * @param id the desired tile id
     * @return a new tile with the desired id and background
     */
    TwentyFourtyEightTile getNewTile(int id){
        return new TwentyFourtyEightTile(id);
    }

    /**
     * A string representation of the board that uses the boards ids.
     * @return the string representation of the board based off the tile ids.
     */
    public String toString(){
        StringBuilder b = new StringBuilder(20);
        b.append("[\n");
        for(int i = 0; i < complexity; i++){
            for(int j = 0; j < complexity; j++)
                b.append(tiles[i][j].getBackgroundId());
            b.append("\n");
        }
        b.append("]");
        return b.toString();
    }
}
