package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * Abstract base implementation of the GPacExpressionTreeNode interface for evaluating a GP Tree
 */
public abstract class AbstractFunctionTreeNodeGPac implements GPacExpressionTreeNode {
    public GPacExpressionTreeNode left;
    public GPacExpressionTreeNode right;
    public GPacExpressionTreeNode parent;

    public AbstractFunctionTreeNodeGPac(GPacExpressionTreeNode parent) {
        this.parent = parent;
    }

    public AbstractFunctionTreeNodeGPac(AbstractFunctionTreeNodeGPac node) {
        this.parent = node.parent;
        this.left = node.left;
        this.right = node.right;
    }

    @Override
    public GPacExpressionTreeNode getLeft() {
        return left;
    }

    @Override
    public GPacExpressionTreeNode getRight() {
        return right;
    }

    @Override
    public GPacExpressionTreeNode getParent() { return parent; }

    @Override
    public boolean terminalNode() {
        return false;
    }

    @Override
    public void setLeft(GPacExpressionTreeNode node) {
        left = node;
    }

    @Override
    public void setRight(GPacExpressionTreeNode node) {
        right = node;
    }

    @Override
    public void setParent(GPacExpressionTreeNode node) { parent = node; }

    @Override
    public abstract GPacExpressionTreeNode copy();

    /**
     * Override this method to implement different node operations
     * @param left the value from the left child
     * @param right the value from the right child
     * @return the operation result
     */
    protected abstract double calculate(double left, double right);

    @Override
    public double evaluate(GPacStats stats) {
        if (left != null && right != null) {
            return calculate(left.evaluate(stats), right.evaluate(stats));
        } else {
            System.out.println("One of the leaves to an expression is null");
            return 0.0;
        }
    }
}
