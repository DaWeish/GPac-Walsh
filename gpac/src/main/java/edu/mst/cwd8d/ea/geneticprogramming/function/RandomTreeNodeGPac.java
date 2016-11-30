package edu.mst.cwd8d.ea.geneticprogramming.function;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class returns a random value between left and right
 */
public class RandomTreeNodeGPac extends AbstractFunctionTreeNodeGPac {
    private Random random;

    public RandomTreeNodeGPac(GPacExpressionTreeNode parent, Random random) {
        super(parent);
        this.random = random;
    }

    public RandomTreeNodeGPac(RandomTreeNodeGPac other) {
        super(other);
        this.random = other.random;
    }

    @Override
    protected double calculate(double left, double right) {
        if (left > right) { // make left the smaller value
            double temp = left;
            left = right;
            right = temp;
        }

        return left + (right - left) * random.nextDouble();
    }

    @Override
    public String toString() {
        return "rand(a,b)";
    }

    @Override
    public GPacExpressionTreeNode copy() {
        return new RandomTreeNodeGPac(this);
    }
}
