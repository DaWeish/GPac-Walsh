package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;

/**
 * @author Connor Walsh
 *
 * This class uses division for evalution
 */
public class DivisionTreeNodeGPac extends AbstractFunctionTreeNodeGPac {

    public DivisionTreeNodeGPac(GPacExpressionTreeNode parent) {
        super(parent);
    }

    public DivisionTreeNodeGPac(DivisionTreeNodeGPac other) {
        super(other);
    }

    @Override
    protected double calculate(double left, double right) {
        if (right == 0.0) right = 0.000001;
        return left / right;
    }

    @Override
    public String toString() {
        return "/";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new DivisionTreeNodeGPac(this);
    }
}
