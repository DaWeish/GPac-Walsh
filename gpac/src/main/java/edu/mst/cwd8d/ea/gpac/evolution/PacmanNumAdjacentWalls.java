package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.terminal.AbstractTerminalNodeGPac;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class is a terminal for getting number of adjacent walls
 */
public class PacmanNumAdjacentWalls extends AbstractTerminalNodeGPac {

    public PacmanNumAdjacentWalls(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public PacmanNumAdjacentWalls(PacmanNumAdjacentWalls other) {
        super(other);
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.pacmanNumAdjacentWalls;
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new PacmanNumAdjacentWalls(this);
    }

    @Override
    public String toString() {
        return "pws";
    }
}
