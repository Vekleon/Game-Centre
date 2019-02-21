package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.gamestates.SlidingTilesGameState;

import static org.junit.Assert.assertEquals;


/**
 * A test class for datapacket
 **/
public class DataPacketTest {

    /**
     * The datapacket for testing
     **/
    private DataPacket dataPacket;


    /**
     * Setup an empty datapacket for testing
     **/
    private void setupBlankData() {
        dataPacket = new DataPacket();
    }

    /**
     * Setup an datapacket for testing
     **/
    private void setupData() {
        dataPacket = new DataPacket();
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "globalUserOne", 500);
        dataPacket = dataPacket.addScore(scoreOne);
        SlidingTilesGameState state = createState();
        dataPacket = dataPacket.updateSaveState("SlidingTiles", state);

    }


    /**
     * Create a gamestate for testing in datapacket
     **/
    private SlidingTilesGameState createState() {
        List<Tile> tileList = new ArrayList<>();
        tileList.add(new Tile(1));
        for (int i = 1; i < 16; i++) {
            tileList.add(new Tile(0));
        }
        SlidingBoard board = new SlidingBoard(tileList, 4);
        return new SlidingTilesGameState(board, 0);
    }

    /**
     * Test to see if the correct scores are returned
     */
    @Test
    public void testGetSaveScores() {
        setupData();
        assertEquals(500, dataPacket.getSaveScores().get(0).getScore());
    }

    /**
     * Test to see if the correct states are returned
     */
    @Test
    public void testGetSaveStates() {
        setupData();
        assertEquals(1, dataPacket.getSaveStates()
                .get("SlidingTiles").getBoard().getTile(0, 0).getBackgroundId());
    }

    /**
     * Test to see if the states are correctly added and updated
     */
    @Test
    public void testUpdateSaveState() {
        setupBlankData();
        dataPacket = dataPacket.updateSaveState("SlidingTiles", createState());
        assertEquals(1, dataPacket.getSaveStates()
                .get("SlidingTiles").getBoard().getTile(0, 0).getBackgroundId());
    }

    /**
     * Test to see if the scores are correctly added and updated
     */
    @Test
    public void testAddScore() {
        setupBlankData();
        ScoreboardEntry scoreOne = new ScoreboardEntry("SlidingTiles", "4x4", "globalUserOne", 500);
        DataPacket updatedData = dataPacket.addScore(scoreOne);
        assertEquals(500, updatedData.getSaveScores().get(0).getScore());
        assert ("globalUserOne".equals(updatedData.getSaveScores().get(0).getUsername()));
    }
}