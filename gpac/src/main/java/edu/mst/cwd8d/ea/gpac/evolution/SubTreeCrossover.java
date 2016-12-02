package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.evolution.genetics.GeneRecombinator;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class uses the GeneRecombinator interface to recombine two genetic trees
 */
public class SubTreeCrossover implements GeneRecombinator<GeneticTree> {
    private Random random;

    public SubTreeCrossover(Random random) {
        this.random = random;
    }

    @Override
    public GeneticTree recombine(GeneticTree leftParent, GeneticTree rightParent) {
        GeneticTree leftCopy = leftParent.copy();
        GeneticTree rightCopy = rightParent.copy();

        GPacExpressionTreeNode leftCrossover = leftCopy.getRandomNode(
                leftCopy.getRoot(),
                Math.log(leftCopy.getSize()) * 2,
                random);

        GPacExpressionTreeNode rightCrossover = rightCopy.getRandomNode(
                rightCopy.getRoot(),
                Math.log(rightCopy.getSize()) * 2,
                random);

        GPacExpressionTreeNode leftCrossoverParent = leftCrossover.getParent(); // these could be null
        GPacExpressionTreeNode rightCrossoverParent = rightCrossover.getParent();

        // Exchange the left parents child for the right tree
        rightCrossover.setParent(leftCrossoverParent);
        if (leftCrossoverParent != null) {
            if (leftCrossoverParent.getLeft() == leftCrossover) { // its the left child
                leftCrossoverParent.setLeft(rightCrossover);
            } else { // its the right child
                leftCrossoverParent.setRight(rightCrossover);
            }
        } else {
            leftCopy.setRoot(rightCrossover);
        }
        leftCopy.calculateSize();

        // Exchange the right parents child for the left tree
        leftCrossover.setParent(rightCrossoverParent);
        if (rightCrossoverParent != null) {
            if (rightCrossoverParent.getLeft() == rightCrossover) {
                rightCrossoverParent.setLeft(leftCrossover);
            } else {
                rightCrossoverParent.setRight(leftCrossover);
            }
        } else {
            rightCopy.setRoot(leftCrossover);
        }
        rightCopy.calculateSize();

        if (random.nextBoolean()) {
            return leftCopy;
        }  else {
            return rightCopy;
        }
    }
}
