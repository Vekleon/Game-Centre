package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import static org.junit.Assert.assertEquals;


/**
 * A test class for Gamecentre
 **/
public class GameCentreTest {

    /**
     * The gamecentre for testing
     **/
    private GameCentre gameCentre;


    /**
     * Setup the gamecentre for testing
     **/
    private void setup() {
        gameCentre = new GameCentre();
    }

    /**
     * Setup a gamecentre with a current user for testing
     **/
    private void setupWithUser() {
        setup();
        gameCentre.setCurrUser("testUser");
    }

    /**
     * Setup a gamecentre with a current user and data
     **/
    private void setupWithUserData() {
        setup();
        gameCentre.setCurrUser("testUser");
        gameCentre.setCurrUserSavedGames("testUser");
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "testUser", 500);
        gameCentre.addSavedScoreToData("testUser", scoreOne);
    }

    /**
     * Test to see if the GameCentre is returned
     */
    @Test
    public void testGetGameCentre() {
        setupWithUser();
        assert ("testUser".equals(GameCentre.getGameCentre().getCurrUser()));

    }

    /**
     * Test to see if the current user is retuned
     **/
    @Test
    public void testGetCurrUser() {
        setupWithUser();
        assert ("testUser".equals(gameCentre.getCurrUser()));
    }


    @Test
    public void testGetUserData() {
        setupWithUserData();
        DataPacket data = gameCentre.getUserData("testUser");
        assertEquals(500, data.getSaveScores().get(0).getScore());

    }

    /**
     * Test to see if the user saved games are set correctly
     **/
    @Test
    public void testSetCurrUserSavedGames() {
        setupWithUser();
        gameCentre.setCurrUserSavedGames("testUser");
        assert (gameCentre.getUserData("testUser") != null);
    }

    /**
     * Test to see if the user saved scores are set correctly
     **/
    @Test
    public void testSetCurrUserSavedScores() {
        setupWithUserData();
        gameCentre.setCurrUserSavedScores("testUser");
        assertEquals(500, gameCentre.getCurrUserSavedScores().get(0).getScore());
    }

    /**
     * Test to see if the user is added correctly
     **/
    @Test
    public void testAddUserToData() {
        setup();
        gameCentre.addUserToData("testUser");
        DataPacket data = gameCentre.getUserData("testUser");
        assertEquals(500, data.getSaveScores().get(0).getScore());
    }

    /**
     * Test to see if the saved game is added correctly
     **/
    @Test
    public void testAddSavedGameToData() {
        setupWithUser();
        List<Tile> tileList = new ArrayList<>();
        tileList.add(new Tile(1));
        for (int i = 1; i < 16; i++) {
            tileList.add(new Tile(0));
        }
        SlidingBoard slidingBoard = new SlidingBoard(tileList, 4);
        gameCentre.addSavedGameToData("testUser", "SlidingTiles", new SlidingTilesGameState(slidingBoard, 0));
        SlidingTilesGameState state = (SlidingTilesGameState)gameCentre.getUserData("testUser").getSaveStates().get("SlidingTiles");
        assertEquals(1, state.getBoard().getTile(0, 0).getBackgroundId());
    }

    /**
     * Test to see if userData is set correctly
     **/
    @Test
    public void testSetUserData() {
        setup();
        HashMap<String, DataPacket > data = new HashMap<>();
        DataPacket dataPacket = new DataPacket();
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "userTest", 5000);
        dataPacket.addScore(scoreOne);
        data.put("userTest", dataPacket );
        GameCentre.setAllUserData(data);
        gameCentre.setCurrUser("userTest");
        DataPacket changedData = gameCentre.getUserData("userTest");
        assertEquals(5000, changedData.getSaveScores().get(0).getScore());
    }
}