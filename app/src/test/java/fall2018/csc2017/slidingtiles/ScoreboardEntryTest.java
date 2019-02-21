package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * A test class for ScoreboardEntry
 **/
public class ScoreboardEntryTest {

    /**
     * The scoreboard for testing
     **/
    private ScoreboardEntry scoreboardEntry;


    /**
     * Setup the scoreboardEntry for testing
     **/
    private void setup() {
        scoreboardEntry = new ScoreboardEntry("SlidingTiles", "4x4", "testUser", 500);

    }


    /**
     * Test to see if scores are correctly identified as larger than other score
     */
    @Test
    public void testCompareScoreGreater() {
        ScoreboardEntry scoreboardEntryTwo = new ScoreboardEntry("SlidingTiles", "4x4", "testUser", 100);
        assertEquals(1, scoreboardEntry.compareTo(scoreboardEntryTwo));
    }

    /**
     * Test to see if scores are correctly identified as smaller than other score
     */
    @Test
    public void testCompareScoreLesser() {
        ScoreboardEntry scoreboardEntryTwo = new ScoreboardEntry("SlidingTiles", "4x4", "testUser", 1500);
        assertEquals(-1, scoreboardEntry.compareTo(scoreboardEntryTwo));
    }

    /**
     * Test to see if scores are correctly identified as equal to other score
     */
    @Test
    public void testCompareScoreEqual() {
        ScoreboardEntry scoreboardEntryTwo = new ScoreboardEntry("SlidingTiles", "4x4", "testUser", 1500);
        assertEquals(0, scoreboardEntry.compareTo(scoreboardEntryTwo));
    }

    /**
     * Test to see if the correct game is returned
     **/
    @Test
    public void testGetGame() {
        setup();
        assert ("SlidingTiles".equals(scoreboardEntry.getGame()));
    }

    /**
     * Test to see if the correct difficulty is returned
     **/
    @Test
    public void testGetDifficulty() {
        setup();
        assert ("4x4".equals(scoreboardEntry.getDifficulty()));
    }

    /**
     * Test to see if the correct username is returned
     **/
    @Test
    public void testGetUsername() {
        setup();
        assert ("testUser".equals(scoreboardEntry.getUsername()));
    }

    /**
     * Test to see if the correct game is returned
     **/
    @Test
    public void testGetScore() {
        setup();
        assertEquals(500, scoreboardEntry.getScore());
    }

    /**
     * Test to see if the tostring method returns the correct string representation of a score
     **/
    @Test
    public void testToString() {
        String expectedString = "Username: testUser | Difficulty: 4x4 |           Score: 500";
        assert (expectedString.equals(scoreboardEntry.toString()));
    }


}