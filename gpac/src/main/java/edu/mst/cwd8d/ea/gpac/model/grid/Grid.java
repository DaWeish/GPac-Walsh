package edu.mst.cwd8d.ea.gpac.model.grid;

import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This interface describes methods for interacting with a 2D grid
 */
public interface Grid {

    /**
     * This enum defines the square cardinal directions
     */
    enum Direction { UP, DOWN, LEFT, RIGHT }

    /**
     * Gets the number of squares for the width of the grid
     * @return number of squares for the grid width
     */
    int getWidth();

    /**
     * Gets the number of squares for the height of the grid
     * @return the number of squares for the grid height
     */
    int getHeight();

    /**
     * Gets the bottom left GridSquare which defines the min x and y values
     * @return the bottom left square
     */
    GridSquare getOrigin();

    /**
     * This method gets the square for the given coordinates
     * @param x coordinate
     * @param y coordinate
     * @return a GridSquare representing the requested coordinates
     */
    GridSquare getSquare(int x, int y);

    /**
     * Gets the x coordinate of the origin square
     * @return the x coordinate of the origin square
     */
    int getOriginX();

    /**
     * Gets the y coordinate of the origin square
     * @return the y coordinate of the origin square
     */
    int getOriginY();

    /**
     * Gets all of the neighbors of the given square even if they are not on the grid
     * @param square neighbors are found relative to this square
     * @return a non-empty list of neighboring squares
     */
    List<GridSquare> getAllSquareNeighbors(GridSquare square);

    /**
     * Gets all valid neighbors of the given square
     * @param square neighbors are found relative to this square
     * @return a possibly empty list of neighboring squares which are on the grid
     */
    List<GridSquare> getSquareNeighbors(GridSquare square);

    /**
     * Gets the square in the given direction from the passed in square
     * @param square reference
     * @param direction which neighbor to return
     * @return a GridSquare which may or may not be on the grid
     */
    GridSquare getNeighbor(GridSquare square, Direction direction);

    /**
     * Whether or not the specified coordinates are on the grid
     * @param x coordinate
     * @param y coordinate
     * @return true if the point is a valid grid square, false otherwise
     */
    boolean onGrid(int x, int y);

    /**
     * Whether or not the specified square is on the grid
     * @param square check if its on the grid
     * @return true if the square is on the grid, false otherwise
     */
    boolean onGrid(GridSquare square);

    /**
     * Get the manhattan distance between two squares
     * @param x1 x coordinate first square
     * @param y1 y coordinate first square
     * @param x2 x coordinate second square
     * @param y2 y coordiante second square
     * @return distance equal to the sum of the difference in x and y
     */
    int manhattanDistance(int x1, int y1, int x2, int y2);

    /**
     * Get the manhattan distance between two squares
     * @param square1 first square
     * @param square2 second square
     * @return distance equal to the sum of the difference in x and y
     */
    int manhattanDistance(GridSquare square1, GridSquare square2);

    /**
     * Get the straight line distance between two squares
     * @param x1 x coordinate first square
     * @param y1 y coordinate first square
     * @param x2 x coordinate second square
     * @param y2 y coordinate second square
     * @return distance equal to the line distance between the points
     */
    double lineDistance(int x1, int y1, int x2, int y2);

    /**
     * Get the straight line distance between two squares
     * @param square1 first square
     * @param square2 second square
     * @return distance equal to the line distance between the points
     */
    double lineDistance(GridSquare square1, GridSquare square2);
}
