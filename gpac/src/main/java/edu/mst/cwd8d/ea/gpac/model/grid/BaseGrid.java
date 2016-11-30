package edu.mst.cwd8d.ea.gpac.model.grid;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This is a simple base grid which implements the Grid interface
 * It uses an array of GridSquares internally to return in order to reduce memory allocations
 *
 * All methods are documented in the Grid Interface
 */
public class BaseGrid extends AbstractGrid {
    private final int width;
    private final int height;
    private GridSquare[] grid;

    private int getIndexFromSquare(int x, int y) {
        return x + width * y;
    }

    public BaseGrid(int width, int height, int originX, int originY) {
        this.width = width;
        this.height = height;

        grid = new GridSquare[width * height];
        for (int x = originX; x < originX + width; ++x) {
            for (int y = originY; y < originY + height; ++y) {
                grid[getIndexFromSquare(x, y)] = new IntPoint2D(x, y);
            }
        }
    }

    public BaseGrid(BaseGrid other) {
        this.width = other.width;
        this.height = other.height;

        // Since this is immutable it should work
        this.grid = other.grid;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public GridSquare getOrigin() {
        return grid[0];
    }

    @Override
    public GridSquare getSquare(int x, int y) {
        if (onGrid(x, y)) {
            return grid[getIndexFromSquare(x, y)];
        } else {
            return new IntPoint2D(x, y);
        }
    }
}
