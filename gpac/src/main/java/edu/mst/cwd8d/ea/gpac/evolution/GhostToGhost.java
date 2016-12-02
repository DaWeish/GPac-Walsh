package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.terminal.AbstractTerminalNodeGPac;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class represents a node that gives the distance of a ghost to the nearest other ghost
 */
public class GhostToGhost extends AbstractTerminalNodeGPac {
    public GhostToGhost(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public GhostToGhost(GhostToGhost other) {
        super(other);
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.ghostToGhost;
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new GhostToGhost(this);
    }
}
