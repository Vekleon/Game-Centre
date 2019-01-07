package fall2018.csc2017.slidingtiles.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.GridView;

import fall2018.csc2017.slidingtiles.GameManager;

/**
 * Abstract class to customize the functionality of GridView
 */
public abstract class CustomGridView extends GridView {

    /**
     * The detector to determine if a screen tape/swipe occurred
     */
    protected GestureDetector gDetector;

    /**
     * Initializes a new CustomGridView
     *
     * @param context the screen in which the creation of this object was called in
     */
    public CustomGridView(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initializes a new CustomGridView with a set of attributes.
     *
     * @param context the screen in which the creation of this object was called in
     * @param attrs   the set of attributes to be considered when initializing the CustomGridView
     */
    public CustomGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializes a new CustomGridView
     *
     * @param context the screen in which the creation of this object was called in
     * @param attrs the set of attributes to be considered when initializing the CustomGridView
     * @param defStyleAttr a reference to a style resource
     */
    public CustomGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Forces classes that extend this to implement an init
     *
     * @param context the screen in which the creation of this object was called in
     */
    protected abstract void init(Context context);

    /**
     * On a touch event it calls for gDetector's onTouchEventMethod
     *
     * @param ev the current touch event that is occurring
     * @return whether the touch event occurring is consistent or not
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return gDetector.onTouchEvent(ev);
    }

    /**
     * Forces classes that extend this to implement a setter for their boardManager
     *
     * @param boardManager the board manager that will be set to this object
     */
    public abstract void setBoardManager(GameManager boardManager);
}
