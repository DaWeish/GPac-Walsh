package edu.mst.cwd8d.evolution;

import edu.mst.cwd8d.evolution.genetics.Population;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for running a simulation
 */
public interface Simulator<T> {
    Population<T> simulate();
}
