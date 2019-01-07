package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ScoreboardEntry implements Comparable<ScoreboardEntry>, Serializable {

    /**
     * The name of the game this score is for.
     */
    private String game;

    /**
     * The name of the difficulty this score is for.
     */
    private String difficulty;

    /**
     * The user that achieved this score.
     */
    private String username;

    /**
     * The actual score achieved by the user.
     */
    private int score;

    /**
     * Builds a scoreboard entry with the given info
     *
     * @param game       The name of the game this score is for.
     * @param difficulty The name of the difficulty this score is for.
     * @param username   The user that achieved this score.
     * @param score      The actual score achieved by the user.
     */
    public ScoreboardEntry(String game, String difficulty, String username, int score) {
        this.game = game;
        this.difficulty = difficulty;
        this.username = username;
        this.score = score;
    }

    @Override
    public int compareTo(@NonNull ScoreboardEntry entry) {
        return Integer.compare(this.score, entry.score);
    }

    /**
     * Gets the game for this entry.
     *
     * @return The name of the game this score is for.
     */
    public String getGame() {
        return game;
    }

    /**
     * Gets the difficulty for this entry.
     *
     * @return The name of the difficulty this score is for.
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * Get the username for this entry.
     *
     * @return The user that achieved this score.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the score for this entry.
     *
     * @return The actual score achieved by the user.
     */
    public int getScore() {
        return score;
    }

    /**
     * Generate and returnn the string representation of the scoreboard entry.
     *
     * @return string representation of this scoreboard entry.
     */
    public String toString() {
        return "Username: " + this.getUsername() + " | Difficulty: " + this.getDifficulty() + " |           Score: " + this.getScore();
    }
}


