package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.widget.Toast;

/**
 * Used to make changes to the sliding tile game
 */
public class TapMovementController {

    /**
     * The GameManager that will be manipulated
     */
    private GameManager boardManager = null;

    /**
     * The initializer for the movementController
     */
    public TapMovementController() {
    }

    /**
     * Sets the boardManager for this object
     * @param boardManager the boardManager that will be manipulated
     */
    public void setBoardManager(GameManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Checks whether a valid move is possible and executes if so
     * @param context the screen in which the method is being called
     * @param position the position on the screen that was tapped
     * @param display the screen that is being displayed
     */
    public void processTapMovement(Context context, int position, boolean display) {
        if (position != -1 && boardManager.isValidTap(position)) {
            boardManager.touchMove(position, false);
            if (boardManager.gameOver()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
