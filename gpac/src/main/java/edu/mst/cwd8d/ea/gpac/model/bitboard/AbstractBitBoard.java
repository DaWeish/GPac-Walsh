package edu.mst.cwd8d.ea.gpac.model.bitboard;

import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

/**
 * @author Connor Walsh (cwd8d)
 *
 * A base implementation of a BitBoard
 */
public abstract class AbstractBitBoard implements BitBoard {
    private final int width;
    private final int height;

    /**
     * This helper method converts square coordinates into 1D coordinates
     * @param x coordinate
     * @param y coordinate
     * @return the 1D index
     */
    protected int getIndexFromSquare(int x, int y) {
        return x + width * y;
    }

    /**
     * This helper method converts square coordinates into 1D coordinates
     * @param square coordinates
     * @return the 1D index
     */
    protected int getIndexFromSquare(GridSquare square) {
        return getIndexFromSquare(square.getX(), square.getY());
    }

    /**
     * This method gets the x coordinate from a given 1D coordinate index
     * @param index coordinate
     * @return x value of the 2D coordinates
     */
    protected int getXFromIndex(int index) {
        return index % width;
    }

    /**
     * This helper method gets the y coordinate from a given 1D index
     * @param index 1D
     * @return y value of the 2D coordinates
     */
    protected int getYFromIndex(int index) {
        return index / width;
    }

    public AbstractBitBoard(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public AbstractBitBoard(AbstractBitBoard other) {
        this.width = other.width;
        this.height = other.height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }
}
