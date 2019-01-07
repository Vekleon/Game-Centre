package fall2018.csc2017.slidingtiles.twentyfourtyeight;


import fall2018.csc2017.slidingtiles.Tile;

public class TwentyFourtyEightTile extends Tile {
    public TwentyFourtyEightTile(int id){
        super(id);
    }

    public TwentyFourtyEightTile clone(){
        return new TwentyFourtyEightTile(getBackgroundId());
    }
}
