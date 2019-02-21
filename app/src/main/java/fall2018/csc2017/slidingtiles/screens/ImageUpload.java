package fall2018.csc2017.slidingtiles.screens;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;

import android.graphics.Bitmap;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.bumptech.glide.*;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.HashMap;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.slidingtiles.StartingActivity;


/**
 * The activity class responsible for the screen where custom images can be uploaded.
 *
 * @author Alexander Chia
 * @version 1.0
 * @since 2018-11-04
 */
public class ImageUpload extends Screen {
    private int reqCode = 1;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        imageView = findViewById(R.id.imageView);

        HashMap<Integer, Class> activityMap = new HashMap<>();
        activityMap.put(R.id.backButton, StartingActivity.class);

        HashMap<Integer, Runnable> runnableMap = new HashMap<>();
        runnableMap.put(R.id.localButton, this::getLocalImage);
        runnableMap.put(R.id.urlButton, this::getImageFromUrl);

        super.createButtonListeners(activityMap, runnableMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                // Oreo workaround for URIs
                Uri uri_data = data.getData();
                loadPhoto(uri_data.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup for the button which opens your file explorer to select an image.
     */
    private void getLocalImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, reqCode);
    }

    /**
     * Takes the chosen file and processes information from it.
     */
    private void getImageFromUrl() {
        EditText urlBox = findViewById(R.id.urlBox);
        String urlAsString = urlBox.getText().toString();
        loadPhoto(urlAsString);
    }

    /**
     * Takes in a URI and loads a small icon showing it, while loading in the image for the game.
     *
     * @param uri, the unique identifier to load an image.
     */
    private void loadPhoto(String uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        GameCentre.getGameCentre().setCustomImageBitmap(resource);
                    }
                });
    }
}
