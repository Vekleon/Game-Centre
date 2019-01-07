package fall2018.csc2017.slidingtiles.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.GameManager;

public class SwipeMovementController {
    /**
     * The  GameManager that will be manipulated
     */
    private GameManager manager = null;

    /**
     * initializer for the swipeController
     */
    public SwipeMovementController(){

    }

    void setGameManager(GameManager manager){this.manager = manager;}

    public void processHorizontalSwipe(Context context, float direction){
        int rotations = direction > 0 ? 2 : 0;
        if(manager.isValidTap(rotations)){
            finishMove(context, rotations);
        } else{
            Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
        }
    }

    public void processVerticalSwipe(Context context, float direction){
        int rotations = direction > 0 ? 3 : 1;
        if(manager.isValidTap(rotations)){
            finishMove(context, rotations);
        } else{
            Toast.makeText(context, "Invalid Move", Toast.LENGTH_SHORT).show();
        }
    }

    private void finishMove(Context context, int rotations){
        manager.touchMove(rotations, false);
        if(manager.gameOver()){
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }
}
