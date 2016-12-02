package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;
import edu.mst.cwd8d.evolution.selection.ParentSelector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class uses the OverSelection algorithm to choose parents
 */
public class OverSelection implements ParentSelector<GeneticTree> {
    private Random random;
    private int matingPoolSize;

    public OverSelection(Random random, int matingPoolSize) {
        this.random = random;
        this.matingPoolSize = matingPoolSize;
    }

    @Override
    public List<Individual<GeneticTree>> selectParents(Population<GeneticTree> population) {
        ArrayList<Individual<GeneticTree>> matingPool = new ArrayList<>(matingPoolSize);
        List<Individual<GeneticTree>> sortedPopulation = population.getSortedPopulationList();
        List<Individual<GeneticTree>> weakPopulation = new ArrayList<>();

        int groupIndex = sortedPopulation.size() / 2; // split the population in half
        for (int i = 0; i < groupIndex; ++i) {
            weakPopulation.add(sortedPopulation.remove(0));
        }

        for (int i = 0; i < (int)(matingPoolSize * 0.8); ++i) {
            matingPool.add(sortedPopulation.get(random.nextInt(sortedPopulation.size())));
        }

        for (int i = 0; i < (int)(matingPoolSize * 0.2); ++i) {
            matingPool.add(weakPopulation.get(random.nextInt(weakPopulation.size())));
        }

        return matingPool;
    }
}
