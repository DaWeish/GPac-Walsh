package edu.mst.cwd8d.ea.geneticprogramming;

import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This interface describes operations of a node in an Expression Tree
 */
public interface GPacExpressionTreeNode {
    /**
     * Recursively obtain the value of this node
     * @return the evaluated result of the expression tree
     */
    double evaluate(GPacStats stats);

    GPacExpressionTreeNode getLeft();
    GPacExpressionTreeNode getRight();
    GPacExpressionTreeNode getParent();

    void setLeft(GPacExpressionTreeNode node);
    void setRight(GPacExpressionTreeNode node);
    void setParent(GPacExpressionTreeNode node);

    boolean terminalNode();

    /**
     * Returns a copy of this GPacExpressionTreeNode
     * @return a copy of this Node
     */
    GPacExpressionTreeNode copy();
}
