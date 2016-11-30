package edu.mst.cwd8d.ea.geneticprogramming.terminal;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.gpac.model.game.GPacStats;

/**
 * @author Connor Walsh
 *
 * This node evaluates to a floating point constant
 */
public class ConstantNodeGPac extends AbstractTerminalNodeGPac {
    private double constant;

    public ConstantNodeGPac(GPacExpressionTreeNode parent, double constant) {
        super(parent);
        this.constant = constant;
    }

    public ConstantNodeGPac(ConstantNodeGPac other) {
        super(other);
        this.constant = other.constant;
    }

    @Override
    public double evaluate(GPacStats stats) {
        return constant;
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new ConstantNodeGPac(this);
    }

    @Override
    public String toString() {
        return "" + constant;
    }
}
