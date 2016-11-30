package edu.mst.cwd8d.evolution.termination;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for determining if simulation has completed
 */
public interface SimulationTerminator {
    boolean terminateSimulation(boolean childEvaluation);
}
