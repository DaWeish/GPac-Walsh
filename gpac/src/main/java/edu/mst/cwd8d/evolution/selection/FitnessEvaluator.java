package edu.mst.cwd8d.evolution.selection;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for evaluating the fitness of GenoTypes
 *
 * @param <T> GenoType for determining the fitness of
 */
public interface FitnessEvaluator<T> {
    /**
     * Return the fitness level of the genes given
     * @param gene to test fitness for
     * @return the fitness level of the genes
     */
    long evaluateFitness(T gene);

    /**
     * Return the fitness level of the genes given
     * @param gene to test fitness for
     * @param increment whether or not to increment evaluation counter
     * @return the fitness level of the genes
     */
    long evaluateFitness(T gene, boolean increment);

    /**
     * Get the number of fitness evaluations done
     * @return the number of evaluations
     */
    int numberEvaluations();

    /**
     * Resets the number of fitness evaluations so the simulation can be run again
     */
    void reset();
}
