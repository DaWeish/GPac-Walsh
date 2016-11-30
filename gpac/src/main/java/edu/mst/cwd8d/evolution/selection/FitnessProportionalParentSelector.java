package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This is currently a best parent selector, needs to use probability
 *
 */
public class FitnessProportionalParentSelector<T> implements ParentSelector<T> {
    private int numParents;
    private Random random;

    public FitnessProportionalParentSelector(Random random, int numParents) {
        this.numParents = numParents;
        this.random = random;
    }

    /**
     * Requires that fitness values range from [0 ,Long.MAX_VALUE]
     * TODO Need to use a better random utility for getting random numbers
     * @param population potential parents
     * @return List of parents for the mating pool
     */
    @Override
    public List<Individual<T>> selectParents(Population<T> population) {
        ArrayList<Individual<T>> parents = new ArrayList<>();

        if (population.isEmpty())
            return parents;

        long totalFitness = population.totalFitness();
        while (parents.size() < numParents) {
            long selection = totalFitness;
            while (selection >= totalFitness) {
                selection = random.nextLong();
            }

            long counter = 0;
            for (Individual<T> individual : population) {
                counter += individual.getFitness();
                if (selection < counter) {
                    parents.add(individual);
                    break;
                }
            }
        }

        return parents;
    }
}
