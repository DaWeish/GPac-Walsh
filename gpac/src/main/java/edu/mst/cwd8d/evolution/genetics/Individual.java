package edu.mst.cwd8d.evolution.genetics;

import lombok.Value;

/**
 * @author Connor Walsh (cwd8d)
 */

@Value
public class Individual<T> implements Comparable<Individual<T>> {
    T genes;
    long fitness;

    public Individual(T genes, long fitness) {
        this.genes = genes;
        if (fitness < 0)
            this.fitness = 0;
        else
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
}
