package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


/**
 * A test class for Scoreboard
 **/
public class ScoreboardTest {

    /**
     * The scoreboard for testing
     **/
    private Scoreboard scoreboard;


    /**
     * Setup the scoreboard for testing
     **/
    private void setup() {
        ArrayList<ScoreboardEntry> entries = new ArrayList<>();
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "userOne", 500);
        ScoreboardEntry scoreTwo = new ScoreboardEntry("SlidingTiles", "3x3", "userTwo", 1500);
        ScoreboardEntry scoreThree = new ScoreboardEntry("2048", "4x4", "userThree", 800);
        ScoreboardEntry scoreFour = new ScoreboardEntry("2048", "4x4", "userThree", 2800);
        entries.add(scoreOne);
        entries.add(scoreTwo);
        entries.add(scoreThree);
        entries.add(scoreFour);
        scoreboard = new Scoreboard(entries);
    }

    /**
     * Setup global score data for testing
     **/
    private void setupGlobal() {
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "globalUserOne", 500);
        ScoreboardEntry scoreTwo = new ScoreboardEntry("SlidingTiles", "3x3", "globalUserTwo", 1500);
        DataPacket dataOne = new DataPacket();
        DataPacket dataTwo = new DataPacket();
        dataOne.addScore(scoreOne);
        dataOne.addScore(scoreTwo);
        HashMap<String, DataPacket> allData = new HashMap<>();
        allData.put("globalUserOne", dataOne);
        allData.put("globalUserTwo", dataTwo);
        GameCentre.setAllUserData(allData);
    }


    /**
     * Test to see if scoreboard constructor correctly orders scores
     */
    @Test
    public void testConstructorScoreOrder() {
        setup();
        assertEquals(2800, scoreboard.getScores("2048", "4x4").get(0).getScore());
        assertEquals(800, scoreboard.getScores("2048", "4x4").get(1).getScore());

    }

    /**
     * Test to see if scoreboard leaves remains unchanged if the game associated with an entry already exists.
     */
    @Test
    public void testEnsureGameInArrayNoModification() {
        setup();
        ScoreboardEntry newScore = new ScoreboardEntry("2048", "5x5", "userTest", 1100);
        assertNotEquals(-1, scoreboard.ensureGameInArray(newScore));
        assertEquals(2, scoreboard.getGames().size());
    }

    /**
     * Test to see if scoreboard doesn't yet have the game for an entry added to the list, then it gets added
     */
    @Test
    public void testEnsureGameInArrayModification() {
        setup();
        ScoreboardEntry newScore = new ScoreboardEntry("MemoryTiles", "4x5", "userTest", 300);
        assertEquals(-1, scoreboard.ensureGameInArray(newScore));
        assertEquals(3, scoreboard.getGames().size());
    }

    /**
     * Test to see if scoreboard leaves remains unchanged if the difficulty associated with an entry already exists.
     */
    @Test
    public void testEnsureDifficultyInArrayNoModification() {
        setup();
        ScoreboardEntry newScore = new ScoreboardEntry("SlidingTiles", "4x4", "userTest", 13100);
        assertNotEquals(-1, scoreboard.ensureDifficultyInArray(newScore));
        assertEquals(2, scoreboard.getDifficulties("SlidingTiles").size());
    }

    /**
     * Test to see if scoreboard doesn't yet have the difficulty for an entry added to the list, then it gets added it
     */
    @Test
    public void testEnsureDifficultyInArrayModification() {
        setup();
        ScoreboardEntry newScore = new ScoreboardEntry("SlidingTiles", "5x5", "userTest", 13100);
        assertEquals(-1, scoreboard.ensureDifficultyInArray(newScore));
        assertEquals(3, scoreboard.getDifficulties("SlidingTiles").size());
    }


    /**
     * Test to see the games are correctly returned
     */
    @Test
    public void testGetGames() {
        setup();
        assert (scoreboard.getGames().size() == 2);
        assert (scoreboard.getGames().get(0).equals("SlidingTiles"));
        assert (scoreboard.getGames().get(1).equals("2048"));

    }

    /**
     * Test to see the difficulties are correctly returned
     */
    @Test
    public void testGetDifficulties() {
        setup();
        assert (scoreboard.getDifficulties("SlidingTiles").size() == 2);
        assert (scoreboard.getDifficulties("SlidingTiles").get(0).equals("4x4"));
        assert (scoreboard.getDifficulties("SlidingTiles").get(1).equals("3x3"));
    }

    /**
     * Test to see if correct scores are returned based on filter of game and difficulty
     */
    @Test
    public void testGetScores() {
        setup();
        assert (scoreboard.getScores("SlidingTiles", "4x4").size() == 1);
        ScoreboardEntry score = scoreboard.getScores("SlidingTiles", "4x4").get(0);
        assert (score.getUsername().equals("userone"));
        assert (score.getScore() == 500);
    }

    /**
     * Test to see if global scores are correctly parsed and returned
     */
    @Test
    public void testGetGlobalScores() {
        setupGlobal();
        ArrayList<ScoreboardEntry> allScores = Scoreboard.getGlobalScores();
        assert ("globalUserOne".equals(allScores.get(0).getUsername()));
        assert ("globalUserTwo".equals(allScores.get(1).getUsername()));
        assertEquals(500, allScores.get(0).getScore());
        assertEquals(500, allScores.get(1).getScore());
    }


}