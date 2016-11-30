package edu.mst.cwd8d.ea.geneticprogramming.terminal;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class represents a terminal node which gives the distance from pacman to the nearest Ghost
 */
public class PacmanToNearestGhost extends AbstractTerminalNodeGPac {

    public PacmanToNearestGhost(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public PacmanToNearestGhost(PacmanToNearestGhost other) {
        super(other);
    }

    @Override
    public String toString() {
        return "p2g";
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.pacmanToNearestGhost;
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new PacmanToNearestGhost(this);
    }
}
