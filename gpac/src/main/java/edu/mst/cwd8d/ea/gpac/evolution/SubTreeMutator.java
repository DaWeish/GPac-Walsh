package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.evolution.genetics.GeneMutator;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class does sub tree mutation on a Genetic Tree
 */
public class SubTreeMutator implements GeneMutator<GeneticTree> {
    private Random random;
    private double mutationRate;

    public SubTreeMutator(Random random, double mutationRate) {
        this.random = random;
        this.mutationRate = mutationRate;
    }

    @Override
    public GeneticTree mutate(GeneticTree gene) {
        if (random.nextDouble() > mutationRate) return gene;

        System.out.println("Mutating the gene.");

        double selectionChance = 1.0 / Math.log(gene.getSize());
        GPacExpressionTreeNode mutationPoint =
                gene.getRandomNode(gene.getRoot(), selectionChance, random);

        if (mutationPoint == null) {
            System.out.println("ERROR! MutationPoint that was found was null!");
            return gene;
        }

        GPacExpressionTreeNode mutatedTree;
        if (random.nextBoolean()) {
            mutatedTree = gene.grow(5, null, random);
        } else {
            mutatedTree = gene.full(5, null, random);
        }

        GPacExpressionTreeNode parent = mutationPoint.getParent();
        mutatedTree.setParent(parent);
        if (parent != null) {
            if (parent.getLeft() == mutationPoint) {
                parent.setLeft(mutatedTree);
            } else {
                parent.setRight(mutatedTree);
            }
        } else {
            gene.setRoot(mutatedTree);
        }
        gene.calculateSize();

        return gene;
    }
}
