package edu.mst.cwd8d.ea.gpac.model.bitboard;

import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

import java.util.BitSet;
import java.util.NoSuchElementException;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class implements a simple bitboard using a BitSet
 *
 * All methods are documented in the BitBoard interface
 */
public class BitSetBoard extends AbstractBitBoard {
    private final BitSet board;

    public BitSetBoard(int width, int height) {
        super(width, height);
        board = new BitSet(width * height);
    }

    public BitSetBoard(BitSetBoard other) {
        super(other);
        board = new BitSet(width() * height());

        // Initialize the set bits
        for (int i = 0; i < board.length(); ++i) {
            if (other.board.get(i)) board.set(i);
        }
    }

    @Override
    public void set(int x, int y) {
        int index = getIndexFromSquare(x, y);
        if (index >= board.size()) {
            throw new NoSuchElementException("Given coordinates " + x + "," + y + " are not on the bitboard");
        }

        board.set(index);
    }

    @Override
    public void set(GridSquare square) {
        set(square.getX(), square.getY());
    }

    @Override
    public void set() {
        board.set(0, getIndexFromSquare(width() - 1, height() - 1) + 1);
    }

    @Override
    public int countSet() {
        return board.cardinality();
    }

    @Override
    public void clear(int x, int y) {
        int index = getIndexFromSquare(x, y);
        if (index >= board.size()) {
            throw new NoSuchElementException("Given coordinates " + x + "," + y + " are not on the bitboard");
        }

        board.clear(index);
    }

    @Override
    public void clear(GridSquare square) {
        clear(square.getX(), square.getY());
    }

    @Override
    public void clear() {
        board.clear();
    }

    @Override
    public boolean get(int x, int y) {
        int index = getIndexFromSquare(x, y);
        if (index >= board.size()) {
            throw new NoSuchElementException("Given coordinates " + x + "," + y + " are not on the bitboard");
        }

        return board.get(index);
    }

    @Override
    public boolean get(GridSquare square) {
        return get(square.getX(), square.getY());
    }
}
