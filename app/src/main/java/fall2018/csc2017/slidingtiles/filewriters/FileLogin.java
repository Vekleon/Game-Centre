package fall2018.csc2017.slidingtiles.filewriters;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class FileLogin {
    private static final String LOGIN_INFORMATION = "USER_LOGIN.txt";
    private FileLogin() {

    }

    /**
     * Used to check if the user exists in the file of users
     * @param ctx the context which calls the this function
     * @return whether the user name exists in the file or not
     */
    public static ArrayList<String> getUserLogin(Context ctx){
        String input;
        ArrayList<String> users = new ArrayList<>();
        try{
            InputStream inputStream = ctx.openFileInput(LOGIN_INFORMATION);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //System.out.println("inputstream length: " + inputStream.)
            while((input = reader.readLine()) != null){
                users.add(input);
                Log.e("READING", input + "\n");
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            Log.e("File read", "File not found: " + e.toString());
        } catch (IOException e){
            Log.e("File read", "Unable to read file: " + e.toString());
        }
        return users;
    }

    public static void writeUserLogin(Context ctx, ArrayList<String> users){
        try {
            OutputStreamWriter writer = new OutputStreamWriter(ctx.openFileOutput(LOGIN_INFORMATION,
                    MODE_PRIVATE));
            for(String user: users){
                writer.write(user + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e){
            Log.e("File write", "File not found: " + e.toString());
        } catch (IOException e){
            Log.e("File write", "Unable write file: " + e.toString());
        }
    }
}
