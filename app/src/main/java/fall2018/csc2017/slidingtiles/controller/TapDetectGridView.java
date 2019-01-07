package fall2018.csc2017.slidingtiles.controller;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/GestureDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import fall2018.csc2017.slidingtiles.GameManager;
import fall2018.csc2017.slidingtiles.TapMovementController;

/**
 * Used to determine the on screen interactions such as taps and swipes
 */
public class TapDetectGridView extends CustomGridView {

    /**
     * The controller that will be used to make moves based off user input
     */
    private TapMovementController mController;

    /**
     * Initializes a new GestureDetectGridView
     * @param context the screen in which the creation of this object was called in
     */
    public TapDetectGridView(Context context) {
        super(context);
    }

    /**
     * Initializes a new GestureDetectGridView
     * @param context the screen in which the creation of this object was called in
     * @param attrs   the attributes set to used when initializing a GestureDetectGridView
     */
    public TapDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *  Initializes a new GestureDetectGridView
     * @param context the screen in which the creation of this object was called in
     * @param attrs the attributes set to used when initializing a GestureDetectGridView
     * @param defStyleAttr a reference to a style resource
     */
    public TapDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets the appropriate variables needed to provide proper functionality
     * @param context the screen in which this object was created
     */
    protected void init(final Context context) {
        mController = new TapMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                int position = TapDetectGridView.this.pointToPosition
                        (Math.round(event.getX()), Math.round(event.getY()));
                mController.processTapMovement(context, position, true);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }

        });
    }

    /**
     * Called every time a touch event is occurring, used to determine if a tap or swipe is
     * occurring
     * @param ev the current touch event that is occurring.
     * @return whether a "fling" is occurring
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Sets the boardManager for this object
     * @param boardManager the boardManager that will be set to this object
     */
    public void setBoardManager(GameManager boardManager) {
        mController.setBoardManager(boardManager);
    }
}
