package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;
import edu.mst.cwd8d.ea.gpac.model.grid.IntPoint2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class implements a grid which can be used to represent the grid state of GPac
 */
public class PieceArrayGameGrid extends AbstractGameGrid {
    private final ArrayList<SimpleGamePiece> pieces;

    /**
     * This class implements the GamePiece interface
     */
    private class SimpleGamePiece implements GamePiece {
        private IntPoint2D location;
        private int id;
        private String description;

        public SimpleGamePiece(int x, int y, int id, String description) {
            this.location = new IntPoint2D(x, y);
            this.id = id;
            this.description = description;
        }

        public SimpleGamePiece(SimpleGamePiece other) {
            this.location = new IntPoint2D(other.getLocation().getX(), other.getLocation().getY());
            this.id = other.id;
            this.description = other.description;
        }

        @Override
        public GridSquare getLocation() {
            return location;
        }

        /**
         * Setter for the location of the piece
         * @param x coordinate
         * @param y coordinate
         */
        public void setLocation(int x, int y) {
            location.setX(x);
            location.setY(y);
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return description + " " + location;
        }
    }

    /**
     * Using a given GamePiece, find the matching SimpleGamePiece on the local board
     * @param piece which piece to find
     * @return the requested piece or null if it does not match any pieces by reference
     */
    private SimpleGamePiece findPiece(GamePiece piece) {
        for (SimpleGamePiece current : pieces) {
            if (piece == current) return current;
        }
        return null;
    }

    public PieceArrayGameGrid(int width, int height, int originX, int originY) {
        super(width, height, originX, originY);

        pieces = new ArrayList<>();
    }

    public PieceArrayGameGrid(PieceArrayGameGrid other) {
        super(other);
        pieces = new ArrayList<>();

        for (SimpleGamePiece piece : other.pieces) {
            pieces.add(new SimpleGamePiece(piece));
        }
    }

    @Override
    public boolean isEmpty(GridSquare square) {
        boolean empty = true;
        for (GamePiece piece : pieces) {
            if (piece.getLocation().equals(square)) empty = false;
        }
        return empty;
    }

    @Override
    public List<? extends GamePiece> getAllPieces() {
        return new ArrayList<>(pieces);
    }

    @Override
    public GamePiece getPiece(GridSquare square, int id) {
        for (SimpleGamePiece piece : pieces) {
            if (piece.getLocation().equals(square) && piece.getId() == id)
                return piece;
        }
        return null;
    }

    @Override
    public List<? extends GamePiece> getPieces(GridSquare square) {
        List<GamePiece> result = new ArrayList<>();
        for (SimpleGamePiece piece : pieces) {
            if (piece.getLocation().equals(square))
                result.add(piece);
        }
        return result;
    }

    @Override
    public GamePiece placePiece(int x, int y, int id, String description) {
        SimpleGamePiece piece = null;
        GridSquare square = getSquare(x, y);

        if (onGrid(square) && isActive(square)) {
            piece = new SimpleGamePiece(x, y, id, description);
            pieces.add(piece);
        }
        return piece;
    }

    @Override
    public boolean movePiece(GamePiece piece, GridSquare dest) {
        SimpleGamePiece actualPiece = findPiece(piece);
        if (actualPiece == null) {
            System.out.println("Unable to find piece on grid!");
            return false;
        }

        if (onGrid(dest)) {
            if (isActive(dest)) {
                actualPiece.setLocation(dest.getX(), dest.getY());
                return true;
            } else {
                System.out.println(dest + " was not active!");
                return false;
            }
        } else {
            System.out.println(dest + "was not on the grid!");
            return false;
        }
    }

    @Override
    public boolean movePiece(GamePiece piece, Direction dir) {
        return movePiece(piece, getNeighbor(piece.getLocation(), dir));
    }

    @Override
    public void removePiece(GamePiece piece) {
        pieces.remove(piece);
    }

    @Override
    public void reset() {
        pieces.clear();
        setAllActive();
    }
}
