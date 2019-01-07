package fall2018.csc2017.slidingtiles.memorytiles.screens;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.filewriters.FileWriter;
import fall2018.csc2017.slidingtiles.memorytiles.MemoryTilesManager;
import fall2018.csc2017.slidingtiles.screens.Screen;

/**
 * The class responsible for setting up button functionality and screen transitioning for
 * the memory tiles title screen
 * @author Thomas
 * @since 2018-11-24
 */
public class MemoryTilesTitleScreen extends Screen {

    /**
     * The board manager
     */
    private MemoryTilesManager boardManager;

    /**
     * The complexity being set for the new game by the seek bar
     */
    private int complexity = 4;

    /**
     * The preliminary set up of the screen. Adds functionality to buttons and sets
     * variables
     * @param savedInstanceState the saved instance of the previous state
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memory_tiles_start);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.memory_tiles_start, this::startGame);
        super.createButtonListeners(new HashMap<>(), runnableMap);

        createSeekBarListener();
    }

    /**
     * Starts the memory tiles game
     */
    private void startGame(){
        TypedArray tileImages = getResources().obtainTypedArray(R.array.memory_tile_tiles);
        GameCentre.getGameCentre().setIdToBitmapArray(createTileList(tileImages, 10));
        boardManager = new MemoryTilesManager(complexity);

        saveToFile();

        Intent intent = new Intent(this, MemoryTilesGameScreen.class);
        Bundle info = new Bundle();
        info.putInt("complexity", complexity);
        intent.putExtras(info);
        startActivity(intent);
    }

    /**
     * Adds functionality for the seek bars to modify the current width and height settings
     */
    private void createSeekBarListener() {
        TextView difficultyText = findViewById(R.id.textViewDifficulty);
        difficultyText.setText("Difficulty: " + complexity + "x" + complexity);
        SeekBar seekBarWidth = findViewById(R.id.seekBarDifficulty);

        seekBarWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                complexity = progress + 3;
                difficultyText.setText("Difficulty: " + complexity + "x" + complexity);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Save the board manager to fileName.
     */
    public void saveToFile() {
        GameCentre gameCentre = GameCentre.getGameCentre();
        gameCentre.addSavedGameToData(gameCentre.getCurrUser(), "MemoryTiles", boardManager.getGameState());
        FileWriter.writeIntoGlobalInfo(this);
    }
}
