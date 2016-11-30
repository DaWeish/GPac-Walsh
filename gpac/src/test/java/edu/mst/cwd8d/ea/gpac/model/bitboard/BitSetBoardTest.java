package edu.mst.cwd8d.ea.gpac.model.bitboard;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Unit tests for the BitBoard interface
 */
public class BitSetBoardTest extends TestCase {
    public BitSetBoardTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(BitSetBoardTest.class);
    }

    public void testSetBits() {
        BitBoard board = new BitSetBoard(10, 10);
        board.set(0, 3);
        board.set(4, 4);

        assertTrue(board.get(0, 3));
        assertTrue(board.get(4, 4));
        assertFalse(board.get(0, 0));
    }
}
