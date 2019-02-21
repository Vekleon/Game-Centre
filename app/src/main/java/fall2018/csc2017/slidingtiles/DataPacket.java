package fall2018.csc2017.slidingtiles;

import java.io.Serializable;
import java.util.*;

import fall2018.csc2017.slidingtiles.gamestates.GameState;

/**
 * The serializable object that will be used to contain users save states and scores to be written
 * into the file
 */
public class DataPacket implements Serializable {

    /**
     * Maps a save state for each game
     **/
    private HashMap<String, GameState> saveStates = new HashMap<>();

    /**
     * A list of scores
     **/
    private ArrayList<ScoreboardEntry> saveScores = new ArrayList<>();

    /**
     * hash value
     */
    static final long serialVersionUID = -2260428854916219304L;

    /**
     * Getter for the list of scoreboard entries
     * @return the scoreboard entries
     */
    public ArrayList<ScoreboardEntry> getSaveScores() {
        return this.saveScores;
    }

    /**
     * Getter for the savestates
     * @return the current savestate
     */
    HashMap<String, GameState> getSaveStates() {
        return this.saveStates;
    }

    /**
     * Update the saveState associated with game, or create a new one if it doesn't exist
     **/
    /**
     * Update the saveState associated with game, or create a new one if it doesn't exist
     * @param game the current game
     * @param gameState The state of the current game
     * @return the serialized datapacket
     */
    DataPacket updateSaveState(String game, GameState gameState) {
        this.saveStates.put(game, gameState);
        return this;
    }

    /**
     * Updates the scoreboard by adding a new score
     * @param scoreboardEntry The scoreboard entry of the score to be added
     * @return The state of DataPacket now including the added scoreboard entry
     */
    DataPacket addScore(ScoreboardEntry scoreboardEntry) {
        this.getSaveScores().add(scoreboardEntry);
        return this;
    }
}

