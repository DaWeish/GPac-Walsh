package edu.mst.cwd8d.ea.gpac.model.grid;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class provides a base class for grid implementations to derive from
 *
 * All methods are documented in the interface class
 */
public abstract class AbstractGrid implements Grid {

    @Override
    public int getOriginX() {
        return getOrigin().getX();
    }

    @Override
    public int getOriginY() {
        return getOrigin().getY();
    }

    /**
     * Gets a list of squares which are neighbors of given square
     * Uses the getNeighbor function to get the neighbor squares
     * @param square neighbors are found relative to this square
     * @return a non-empty list of GridSquares
     */
    @Override
    public List<GridSquare> getAllSquareNeighbors(GridSquare square) {
        ArrayList<GridSquare> result = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            result.add(getNeighbor(square, dir));
        }
        return result;
    }

    /**
     * Gets a list of neighbor squares which are on the grid
     * @param square neighbors are found relative to this square
     * @return a possibly empty list of neighbors
     */
    @Override
    public List<GridSquare> getSquareNeighbors(GridSquare square) {
        ArrayList<GridSquare> result = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            GridSquare neighbor = getNeighbor(square, dir);
            if (onGrid(neighbor)) result.add(neighbor);
        }
        return result;
    }

    @Override
    public GridSquare getNeighbor(GridSquare square, Direction direction) {
        int x = square.getX(), y = square.getY();
        switch (direction) {
            case UP:
                y += 1;
                break;
            case DOWN:
                y -= 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
        }

        return getSquare(x, y);
    }

    /**
     * Whether or not a set of square coordinates is on the grid. Squares are referenced by their bottom left point
     * @param x coordinate
     * @param y coordinate
     * @return true if the square is on the grid, false otherwise
     */
    @Override
    public boolean onGrid(int x, int y) {
        return (x >= getOriginX() && x < (getOriginX() + getWidth())) &&
                (y >= getOriginY() && y < (getOriginY() + getHeight()));
    }

    /**
     * Whether or not a square is on the grid. Square are referenced by their bottom left point
     * @param square check if its on the grid
     * @return true if on the grid, false otherwise
     */
    @Override
    public boolean onGrid(GridSquare square) {
        return onGrid(square.getX(), square.getY());
    }

    @Override
    public int manhattanDistance(int x1, int y1, int x2, int y2) {
        return ((x1 > x2) ? x1 - x2 : x2 - x1) + ((y1 > y2) ? y1 - y2 : y2 - y1);
    }

    @Override
    public int manhattanDistance(GridSquare square1, GridSquare square2) {
        return manhattanDistance(square1.getX(), square1.getY(), square2.getX(), square2.getY());
    }

    @Override
    public double lineDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    @Override
    public double lineDistance(GridSquare square1, GridSquare square2) {
        return lineDistance(square1.getX(), square1.getY(), square2.getX(), square2.getY());
    }
}
