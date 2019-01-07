package fall2018.csc2017.slidingtiles;

import java.util.ArrayList;
import java.util.Collections;

public class Scoreboard {

    /**
     * Names of all games that have at least one score.
     */
    private ArrayList<String> games = new ArrayList<>();

    /**
     * Names of all difficulties for a game, at the same index as found in the list "games".
     */
    private ArrayList<ArrayList<String>> gameDifficulties = new ArrayList<>();

    /**
     * List of (name, score) entries, at the same index as found in the list "gameDifficulties"
     */
    private ArrayList<ArrayList<ArrayList<ScoreboardEntry>>> scores = new ArrayList<>();

    /**
     * If this scoreboard doesn't yet have the game for this entry added to the list, then add it.
     *
     * @param entry A valid entry.
     * @return The new index of the game in the list.
     */
    int ensureGameInArray(ScoreboardEntry entry) {
        int gameIndex = games.indexOf(entry.getGame());
        if (gameIndex == -1) {
            gameIndex = games.size();
            games.add(entry.getGame());
            gameDifficulties.add(new ArrayList<String>());
            scores.add(new ArrayList<ArrayList<ScoreboardEntry>>());
        }
        return gameIndex;
    }

    /**
     * If this scoreboard doesn't yet have the game or difficulty for this entry added to the list,
     * then add it. This assumes that the game is already in the list. If not, then use ensureGameInArray
     *
     * @param entry A valid entry.
     * @return The new index of the difficulty in the list.
     */
    int ensureDifficultyInArray(ScoreboardEntry entry) {
        int gameIndex = games.indexOf(entry.getGame());
        int difficultyIndex = gameDifficulties.get(gameIndex).indexOf(entry.getDifficulty());
        if (difficultyIndex == -1) {
            difficultyIndex = gameDifficulties.get(gameIndex).size();
            gameDifficulties.get(gameIndex).add(entry.getDifficulty());
            scores.get(gameIndex).add(new ArrayList<ScoreboardEntry>());
        }
        return difficultyIndex;
    }


    /**
     * Builds the scoreboard with the given entries sorted in non-increasing order.
     *
     * @param entries A list of pairs of a (game, difficulty) pair and a (username, score) pair,
     *                which will be mutated.
     */
    public Scoreboard(ArrayList<ScoreboardEntry> entries) {
        Collections.sort(entries, Collections.<ScoreboardEntry>reverseOrder());
        Collections.sort(games);
        for (int i = 0; i < games.size(); i++)
            Collections.sort(gameDifficulties.get(i));

        for (ScoreboardEntry entry : entries) {
            int gameIndex = ensureGameInArray(entry);
            int difficultyIndex = ensureDifficultyInArray(entry);
            scores.get(gameIndex).get(difficultyIndex).add(entry);
        }
    }


    /**
     * Gets names of games that have at least one score.
     *
     * @return A list of games that have scores.
     */
    public ArrayList<String> getGames() {
        return games;
    }

    /**
     * Gets names of difficulties that have at least one score.
     *
     * @param game The game to retrieve difficulties for.
     * @return The list of difficulties that have scores for this game.
     */
    public ArrayList<String> getDifficulties(String game) {
        int gameIndex = games.indexOf(game);
        if (gameIndex != -1)
            return gameDifficulties.get(gameIndex);
        else
            return new ArrayList<>();
    }

    /**
     * @param game       The game to retrieve scores for.
     * @param difficulty The specific difficulty for this game to retrieve scores for.
     * @return A list of score entries for this difficulty for this game.
     */
    public ArrayList<ScoreboardEntry> getScores(String game, String difficulty) {
        int gameIndex = games.indexOf(game);
        if (gameIndex != -1) {
            int difficultyIndex = gameDifficulties.get(gameIndex).indexOf(difficulty);
            if (difficultyIndex != -1)
                return scores.get(gameIndex).get(difficultyIndex);
        }
        return new ArrayList<>();
    }

    /**
     * Parses through every user in each game (and game difficulty)  and collects their top scores,
     * stores them and creates a global scoreboard with all of the scores per game.
     *
     * @return A compiled list of top scores in every game/difficulty.
     */
    public static ArrayList<ScoreboardEntry> getGlobalScores() {
        ArrayList<ScoreboardEntry> allScores = new ArrayList<>();
        for (DataPacket userData : GameCentre.allUserData.values()) {
            Scoreboard scoreboard = new Scoreboard(userData.getSaveScores());
            for (String game : scoreboard.getGames()) {
                for (String difficulty : scoreboard.getDifficulties(game)) {
                    ArrayList<ScoreboardEntry> userScores = scoreboard.getScores(game, difficulty);
                    if (userScores.size() != 0)
                        allScores.add(Collections.max(userScores));
                }
            }
        }
        return allScores;
    }
}