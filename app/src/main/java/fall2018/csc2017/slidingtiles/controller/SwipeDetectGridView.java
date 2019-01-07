package fall2018.csc2017.slidingtiles.controller;

/*
Adapted from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/SwipeDetectGridView.java

This extension of GridView contains built in logic for handling swipes between buttons
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import fall2018.csc2017.slidingtiles.GameManager;

/**
 * Used to determine the on screen interactions such as taps and swipes
 */
public class SwipeDetectGridView extends CustomGridView {
    /**
     * the minimum distance for a swipe to be registered
     */
    public static final int SWIPE_MIN_DISTANCE = 50;

    /**
     * The controller that will be used to make moves based off user input
     */
    private SwipeMovementController mController;

    /**
     * The x and y position of the last tap
     */
    private float mTouchX;
    private float mTouchY;

    /**
     * The screen in which the creation of this object was called in
     */
    private Context context;

    /**
     * Initializes a new SwipeDetectGridView
     *
     * @param context the screen in which the creation of this object was called in
     */
    public SwipeDetectGridView(Context context) {
        super(context);
    }

    /**
     * Initializes a new SwipeDetectGridView
     *
     * @param context the screen in which the creation of this object was called in
     * @param attrs   the attributes set to used when initializing a SwipeDetectGridView
     */
    public SwipeDetectGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Initializes a new SwipeDetectGridView
     *
     * @param context      the screen in which the creation of this object was called in
     * @param attrs        the attributes set to used when initializing a SwipeDetectGridView
     * @param defStyleAttr a reference to a style resource
     */
    public SwipeDetectGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets the appropriate variables needed to provide proper functionality
     *
     * @param context the screen in which this object was created
     */
    protected void init(final Context context) {
        mController = new SwipeMovementController();
        gDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        });
        this.context = context;
    }

    /**
     * Called every time a touch event is occurring, used to determine if a tap or swipe is
     * occurring
     *
     * @param ev the current touch event that is occurring.
     * @return whether a "fling" is occurring
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getActionMasked();
        gDetector.onTouchEvent(ev);

        if (action == MotionEvent.ACTION_DOWN) {
            mTouchX = ev.getX();
            mTouchY = ev.getY();
        } else {

            float horizontalDirection = ev.getX() - mTouchX;
            float verticalDirection = ev.getY() - mTouchY;
            float dX = (Math.abs(horizontalDirection));
            float dY = (Math.abs(verticalDirection));
            if ((dX > SWIPE_MIN_DISTANCE) || (dY > SWIPE_MIN_DISTANCE)) {
                if (dX > dY) {
                    mController.processHorizontalSwipe(context, horizontalDirection);
                } else {
                    mController.processVerticalSwipe(context, verticalDirection);
                }
            }
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * Sets the boardManager for this object
     *
     * @param boardManager the boardManager that will be set to this object
     */
    public void setBoardManager(GameManager boardManager) {
        mController.setGameManager(boardManager);
    }
}
