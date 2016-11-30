package edu.mst.cwd8d.evolution.genetics;

/**
 * @author Connor Walsh (cwd8d)
 * Inteface for getting a mutated version of a gene
 *
 * @param <T> GenoType to mutate
 */
public interface GeneMutator<T> {
    T mutate(T gene);
}
