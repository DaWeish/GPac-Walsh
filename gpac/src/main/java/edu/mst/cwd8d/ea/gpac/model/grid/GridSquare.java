package edu.mst.cwd8d.ea.gpac.model.grid;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Describes a location on a Grid
 */
public interface GridSquare {
    /**
     * Gets the x coordinate
     * @return the x coordinate
     */
    int getX();

    /**
     * Gets the y coordinate
     * @return the y coordinate
     */
    int getY();

    /**
     * Equality comparison for GridSquares
     * @param other square to compare to
     * @return true if same x and y, false otherwise
     */
    boolean equals(GridSquare other);
}
