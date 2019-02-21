package fall2018.csc2017.slidingtiles.memorytiles;

import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.Tile;

/**
 * The tiles that will be used for the memory tile game
 */
public class MemoryTile extends Tile {

    /**
     * Determines whether the tile is flipped over or not
     */
    private boolean flipped = false;

    /**
     * Initializes a new memory tile
     * @param id the id number corresponding to this tile
     */
    public MemoryTile(int id){
        super(id);
    }

    /**
     * Flips the tile over
     */
    public void flip(){
        flipped = !flipped;
    }

    /**
     * Returns the flip state of the tile
     * @return whether or not the tile is flipped
     */
    public boolean isFlipped(){
        return this.flipped;
    }

}
