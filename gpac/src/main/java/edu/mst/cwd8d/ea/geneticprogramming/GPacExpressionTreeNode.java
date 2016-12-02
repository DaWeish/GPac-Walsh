package edu.mst.cwd8d.ea.geneticprogramming;

import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This interface describes operations of a node in an Expression Tree
 */
public abstract class GPacExpressionTreeNode {
    private GPacExpressionTreeNode parent;

    public GPacExpressionTreeNode(GPacExpressionTreeNode parent) {
        this.parent = parent;
    }
    /**
     * Recursively obtain the value of this node
     * @return the evaluated result of the expression tree
     */
    public abstract double evaluate(GPacStats stats);

    public abstract GPacExpressionTreeNode getLeft();
    public abstract GPacExpressionTreeNode getRight();
    public GPacExpressionTreeNode getParent() {
        return parent;
    }

    public abstract void setLeft(GPacExpressionTreeNode node);
    public abstract void setRight(GPacExpressionTreeNode node);
    public void setParent(GPacExpressionTreeNode node) {
        this.parent = node;
    }

    public abstract boolean terminalNode();

    /**
     * Returns a copy of this GPacExpressionTreeNode
     * @return a copy of this Node
     */
    public abstract GPacExpressionTreeNode copy();
}
