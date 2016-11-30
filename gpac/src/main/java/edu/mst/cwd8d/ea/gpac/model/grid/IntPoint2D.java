package edu.mst.cwd8d.ea.gpac.model.grid;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class describes a simple mutable point in discrete 2D space.
 *
 * It also implements the GridSquare interface which is an immutable point in discrete 2D Space
 * All immutable methods are described in the GridSquare Interface
 */
public class IntPoint2D implements GridSquare {
    private int x;
    private int y;

    public IntPoint2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /**
     * Sets the x coordinate
     * @param x coordinate
     */
    public void setX(int x) { this.x = x; }

    /**
     * Sets the y coordinate
     * @param y coordinate
     */
    public void setY(int y) { this.y = y; }

    @Override
    public boolean equals(GridSquare square) {
        return x == square.getX() && y == square.getY();
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
