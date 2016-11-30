package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;

/**
 * @author Connor Walsh
 *
 * This node uses the addition operator to evaluate
 */
public class AdditionTreeNodeGPac extends AbstractFunctionTreeNodeGPac {

    public AdditionTreeNodeGPac(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public AdditionTreeNodeGPac(AdditionTreeNodeGPac other) {
        super(other);
    }

    @Override
    protected double calculate(double left, double right) {
        return left + right;
    }

    @Override
    public String toString() {
        return "+";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new AdditionTreeNodeGPac(this);
    }
}
