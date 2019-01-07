package fall2018.csc2017.slidingtiles.screens;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.slidingtiles.R;

/**
 * A general screen class that has methods to handle adding button listeners
 *
 * @author Jon
 */
public abstract class Screen extends AppCompatActivity {

    protected MediaPlayer song = new MediaPlayer();
    protected int songPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        song = MediaPlayer.create(this, getSongId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        song.start();
        song.setLooping(true);
    }

    @Override
    protected void onResume(){
        super.onResume();
        song.start();
        song.seekTo(songPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(song.isPlaying()){
            song.pause();
            songPosition = song.getCurrentPosition();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        song.stop();
        song.release();
    }

    /**
     * Creates the button listeners for each button on the screen. Behaviour is undefined for
     * when a button is given mappings to both an activity and a method.
     *
     * @param buttonIdToActivityMap Maps button resource IDs to an activity to run when clicked
     * @param buttonIdToRunnableMap Maps button resource IDs to a method to run when clicked
     */
    protected void createButtonListeners(HashMap<Integer, Class> buttonIdToActivityMap, HashMap<Integer, Runnable> buttonIdToRunnableMap) {
        final Context context = this;


        for (HashMap.Entry<Integer, Class> entry : buttonIdToActivityMap.entrySet()) {
            final Class gameClass = entry.getValue();
            Button button = findViewById(entry.getKey());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, gameClass));
                }
            });
        }

        for (HashMap.Entry<Integer, Runnable> entry : buttonIdToRunnableMap.entrySet()) {
            final Runnable runnable = entry.getValue();
            Button button = findViewById(entry.getKey());

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    runnable.run();
                }
            });
        }
    }

    /**
     * Help function to create an array list of tile images
     * @param tileImages the images for the tiles
     * @return an array list containing the bitmaps of the tiles
     */
    protected ArrayList<BitmapDrawable> createTileList(TypedArray tileImages, int size){
        ArrayList<BitmapDrawable> tiles = new ArrayList<>();
        for(int i = 0; i < size; i++){
            int background = tileImages.getResourceId(i, 0);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), background);
            tiles.add(new BitmapDrawable(bitmap));
        }
        return tiles;
    }

    /**
     * Creates a message on the screen with the given text
     *
     * @param text The message that will be displayed
     */
    protected void makeToastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Starts a new activity
     *
     * @param context     The context we're coming from
     * @param destination The destination activity class
     */
    protected void goToScreen(Context context, Class destination) {
        context.startActivity(new Intent(context, destination));
    }

    /** Gets the song id for a given file, overload in derived classes to change the song.
     * @return the song id
     */
    protected int getSongId() {
        return R.raw.jeep;
    }

}
