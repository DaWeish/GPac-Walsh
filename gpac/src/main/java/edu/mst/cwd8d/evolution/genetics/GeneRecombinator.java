package edu.mst.cwd8d.evolution.genetics;

/**
 * @author Connor Walsh (cwd8d)
 * Provides interface for recombining two genes and creating a new gene
 *
 * @param <T> The type of genes to be recombined
 */
public interface GeneRecombinator<T> {
    T recombine(T leftParent, T rightParent);
}
