package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.terminal.AbstractTerminalNodeGPac;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class gets the distance to the nearest pill
 */
public class PacmanToNearestPill extends AbstractTerminalNodeGPac {

    public PacmanToNearestPill(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public PacmanToNearestPill(PacmanToNearestPill other) {
        super(other);
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.pacmanToNearestPill;
    }

    @Override
    public String toString() {
        return "p2p";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new PacmanToNearestPill(this);
    }
}
