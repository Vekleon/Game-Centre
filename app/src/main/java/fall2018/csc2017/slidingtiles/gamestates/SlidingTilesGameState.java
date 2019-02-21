package fall2018.csc2017.slidingtiles.gamestates;

import android.graphics.drawable.BitmapDrawable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fall2018.csc2017.slidingtiles.GameCentre;
import fall2018.csc2017.slidingtiles.ProxyBitmap;
import fall2018.csc2017.slidingtiles.SlidingBoard;

/**
 * The class responsible for recording all moves and time elapsed.
 *
 * @author Group_0645
 * @since 2018-11-04
 */
public class SlidingTilesGameState extends GameState implements Stack<Integer> {
    /**
     * List of moves of the blank tile that brought us to the current state
     */
    private ArrayList<Integer> moves;

    /**
     * List of proxy bitmap version of the bitmapDrawables used for the game
     */
    ArrayList<ProxyBitmap> proxyBitmaps = new ArrayList<>();

    /**
     * Initialises the stack of moves and prints the time to standard output.
     *
     * @param slidingBoard, the slidingBoard of which the moves you are keeping track of.
     * @param seconds,      as defined.
     **/
    public SlidingTilesGameState(SlidingBoard slidingBoard, int seconds) {
        super(slidingBoard, seconds);
        moves = new ArrayList<>();
    }

    /**
     * Getter for the stack of moves.
     *
     * @return the arraylist (stack) of moves.
     */
    public ArrayList<Integer> getMoves() {
        return this.moves;
    }

    /**
     * Push a move onto the stack.
     *
     * @param i, push the integer representing the move done onto the stack.
     */
    public void push(Integer i) {
        moves.add(i);
    }

    /**
     * Pop a move off the stack and return the value of the popped move.
     *
     * @return the most recently pushed move.
     */
    public Integer pop() {
        return moves.remove(moves.size() - 1);
    }

    /**
     * Method to check if the stack is empty.
     *
     * @return returns true if there are no more moves in the stack.
     */
    public boolean isEmpty() {
        return moves.size() == 0;
    }

    /**
     * Gets the BitmapDrawables from GameCentre and stores them in this class as ProxyBitmaps
     */
    public void setProxyBitmaps() {
        ArrayList<BitmapDrawable> bmps = GameCentre.getGameCentre().getIdToBitmapArray();
        proxyBitmaps = new ArrayList<>();
        for (BitmapDrawable bmp : bmps)
            proxyBitmaps.add(new ProxyBitmap(bmp.getBitmap()));
    }

    public ArrayList<BitmapDrawable> getBitmapDrawables() {
        ArrayList<BitmapDrawable> bmps = new ArrayList<>();
        for (ProxyBitmap bmp : proxyBitmaps)
            bmps.add(new BitmapDrawable(bmp.getBitmap()));
        return bmps;
    }
}
