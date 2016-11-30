package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Unit tests for PieceArrayGameGrid
 */
public class PieceArrayGameGridTest extends TestCase {
    public PieceArrayGameGridTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PieceArrayGameGridTest.class);
    }

    public void testActiveSquares() {
        PieceArrayGameGrid grid = new PieceArrayGameGrid(10, 10, 0, 0);
        GridSquare square = grid.getSquare(0, 5);
        assertTrue(grid.isEmpty(square));
        assertTrue(grid.isActive(square));
        grid.setInactive(square);
        assertFalse(grid.isActive(square));

        grid.setInactive(grid.getSquare(3, 5));
        grid.setInactive(grid.getSquare(9, 9));
        List<GridSquare> active = grid.getActive();
        assertTrue(active.size() == 97);
        List<GridSquare> inactive = grid.getInactive();
        assertTrue(inactive.size() == 3);
    }

    public void testPlacePiece() {
        GameGrid grid = new PieceArrayGameGrid(10, 12, 0, 0);
        grid.placePiece(0, 0, 0, "First Piece");
        grid.placePiece(2, 2, 1, "Second Piece");
        grid.placePiece(0, 0, 3, "Third Piece");

        List<? extends GameGrid.GamePiece> pieces = grid.getAllPieces();
        assertEquals(pieces.size(), 3);
        List<? extends GameGrid.GamePiece> squarePieces = grid.getPieces(grid.getSquare(0,0));
        assertEquals(squarePieces.size(), 2);

        grid.setInactive(grid.getSquare(4, 4));
    }

    public void testRemovePiece() {
        GameGrid grid = new PieceArrayGameGrid(10, 15, 0, 0);
        Random random = new Random(0);
        for (int i = 0; i < 10; ++i) {
            grid.placePiece(random.nextInt(10), random.nextInt(15), i, "Piece " + i);
        }

        List<? extends GameGrid.GamePiece> pieces = grid.getAllPieces();
        assertEquals(pieces.size(), 10);

        for (GameGrid.GamePiece piece : pieces) {
            grid.removePiece(piece);
        }

        pieces = grid.getAllPieces();
        assertEquals(pieces.size(), 0);
    }

    public void testMovePiece() {
        GameGrid grid = new PieceArrayGameGrid(40, 40, 0, 0);
        GameGrid.GamePiece piece = grid.placePiece(10, 10, 0, "Test Piece");
        assertEquals(piece.getLocation().getX(), 10);
        assertEquals(piece.getId(), 0);
        grid.movePiece(piece, grid.getSquare(20, 21));
        assertEquals(piece.getLocation().getX(), 20);
        assertEquals(piece.getLocation().getY(), 21);
        assertEquals(piece.getId(), 0);
    }
}
