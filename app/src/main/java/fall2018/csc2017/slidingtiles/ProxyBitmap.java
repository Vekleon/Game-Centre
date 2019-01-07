package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;

import java.io.Serializable;


/**
 * Serializable class to store a bitmap for each tile.
 * Taken from http://xperience57.blogspot.com/2015/09/android-saving-bitmap-as-serializable.html
 * Thank you very much random guy on the internet for this code
 */
public class ProxyBitmap implements Serializable{
    private final int [] pixels;
    private final int width , height;

    public ProxyBitmap(Bitmap bitmap){
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        pixels = new int [width*height];
        bitmap.getPixels(pixels,0,width,0,0,width,height);
    }

    public Bitmap getBitmap(){
        return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
    }
}