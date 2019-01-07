package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.gamestates.GameState;


public class GameCentre {
    /**
     * initializes a new gamecentre
     */
    private static GameCentre gc = new GameCentre();

    /**
     * The current username that is logged in
     **/
    private String currUser;

    /**
     * The current users saved games
     **/
    private HashMap<String, GameState> currUserSavedGames;

    /**
     * The current users saved scores
     **/
    private ArrayList<ScoreboardEntry> currUserSavedScores;

    /**
     * Arraylist mapping tile background ID to its corresponding bitmap
     */
    private ArrayList<BitmapDrawable> idToBitmap;

    /**
     * Maps every user to their associated datapacket
     **/
    private static HashMap<String, DataPacket> allUserData = new HashMap<>();

    /**
     * Stored bitmap
     */
    private Bitmap bitmap;

    /**
     * Default init
     */
    private GameCentre() {

    }

    /**
     * Getter for the current gamecentre
     *
     * @return current game centre
     */
    public static GameCentre getGameCentre() {
        return gc;
    }

    /**
     * Return this current user
     **/
    public String getCurrUser() {
        return this.currUser;
    }

    /**
     * Set the current user
     **/
    public void setCurrUser(String user) {
        this.currUser = user;
    }

    /**
     * Return the current users saved gamestate associated with game, will return null if there is no game
     **/
    GameState getCurrUserSavedGame(String game) {
        return this.currUserSavedGames.get(game);
    }

    /**
     * Get the users datapacket from all user data
     **/
    public DataPacket getUserData(String username) {
        return allUserData.get(username);
    }

    /**
     * Set the current user saved games based on user data
     **/
    public void setCurrUserSavedGames(String username) {
        DataPacket dataPacket = this.getUserData(username);
        if (dataPacket == null)
            addUserToData(username);
        else
            this.currUserSavedGames = dataPacket.getSaveStates();
    }

    /**
     * Set the current user saved scores based on user data
     **/
    public void setCurrUserSavedScores(String username) {
        DataPacket dataPacket = this.getUserData(username);
        this.currUserSavedScores = dataPacket.getSaveScores();
    }

    /**
     * Add a new user to user data with no saved games or scores
     **/
    public void addUserToData(String username) {
        allUserData.put(username, new DataPacket());

    }

    /**
     * Add a saved game to a users userdata
     **/
    public void addSavedGameToData(String username, String game, GameState gameState) {
        DataPacket oldData = allUserData.get(username);
        allUserData.replace(username, oldData, oldData.updateSaveState(game, gameState));

    }

    /**
     * setter for user data
     *
     * @param allUserData all passed in user data
     */
    public static void setAllUserData(HashMap<String, DataPacket> allUserData) {
        GameCentre.allUserData = allUserData;
    }

    /**
     * @return all user data
     */
    public static HashMap<String, DataPacket> getAllUserData(){
        return allUserData;
    }

    /**
     * getter for bitmap
     *
     * @return curr bitmap
     */
    Bitmap getCustomImageBitmap() {
        return bitmap;
    }

    /**
     * Add a saved score to a users userdata
     **/
    void addSavedScoreToData(String username, ScoreboardEntry scoreboardEntry) {
        DataPacket oldData = allUserData.get(username);
        allUserData.replace(username, oldData, oldData.addScore(scoreboardEntry));

    }

    /**
     * setter for bitmap
     *
     * @param bitmap curr bitmap
     */
    void setCustomImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * @return the arrays list of bitmaps to be used to texture the tiles
     */
    public ArrayList<BitmapDrawable> getIdToBitmapArray() {
        return idToBitmap;
    }

    /**
     * @param idToBitmap sets the array list of bitmaps
     */
    public void setIdToBitmapArray(ArrayList<BitmapDrawable> idToBitmap) {
        this.idToBitmap = idToBitmap;
    }

    /**
     * @param id the id of the desired tile
     * @return returns the appropriate bitmap texture for the given id
     */
    public BitmapDrawable getBitmapFromId(int id) {
        return idToBitmap.get(id);
    }
}
