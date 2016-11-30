package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.gpac.model.bitboard.BitBoard;
import edu.mst.cwd8d.ea.gpac.model.bitboard.BitSetBoard;
import edu.mst.cwd8d.ea.gpac.model.grid.BaseGrid;
import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This abstract base class implements the active functionality for GridSquares
 */
public abstract class AbstractGameGrid extends BaseGrid implements GameGrid {
    private final BitSetBoard inactiveBoard;
    HashSet<GridSquare> inactiveSquares;
    HashSet<GridSquare> activeSquares;

    public AbstractGameGrid(int width, int height, int originX, int originY) {
        super(width, height, originX, originY);
        inactiveBoard = new BitSetBoard(width, height);
        activeSquares = new HashSet<>();
        inactiveSquares = new HashSet<>();

        for (int x = originX; x < originX + width; ++x) {
            for (int y = originY; y < originY + height; ++y) {
                activeSquares.add(getSquare(x, y));
            }
        }
    }

    public AbstractGameGrid(AbstractGameGrid other) {
        super(other);
        this.inactiveBoard = new BitSetBoard(other.inactiveBoard);

        this.inactiveSquares = new HashSet<>(other.inactiveSquares);
        this.activeSquares = new HashSet<>(other.activeSquares);
    }

    @Override
    public boolean isActive(GridSquare square) {
        return onGrid(square) && !inactiveBoard.get(square);
    }

    @Override
    public void setActive(GridSquare square) {
        inactiveBoard.clear(square);
        if (inactiveSquares.contains(square)) {
            inactiveSquares.remove(square);
        }
        activeSquares.add(square);
    }

    @Override
    public void setAllActive() {
        inactiveBoard.clear();
        for (GridSquare square : inactiveSquares) {
            activeSquares.add(square);
        }
        inactiveSquares.clear();
    }

    @Override
    public void setAllInactive() {
        inactiveBoard.set();
        for (GridSquare square : activeSquares) {
            inactiveSquares.add(square);
        }
        activeSquares.clear();
    }

    @Override
    public void setInactive(GridSquare square) {
        if (isEmpty(square)) {
            inactiveBoard.set(square);
            if (activeSquares.contains(square)) {
                activeSquares.remove(square);
            }
            inactiveSquares.add(square);
        } else {
            // Square was not empty, cannot make it inactive
            throw new IllegalStateException("Cannot set square inactive when it is occupied!" + square);
        }
    }

    /**
     * This method returns a list of all squares that are either active, or inactive
     * @param active if true, get active squares, if false, get inactive squares
     * @return a possibly empty list of the requested square types
     */
    private List<GridSquare> getSquares(boolean active) {
        if (active) {
            return new ArrayList<>(activeSquares);
        } else {
            return new ArrayList<>(inactiveSquares);
        }
    }

    @Override
    public List<GridSquare> getActive() {
        return getSquares(true);
    }

    @Override
    public List<GridSquare> getInactive() {
        return getSquares(false);
    }

    @Override
    public List<GridSquare> getInactiveBorder() {
        List<GridSquare> border = new ArrayList<>();
        for (GridSquare square : getInactive()) {
            boolean addSquare = false;
            for (GridSquare neighbor : getSquareNeighbors(square)) {
                if (isActive(neighbor)) addSquare = true;
            }

            if (addSquare) border.add(square);
        }
        return border;
    }

    @Override
    public List<GridSquare> getInactiveNeighbors(GridSquare square) {
        List<GridSquare> neighbors = getSquareNeighbors(square);
        ArrayList<GridSquare> result = new ArrayList<>();
        for (GridSquare currentSquare : neighbors) {
            if (!isActive(currentSquare)) {
                result.add(currentSquare);
            }
        }
        return result;
    }

    @Override
    public int countInactive() {
        return inactiveBoard.countSet();
    }
}
