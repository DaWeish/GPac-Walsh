package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This selector should remove the lowest fitness individuals until meets population size
 */
public class TruncationSurvivalSelector<T> implements SurvivalEvaluator<T> {
    @Override
    public void trimPopulation(Population<T> population) {
        while (population.size() > population.targetSize()) {
            Individual<T> weakest = population.killWeakest();
        }
    }
}
