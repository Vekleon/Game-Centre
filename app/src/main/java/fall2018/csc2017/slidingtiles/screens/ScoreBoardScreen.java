package fall2018.csc2017.slidingtiles.screens;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import fall2018.csc2017.slidingtiles.DataPacket;
import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.Scoreboard;
import fall2018.csc2017.slidingtiles.ScoreboardEntry;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * The display for the score board screen
 *
 * @author Jon
 * @since 2018-10-29
 */
public class ScoreBoardScreen extends AppCompatActivity {

    /**
     * The tab layout that stores difficulty tabs
     */
    private TabLayout tabLayout;

    /**
     * The tab layout for choosing between personal and global scores
     */
    private TabLayout tabScope;

    /**
     * The tab layout that stores game tabs
     */
    private TabLayout gameTabScope;

    /**
     * The current instance of Scoreboard in use for displaying entries
     */
    private Scoreboard scoreboard;

    /**
     * The current game selected
     */
    private String currentGame;

    /**
     * The current difficulty selected
     */
    private String currentDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        tabLayout = findViewById(R.id.tabLayout);
        tabScope = findViewById(R.id.tabScope);
        gameTabScope = findViewById(R.id.gameTabScope);
        setTabListeners();
        updateForNewScope("Personal");
    }

    /**
     * Adds functionality for selecting different tabs for each tab layout
     */
    private void setTabListeners() {
        gameTabScope.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab){
                /*populateEntries(scoreboard.getScores(tab.getText().toString(),
                        tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString()));*/
                updateForNewGame(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab){

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                populateEntries(scoreboard.getScores(
                        gameTabScope.getTabAt(gameTabScope.getSelectedTabPosition()).getText().toString(),
                        tab.getText().toString()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabScope.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateForNewScope(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * Get all scores for the current user
     * @return an arraylist of scoreboard entries, each of which have the current user as their user
     */
    private ArrayList<ScoreboardEntry> getPersonalScores() {
        GameCentre gameCentre = GameCentre.getGameCentre();
        DataPacket data = gameCentre.getUserData(gameCentre.getCurrUser());
        return data.getSaveScores();
    }

    /**
     * Update the scoreboard with the newly selected scope
     * @param scope Either "Personal" or "Global", depending on the selected tab
     */
    private void updateForNewScope(String scope){
        if(scope == "Personal")
            this.scoreboard = new Scoreboard(getPersonalScores());
        else
            this.scoreboard = new Scoreboard(Scoreboard.getGlobalScores());

        createGameTabs();
        updateForNewGame(currentGame);
    }

    /**
     * Update the scoreboard with the newly selected game, assuming the scope has been updated already
     * @param game The newly selected game we want to filter scores for
     */
    private void updateForNewGame(String game){
        currentGame = game;
        createDifficultyTabs();
        populateEntries(scoreboard.getScores(currentGame, currentDifficulty));
    }

    /**
     * Creates the tabs for each of the games that there are scores for within the current scope
     */
    private void createGameTabs(){
        createDifficultyTabs();

        gameTabScope.removeAllTabs();

        ArrayList<String> games = scoreboard.getGames();
        for(String currGame : games)
            gameTabScope.addTab(gameTabScope.newTab().setText(currGame));

        if(games.indexOf(currentGame) == -1)
            currentGame = games.size() > 0 ? games.get(0) : "";
    }

    /**
     * Creates the difficulty tabs that there are scores for within the current scope and game
     */
    private void createDifficultyTabs() {
        tabLayout.removeAllTabs();

        ArrayList<String> difficulties = scoreboard.getDifficulties(currentGame);
        for (String difficulty : difficulties)
            tabLayout.addTab(tabLayout.newTab().setText(difficulty));

        if (difficulties.indexOf(currentDifficulty) == -1)
            currentDifficulty = difficulties.size() > 0 ? difficulties.get(0) : "";
    }

    /**
     * Populates the score table with entries filtered to the current scope, game, and difficulty
     * @param entries A list of entries under the current scope
     */
    private void populateEntries(ArrayList<ScoreboardEntry> entries) {
        ArrayList<String> usernames = new ArrayList<>();
        ArrayList<String> scores = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            usernames.add(entries.get(i).getUsername());
            scores.add(String.valueOf(entries.get(i).getScore()));
        }

        ListView userList = findViewById(R.id.userList);
        ListView scoreList = findViewById(R.id.scoreList);
        ArrayAdapter<String> usernamesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernames);
        ArrayAdapter<String> scoresArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scores);
        userList.setAdapter(usernamesArrayAdapter);
        scoreList.setAdapter(scoresArrayAdapter);
    }
}
