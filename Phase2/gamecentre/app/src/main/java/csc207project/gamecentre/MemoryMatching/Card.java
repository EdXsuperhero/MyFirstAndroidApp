package csc207project.gamecentre.MemoryMatching;

import android.support.annotation.NonNull;

import java.io.Serializable;

import csc207project.gamecentre.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Card implements Comparable<Card>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background = R.drawable.card_0;

    /**
     * the back side of the tile, which is always blank.
     */
    private int backside;

    /**
     * The unique id.
     */
    private int id;

    /**
     *  If the card has been matched successfully
     */
    private boolean flipable = true;

    /**
     *  return if the card if flipable
     * @return flipable
     */
    public boolean getFlipable(){return flipable;}

    /**
     *  change the status of if the card if flipable
     */
    public void setFlipable(boolean matched){this.flipable = matched;}

    /**
     * the number on the back of the card
     */
    private int num;

    /**
     * return the number on the back of the card
     * @return num
     * */
    public int getnum(){return num;}

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the backside id.
     * @return the backside id.
     */
    public int getBackside(){
        return backside;
    }

    public void setBackground(int bg){
        this.background = bg;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    //public Card(int id, int background) {
        //this.id = id;
        //this.background = background;
    //}

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param id the id
     * @param num the number on the back of the card
     */
    public Card(int id, int num) {
        this.id = id;
        this.num = num;
        // This looks so ugly.
        // True, but I don't know how to fix it, ha ha ha. : )
        switch (num) {
            case 0:
                this.backside = R.drawable.card_1;
                break;
            case 1:
                this.backside = R.drawable.card_2;
                break;
            case 2:
                this.backside = R.drawable.card_3;
                break;
            case 3:
                this.backside = R.drawable.card_4;
                break;
            case 4:
                this.backside = R.drawable.card_5;
                break;
            case 5:
                this.backside = R.drawable.card_6;
                break;
            case 6:
                this.backside = R.drawable.card_7;
                break;
            case 7:
                this.backside = R.drawable.card_8;
                break;
            case 8:
                this.backside = R.drawable.card_9;
                break;
            case 9:
                this.backside = R.drawable.card_10;
                break;
            case 10:
                this.backside = R.drawable.card_11;
                break;
            case 11:
                this.backside = R.drawable.card_12;
                break;
            case 12:
                this.backside = R.drawable.card_13;
                break;
            case 13:
                this.backside = R.drawable.card_14;
                break;
            case 14:
                this.backside = R.drawable.card_15;
                break;
            case 15:
                this.backside = R.drawable.card_16;
                break;
            case 16:
                this.backside = R.drawable.card_17;
                break;
            case 17:
                this.backside = R.drawable.card_18;
                break;
            default:
                this.backside = R.drawable.card_0;
                break;
        }
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}
