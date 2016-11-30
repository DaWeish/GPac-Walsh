package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;

import java.util.List;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for selecting parent genotypes to reproduce with
 *
 * @param <T> GenoType of the parents and eventual children
 *
 * This interface will need to be flushed out more since fitness values will be required
 */
public interface ParentSelector<T> {
    List<Individual<T>> selectParents(Population<T> population);
}
