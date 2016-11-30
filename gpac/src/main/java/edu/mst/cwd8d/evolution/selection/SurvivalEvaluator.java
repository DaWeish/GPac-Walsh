package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Population;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for obtaining the surviving population after selection pressures
 *
 * @param <T> Genotype of population individuals
 */
public interface SurvivalEvaluator<T> {
    void trimPopulation(Population<T> population);
}
