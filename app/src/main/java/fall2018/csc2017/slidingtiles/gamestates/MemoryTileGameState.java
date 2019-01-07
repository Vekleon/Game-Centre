package fall2018.csc2017.slidingtiles.gamestates;

import fall2018.csc2017.slidingtiles.memorytiles.MemoryTileBoard;

/**
 * The class responsible for recording the state of the memory tiles game
 *
 * @author Group_0645
 * @since 2018-11-04
 */
public class MemoryTileGameState extends GameState {
    /**
     * Initialises the MemoryTile board for MemoryTiles
     *
     * @param board,   the board of which the moves you are keeping track of.
     * @param seconds, as defined.
     **/
    public MemoryTileGameState(MemoryTileBoard board, int seconds) {
        super(board, seconds);
    }
}
