package fall2018.csc2017.slidingtiles.filewriters;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import fall2018.csc2017.slidingtiles.DataPacket;
import fall2018.csc2017.slidingtiles.GameCentre;

/**
 * A singleton static class that will be used to write information into files anywhere in the
 * program.
 *
 * @author Thomas Leung
 * @since 2018-11-04
 */
public class FileWriter {
    private static final String GLOBAL_USER_INFORMATION = "ALL_USERS.ser";
    /**
     * Constructor of the FileWriter class
     */
    private FileWriter() {

    }

    /**
     * Retries the global info from the file containing the information
     * @param ctx the screen which calls this method
     * @return the global information stored in the HashMap that was in the file
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, DataPacket> getGlobalUserInfo(Context ctx) {
        HashMap<String, DataPacket> data = new HashMap<>();
        try {
            InputStream inputStream = ctx.openFileInput(GLOBAL_USER_INFORMATION);
            ObjectInputStream input = new ObjectInputStream(inputStream);
            data = (HashMap<String, DataPacket>) input.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("File read", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("File read", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("File read", "File contained unexpected data type: " + e.toString());
        }
        return data;
    }

    /**
     * Rewrites the new information into the global data file
     * @param ctx the screen which calls this method
     */
    public static void writeIntoGlobalInfo(Context ctx) {
        try {
            FileOutputStream output;
            output = ctx.openFileOutput(GLOBAL_USER_INFORMATION, Context.MODE_PRIVATE);

            ObjectOutputStream outputStream = new ObjectOutputStream(output);
            outputStream.writeObject(GameCentre.allUserData);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
