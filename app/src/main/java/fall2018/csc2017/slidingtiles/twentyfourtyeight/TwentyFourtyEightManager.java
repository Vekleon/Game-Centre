package fall2018.csc2017.slidingtiles.twentyfourtyeight;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import fall2018.csc2017.slidingtiles.GameManager;
import fall2018.csc2017.slidingtiles.ProxyBitmap;
import fall2018.csc2017.slidingtiles.Tile;
import fall2018.csc2017.slidingtiles.gamestates.GameState;
import fall2018.csc2017.slidingtiles.gamestates.TwentyFourtyEightGameState;

public class TwentyFourtyEightManager extends GameManager {
    /**
     * The current score
     **/
    private int score = 0;

    /**
     * Whether or not 2048 is on the twentyFourtyEightBoard
     **/
    private boolean hasTwentyFourtyEight = false;

    /**
     * Manage a 2048 board that has been pre-populated
     *
     * @param gameState the state to load from
     */
    public TwentyFourtyEightManager(GameState gameState){
        this.board = gameState.getBoard();
        setGameState(gameState);
    }

    /**
     * Manage a new shuffled twentyFourtyEightBoard.
     */
    public TwentyFourtyEightManager(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;

        // Two "2" tiles initially spawn on 2048 twentyFourtyEightBoard
        tiles.add(new TwentyFourtyEightTile(1));
        tiles.add(new TwentyFourtyEightTile(1));

        // All other tiles are blank
        for (int tileNum = 2; tileNum != numTiles; tileNum++) {
            tiles.add(new TwentyFourtyEightTile(0));
        }

        Collections.shuffle(tiles);
        this.board = new TwentyFourtyEightBoard(tiles, complexity);
        setGameState(new TwentyFourtyEightGameState((TwentyFourtyEightBoard) board, 0));
    }

    /**
     * Return whether or not the game is over
     **/
    public boolean gameOver() {
        return !((TwentyFourtyEightGameState) getGameState()).canMove() ||
                this.hasTwentyFourtyEight;
    }


    /**
     * Return whether the move is a valid move to make
     *
     * @param move - the move to make
     *             0: left move
     *             1: up move
     *             2: right move
     *             3: down move
     * @return whether the move can be made
     */
    public boolean isValidTap(int move) {
        makeRotation(move);
        for(int i = 0; i < board.getComplexity(); i++){
            TwentyFourtyEightTile[] line = ((TwentyFourtyEightBoard)board).getLine(i);
            TwentyFourtyEightTile[] merged = mergeLine(((TwentyFourtyEightBoard)board).moveLine(line), i, true);
            if(!((TwentyFourtyEightBoard)board).compare(line, merged)){
                makeRotation(4 - move);
                return true;
            }
        }
        makeRotation(4 - move);
        return false;
    }

    /**
     * Process a move and change the game state accordingly
     * 0: left move
     * 1: up move
     * 2: right move
     * 3: down move
     *
     * @param move - the move to be made
     */
    public void touchMove(int move, boolean undo) {
        ((TwentyFourtyEightGameState) gameState).push((TwentyFourtyEightBoard) board);
        makeRotation(move);
        makeMove();
        makeRotation(4 - move);
        notifyObservers();
    }

    public void undo(){
        board = ((TwentyFourtyEightGameState) gameState).pop();
    }

    /**
     * Rotates the board the desired amount of time
     * @param rotations the amount of times to rotate the board
     */
    private void makeRotation(int rotations){
        for(int i = 0; i < rotations; i++){
            ((TwentyFourtyEightBoard)board).rotateLeft();
        }
    }

    /**
     * Make a move to do 2048 twentyFourtyEightBoard
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    private void makeMove() {
        boolean needAddTile = false;
        TwentyFourtyEightTile[][] mergedBoard = new TwentyFourtyEightTile[4][4];
        for (int i = 0; i < this.board.getComplexity(); i++) {
            TwentyFourtyEightTile[] line = ((TwentyFourtyEightBoard)board).getLine(i);
            TwentyFourtyEightTile[] merged = mergeLine(((TwentyFourtyEightBoard)board).moveLine(line), i, false);
            mergedBoard[i] = merged;
            if (!needAddTile && !((TwentyFourtyEightBoard)board).compare(line, merged)) {
                needAddTile = true;
            }
        }
        for(int i = 0; i < board.getComplexity(); i++){
            ((TwentyFourtyEightBoard)board).changeRow(i, mergedBoard[i]);
        }

        if (needAddTile) {
            addTile();
        }
    }



    /**
     * Add a random tile onto the twentyFourtyEightBoard
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    public void addTile() {
        if (((TwentyFourtyEightBoard)board).numBlank() != 0) {
            Random random = new Random();
            int row = random.nextInt(board.getComplexity());
            int col = random.nextInt(board.getComplexity());
            while (board.getTile(row, col).getBackgroundId() != 0) {
                row = random.nextInt(board.getComplexity());
                col = random.nextInt(board.getComplexity());
            }
            //More likely to generate 2 tile
            int newTileValue = Math.random() < 0.9 ? 2 : 3;
            ((TwentyFourtyEightBoard)board).changeTileBackground(row, col, newTileValue -1);
        }
    }


    /**
     * Merge any common values on the line and add score
     *
     * Cite: https://github.com/bulenkov/2048/blob/master/src/com/bulenkov/game2048/Game2048.java#L55
     **/
    public TwentyFourtyEightTile[] mergeLine(TwentyFourtyEightTile[] oldLine, int row, boolean testing) {
        LinkedList<TwentyFourtyEightTile> tileShift = new LinkedList<>();
        for (int i = 0; i < board.getComplexity() && oldLine[i].getBackgroundId() != 0; i++) {
            int num = oldLine[i].getBackgroundId();

            if (i < (board.getComplexity() - 1) && oldLine[i].getBackgroundId() == oldLine[i + 1].getBackgroundId()) {
                if(!testing) {
                    ((TwentyFourtyEightBoard)board).changeTileBackground(row, i,
                            oldLine[i].getBackgroundId() + 1);
                    ((TwentyFourtyEightBoard)board).changeTileBackground(row, i + 1, 0);

                    oldLine[i + 1].setBackgroundId(0);
                    oldLine = ((TwentyFourtyEightBoard)board).moveLine(oldLine);

                    addScore(num*2);
                }
                num += 1;
                //Target is 11 since 2^11 = 2048
                if (num == 11) {
                    this.hasTwentyFourtyEight = true;
                }
            }

            tileShift.add(((TwentyFourtyEightBoard)board).getNewTile(num));
        }
        if (tileShift.size() == 0) {
            return oldLine;
        } else {
            ((TwentyFourtyEightBoard)board).ensureSize(tileShift, board.getComplexity());
            return tileShift.toArray(new TwentyFourtyEightTile[board.getComplexity()]);
        }
    }

    /**
     * Increments the score
     * @param addedScore the amount to increase the score by
     */
    public void addScore(int addedScore){
        this.score += addedScore;
    }
    /**
     * Return the score associated with the current gamestate
     **/
    public int getScore() {
        return this.score;
    }

    public void setScore(int score){this.score = score;}

    public boolean hasWon(){
        return this.hasTwentyFourtyEight;
    }

}
