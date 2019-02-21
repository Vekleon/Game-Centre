package fall2018.csc2017.slidingtiles.memorytiles;

import java.util.List;

import fall2018.csc2017.slidingtiles.Board;
import fall2018.csc2017.slidingtiles.Tile;
/**
 * The class responsible for keeping track of the board
 *
 * @author Group_0645
 * @since 2018-11-23
 */
public class MemoryTileBoard extends Board {
    /**
     * Counter for flipped tiles
     */
    private int numFlipped = 0;

    /**
     * Initiates the board with given tiles and complexity
     * @param tiles list of every tile on the board
     * @param complexity board complexity
     */
    public MemoryTileBoard(List<Tile> tiles, int complexity){
        super(tiles, complexity);
    }

    /**
     * Keep track of how many tiles are flipped
     */
    public void updateNumFlipped(){
        int countFlipped = 0;
        for(int r = 0; r < complexity; r++)
            for(int c = 0; c < complexity; c++)
                if(((MemoryTile)tiles[r][c]).isFlipped())
                    countFlipped++;
        this.numFlipped = countFlipped;
    }

    /**
     * Getter for the number of flipped tiles
     * @return the number of flipped tiles
     */
    public int getNumFlipped(){return this.numFlipped;}
}
