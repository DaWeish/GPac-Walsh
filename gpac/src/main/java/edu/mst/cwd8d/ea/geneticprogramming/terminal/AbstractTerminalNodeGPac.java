package edu.mst.cwd8d.ea.geneticprogramming.terminal;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class is a base class for Terminal nodes in the Exression Tree
 */
public abstract class AbstractTerminalNodeGPac extends GPacExpressionTreeNode {
    public AbstractTerminalNodeGPac(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public AbstractTerminalNodeGPac(AbstractTerminalNodeGPac other) {
        super(other);
    }

    @Override
    public abstract double evaluate(GPacStats stats);

    @Override
    public GPacExpressionTreeNode getLeft() {
        return null;
    }

    @Override
    public GPacExpressionTreeNode getRight() {
        return null;
    }

    @Override
    public void setLeft(GPacExpressionTreeNode node) {}

    @Override
    public void setRight(GPacExpressionTreeNode node) {}

    @Override
    public boolean terminalNode() {
        return true;
    }

    @Override
    public abstract GPacExpressionTreeNode copy();
}
