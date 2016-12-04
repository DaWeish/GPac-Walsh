package edu.mst.cwd8d.evolution.genetics;

/**
 * @author Connor Walsh (cwd8d)
 */

public class Individual<T> implements Comparable<Individual<T>> {
    private T genes;
    private long fitness;

    public Individual(T genes, long fitness) {
        this.genes = genes;
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual<T> tIndividual) {
        if (fitness < tIndividual.fitness)
            return -1;
        else if (fitness > tIndividual.fitness)
            return 1;
        else
            return 0;
    }

    public T getGenes() { return genes; }

    public long getFitness() { return fitness; }

    public void setFitness(long fitness) { this.fitness = fitness; }
}
