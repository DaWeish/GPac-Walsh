package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;

/**
 * @author Connor Walsh
 *
 * This node uses multiplication to evalue
 */
public class MultiplicationTreeNodeGPac extends AbstractFunctionTreeNodeGPac {

    public MultiplicationTreeNodeGPac(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public MultiplicationTreeNodeGPac(MultiplicationTreeNodeGPac other) {
        super(other);
    }

    @Override
    protected double calculate(double left, double right) {
        return left * right;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new MultiplicationTreeNodeGPac(this);
    }
}
