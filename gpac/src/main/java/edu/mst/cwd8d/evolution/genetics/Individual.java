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
        long compare = (fitness - tIndividual.fitness);
        if (compare < 0)
            return -1;
        else
            return 1;
    }

    public T getGenes() { return genes; }

    public long getFitness() { return fitness; }

    public void setFitness(long fitness) { this.fitness = fitness; }
}
