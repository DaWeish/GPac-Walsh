package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;

/**
 * @author Connor Walsh
 *
 * This node uses Subtraction to evaluate
 */
public class SubtractionTreeNodeGPac extends AbstractFunctionTreeNodeGPac {

    public SubtractionTreeNodeGPac(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public SubtractionTreeNodeGPac(SubtractionTreeNodeGPac other) {
        super(other);
    }

    @Override
    protected double calculate(double left, double right) {
        return left - right;
    }

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new SubtractionTreeNodeGPac(this);
    }
}
