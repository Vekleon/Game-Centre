package fall2018.csc2017.slidingtiles;

/*
Taken from:
https://github.com/DaveNOTDavid/sample-puzzle/blob/master/app/src/main/java/com/davenotdavid/samplepuzzle/CustomAdapter.java

This Class is an overwrite of the Base Adapter class
It is designed to aid setting the button sizes and positions in the GridView
 */


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * The class Responsible for setting up the display correctly for grid views
 *
 * @author Group_0645
 * @since 2018-11-23
 */
public class CustomAdapter extends BaseAdapter {
    /**
     * The array of buttons on the grid
     */
    private ArrayList<Button> mButtons;

    /**
     * The height and width of the grid
     */
    private int mColumnWidth, mColumnHeight;

    /**
     * The constructor of the Custom Adapter, sets up provided variables
     * @param buttons the array list of buttons
     * @param columnWidth the width of the grid
     * @param columnHeight the height of the grid
     */
    public CustomAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        mButtons = buttons;
        mColumnWidth = columnWidth;
        mColumnHeight = columnHeight;
    }

    /**
     * Returns the amount of buttons on the grid
     * @return the # of buttons on the grid
     */
    @Override
    public int getCount() {
        return mButtons.size();
    }

    /**
     * Gets the specific button at the provided button position
     * @param position the desired position of the button
     * @return the button at the desired position
     */
    @Override
    public Object getItem(int position) {
        return mButtons.get(position);
    }

    /**
     * returns the inputted value
     * @param position the position on the board
     * @return the position on the board
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Sets the layout of the button on the screen
     * @param position the position of the button on the board
     * @param convertView the view converter used to set up button position
     * @param parent ???
     * @return returns the button that has just been set in place.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = mButtons.get(position);
        } else {
            button = (Button) convertView;
        }

        android.widget.AbsListView.LayoutParams params =
                new android.widget.AbsListView.LayoutParams(mColumnWidth, mColumnHeight);
        button.setLayoutParams(params);

        return button;
    }
}
