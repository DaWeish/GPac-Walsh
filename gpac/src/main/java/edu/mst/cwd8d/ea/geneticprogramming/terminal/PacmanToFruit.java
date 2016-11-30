package edu.mst.cwd8d.ea.geneticprogramming.terminal;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class returns the distance from pacman to the fruit
 */
public class PacmanToFruit extends AbstractTerminalNodeGPac {

    public PacmanToFruit(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public PacmanToFruit(PacmanToFruit other) {
        super(other);
    }

    @Override
    public double evaluate(GPacStats stats) {
        return stats.pacmanToFruit;
    }

    @Override
    public String toString() {
        return "p2f";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new PacmanToFruit(this);
    }
}
