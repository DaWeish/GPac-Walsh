package edu.mst.cwd8d.evolution.genetics;

import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for obtaining a starting population
 *
 * @param <T> GenoType of population
 */
public interface GeneSeeder<T> {
    /**
     * Gets a single gene from the seed
     * @return Gene from the seed
     */
    T getGene();

    /**
     * Gets a list of genes from seeder
     * @param size number of genes to obtain
     * @return a List of genes from the seed
     */
    List<T> getGene(int size);
}
