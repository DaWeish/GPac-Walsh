package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.evolution.genetics.GeneSeeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class is used to seed controller tree's for the Ghosts
 */
public class GhostRampedHalfAndHalfSeeder implements GeneSeeder<GeneticTree> {
    private Random random;
    private int maxDepth;

    public GhostRampedHalfAndHalfSeeder(Random random, int maxDepth) {
        this.random = random;
        this.maxDepth = maxDepth / 2;
    }

    @Override
    public GeneticTree getGene() {
        int depth = random.nextInt(maxDepth) + maxDepth;
        return new GhostGeneticTree(random, depth, random.nextBoolean());
    }

    @Override
    public List<GeneticTree> getGene(int size) {
        ArrayList<GeneticTree> genes = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            genes.add(getGene());
        }
        return genes;
    }
}
