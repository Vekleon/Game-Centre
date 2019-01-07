package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile implements Comparable<Tile>, Serializable {

    static final long serialVersionUID = -16132278283584306L;

    /**
     * The background bitmap id for this tile.
     */
    protected int backgroundId;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackgroundId() {
        return backgroundId;
    }


    /**
     * Sets the background Id for the tile
     * @param backgroundId The id of the background, as indexed in the BitmapDrawable array in GameCentre
     */
    public void setBackgroundId(int backgroundId){
        this.backgroundId = backgroundId;
    }

    /**
     * A Tile with background id. The background id is the index of the BitmapDrawable array in GameCentre
     *
     * @param backgroundId the background id
     */
    public Tile(int backgroundId) {
        this.backgroundId = backgroundId;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.backgroundId - this.backgroundId;
    }
}
