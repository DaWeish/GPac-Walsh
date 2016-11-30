package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.gpac.model.grid.Grid;
import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This interface describes a Grid suitable for simple games where some squares are blocked and pieces can be placed
 * on any unblocked square.
 */
public interface GameGrid extends Grid {
    /**
     * This interface describes elements that can be placed on the GameGrid
     */
    interface GamePiece {
        GridSquare getLocation();
        int getId();
    }

    /**
     * This method is used to determine if a square is active or not
     * @param square location to check
     * @return true if square is active, false otherwise. If off the grid, returns false.
     */
    boolean isActive(GridSquare square);

    /**
     * This method is used to make a square active (pieces can be placed on it)
     * Throws exception if square is not on the grid
     * @param square location to make active
     */
    void setActive(GridSquare square);

    /**
     * This method is used to make a square inactive (no pieces can be placed on it)
     * @param square an empty square, otherwise throws exception
     */
    void setInactive(GridSquare square);

    /**
     * Gets the total number of inactive squares
     * @return the number of inactive squares
     */
    int countInactive();

    /**
     * This function makes all squares active again
     */
    void setAllActive();

    /**
     * This function makes all squares inactive
     */
    void setAllInactive();

    /**
     * Check if a square is empty
     * @param square location to check
     * @return true if the square is empty, false if it is occupied. Always true for squares off the grid
     */
    boolean isEmpty(GridSquare square);

    /**
     * This method returns a list of all the inactive GridSquares currently on the grid
     * @return a possibly empty list of all inactive GridSquares
     */
    List<GridSquare> getInactive();

    /**
     * This method returns a list of GridSquares which are inactive but are adjacent to an active square
     * while still remaining on the grid.
     * @return possibly empty list of inactive squares on the border
     */
    List<GridSquare> getInactiveBorder();

    /**
     * This method returns a list of GridSqures which are inactive but adjacent to the given square
     * while still remaining on the grid.
     * @return
     */
    List<GridSquare> getInactiveNeighbors(GridSquare square);

    /**
     * This method returns a list of all the active GridSquares currently on the grid
     * @return a possibly empty list of all active GridSquares
     */
    List<GridSquare> getActive();

    /**
     * Returns a list of all pieces on the current location
     * @param square location to get pieces from
     * @return a possibly empty list of pieces or exception thrown if square is not on the grid
     */
    List<? extends GamePiece> getPieces(GridSquare square);

    /**
     * This method returns the first game piece at the given square with the requested id or null if there isnt one
     * @param square location to get piece from
     * @param id id of the piece to look for
     * @return the requested piece or null if not there
     */
    GamePiece getPiece(GridSquare square, int id);

    /**
     * This method gets all of the pieces on the GameGrid
     * @return a possibly empty list containing all pieces on the Grid
     */
    List<? extends GamePiece> getAllPieces();

    /**
     * Places the specified piece on the board if the square is active
     * @param x coordinate
     * @param y coordinate
     * @param id of the piece
     * @param description Name of the piece to print in output
     * @return the newly placed piece, or null if unable to place piece
     */
    GamePiece placePiece(int x, int y, int id, String description);

    /**
     * Method for moving a piece from one square to another
     * @param piece reference of piece to move
     * @param dest where to move the piece to
     * @return true if the piece was moved, false otherwise
     */
    boolean movePiece(GamePiece piece, GridSquare dest);

    /**
     * Method for moving a piece in a direction
     * @param piece reference of piece to move
     * @param dir which direction to move in
     * @return true if the piece was moved, false otherwise
     */
    boolean movePiece(GamePiece piece, Direction dir);

    /**
     * This function removes a GamePiece from the GameGrid
     * @param piece the piece to remove
     */
    void removePiece(GamePiece piece);

    /**
     * Set the GameGrid back to its initial state with no pieces and all squares active
     */
    void reset();
}
