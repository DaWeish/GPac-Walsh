package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.terminal.AbstractTerminalNodeGPac;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh (cwd8d)
 *
 * Node representing the distance a ghost is to pacman
 */
public class GhostToPacman extends AbstractTerminalNodeGPac {
    public GhostToPacman(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public GhostToPacman(GhostToPacman other) {
        super(other);
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.ghostToPacman;
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new GhostToPacman(this);
    }

    @Override
    public String toString() {
        return "g2p";
    }
}
