package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

/**
 * Takes a directory or URL to an image and makes a bitmap for it.
 *
 * @author Alexander Chia
 * @version 1.0
 * @since 2018-11-2
 */
public class ImageParser {

    private Bitmap picture;

    /**
     * Constructor given a URL
     *
     * @param link, the url to the image.
     */
    public ImageParser(URL link) {
        try {
            this.picture = BitmapFactory.decodeStream(link.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor given a directory
     *
     * @param directory, the directory to the image.
     */
    public ImageParser(String directory) {
        try {
            this.picture = BitmapFactory.decodeFile(directory);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor given a bitmap
     *
     * @param bmp, an image to be parsed.
     */
    public ImageParser(Bitmap bmp) {
        this.picture = bmp;
    }

    /**
     * Getter for the private picture.
     *
     * @return the stored picture.
     */

    public Bitmap getPicture() {
        return this.picture;
    }

    // Both of the above return NULL if the image can't be decoded to a bitmap or doesn't exist

    /**
     * Splits the bitmap into a "n by n grid".
     *
     * @param n, the dimensions of the grid.
     * @return The n by n array of bitmaps which is the original split up.
     */
    public Bitmap[][] splitGrid(int n) {
        Bitmap[][] bitmaps = new Bitmap[n][n];
        int width = this.picture.getWidth() / n;
        int height = this.picture.getHeight() / n;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                try {
                    bitmaps[i][j] = Bitmap.createBitmap(this.picture, i * width, j * height, width, height);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmaps;
    }

    /**
     * Splits the bitmap into a "n by n grid" linearly into an ArrayList.
     *
     * @param n, the dimensions of the grid.
     * @return The ArrayList of ProxyBitmaps from the split up bitmap.
     */
    public ArrayList<BitmapDrawable> splitGridToBitmapDrawables(int n){
        Bitmap[][] bitmaps = splitGrid(n);
        ArrayList<BitmapDrawable> bitmapDrawables = new ArrayList<>();

        for(int i = 0; i < bitmaps.length; i++)
            for(int j = 0; j < bitmaps[i].length; j++)
                bitmapDrawables.add(new BitmapDrawable(bitmaps[j][i]));

        return bitmapDrawables;
    }
}
