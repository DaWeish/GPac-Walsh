package edu.mst.cwd8d.evolution.genetics;

/**
 * @author Connor Walsh (cwd8d)
 * Provides interface for dealing with a population of Genotypes
 *
 * @param <T> GenoType of individuals in the population
 */
public interface Population<T> extends Iterable<Individual<T>> {
    /**
     * Determine if a set of Genes exists in the population
     * @param individual Genes of individual in question
     * @return true if exists in population, false otherwise
     */
    boolean contains(Individual<T> individual);

    /**
     * Get the most fit population member
     * @return Genes of individual, null on zero population size
     */
    Individual<T> getFittest();

    /**
     * Add the individual to the genepool with a Long.Min_Value as fitness
     * @param individual Genes to add to population
     */
    boolean add(Individual<T> individual);

    /**
     * Remove the genes from the population
     * @param individual genes to remove from population
     */
    boolean kill(Individual<T> individual);

    /**
     * Removes the weakest individual
     * @return the individual removed
     */
    Individual<T> killWeakest();

    /**
     * Removes all individuals from the population
     */
    void killAll();

    /**
     * Gets the acutal population size
     * @return size of the population
     */
    int size();

    /**
     * Sets the target population size
     * @param size target size for the population
     */
    void setTargetSize(int size);

    /**
     * Gets the target population size
     * @return the target size for the population
     */
    int targetSize();

    /**
     * Sets the birth rate for a population
     * @param rate number of children per generation (lambda)
     */
    void setBirthRate(int rate);

    /**
     * Returns the birth rate of the population
     * @return number of children per generation (lambda)
     */
    int birthRate();

    /**
     * Is the population of size 0?
     * @return true if size is 0, false otherwise
     */
    boolean isEmpty();

    /**
     * Gets the total fitness value of the population's individuals
     * @return total fitness value for the entire population
     */
    long totalFitness();
}
