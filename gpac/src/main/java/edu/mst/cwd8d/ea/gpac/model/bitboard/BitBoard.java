package edu.mst.cwd8d.ea.gpac.model.bitboard;

import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This interface describes a simple bitboard useful for storing an array of boolean values
 */
public interface BitBoard {
    /**
     * This method sets the bit at the given square
     * @param square where to set the bit for. Throws NoSuchElementException if invalid square
     */
    void set(GridSquare square);
    void set(int x, int y);

    /**
     * Sets all of the bits in the BitBoard
     */
    void set();

    /**
     * This method clears the bit at the given square
     * @param square where to clear the bit from. Throws NoSuchElementException if invalid square
     */
    void clear(GridSquare square);
    void clear(int x, int y);

    /**
     * Clears all of the bits in the BitBoard
     */
    void clear();

    /**
     * Get the number of bits set to true
     * @return the number of bits set
     */
    int countSet();

    /**
     * This method gets the boolean value of the bit at the given square
     * @param square where to read the bit from. Throws NoSuchElementException if invalid square
     * @return true if the bit is set, false otherwise
     */
    boolean get(GridSquare square);
    boolean get(int x, int y);

    /**
     * Get the width of the BitBoard
     * @return width of the board
     */
    int width();

    /**
     * Get the height of the BitBoard
     * @return the height of the board
     */
    int height();
}
