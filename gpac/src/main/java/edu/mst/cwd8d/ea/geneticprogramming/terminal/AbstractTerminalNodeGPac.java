package edu.mst.cwd8d.ea.geneticprogramming.terminal;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This class is a base class for Terminal nodes in the Exression Tree
 */
public abstract class AbstractTerminalNodeGPac implements GPacExpressionTreeNode {
    private GPacExpressionTreeNode parent;

    public AbstractTerminalNodeGPac(GPacExpressionTreeNode parent) {
        this.parent = parent;
    }

    public AbstractTerminalNodeGPac(AbstractTerminalNodeGPac other) {
        this.parent = other.parent;
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
    public GPacExpressionTreeNode getParent() {
        return parent;
    }

    @Override
    public void setLeft(GPacExpressionTreeNode node) {}

    @Override
    public void setRight(GPacExpressionTreeNode node) {}

    @Override
    public void setParent(GPacExpressionTreeNode node) {
        this.parent = node;
    }

    @Override
    public boolean terminalNode() {
        return true;
    }

    @Override
    public abstract GPacExpressionTreeNode copy();
}
