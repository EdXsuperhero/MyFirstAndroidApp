package csc207project.gamecentre.MemoryMatchingPics;

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
    private int background = R.drawable.tile_0;

    /**
     * the back side of the tile, which is always blank.
     */
    private int backside;

    /**
     * The unique id.
     */
    private int id;

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
    public Card(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param id the id
     */
    public Card(int id) {
        this.id = id;
        // This looks so ugly.
        // True, but I don't know how to fix it, ha ha ha. : )
        switch (this.id) {
            case 0:
                this.backside = R.drawable.tile_1;
                break;
            case 1:
                this.backside = R.drawable.tile_2;
                break;
            case 2:
                this.backside = R.drawable.tile_3;
                break;
            case 3:
                this.backside = R.drawable.tile_4;
                break;
            case 4:
                this.backside = R.drawable.tile_5;
                break;
            case 5:
                this.backside = R.drawable.tile_6;
                break;
            case 6:
                this.backside = R.drawable.tile_7;
                break;
            case 7:
                this.backside = R.drawable.tile_8;
                break;
            case 8:
                this.backside = R.drawable.tile_9;
                break;
            case 9:
                this.backside = R.drawable.tile_10;
                break;
            case 10:
                this.backside = R.drawable.tile_11;
                break;
            case 11:
                this.backside = R.drawable.tile_12;
                break;
            case 12:
                this.backside = R.drawable.tile_13;
                break;
            case 13:
                this.backside = R.drawable.tile_14;
                break;
            case 14:
                this.backside = R.drawable.tile_15;
                break;
            case 15:
                this.backside = R.drawable.tile_16;
                break;
            default:
                this.backside = R.drawable.tile_0;
                break;
        }
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}
