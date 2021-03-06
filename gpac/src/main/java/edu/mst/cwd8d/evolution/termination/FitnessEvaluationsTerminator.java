package edu.mst.cwd8d.evolution.termination;

import edu.mst.cwd8d.evolution.selection.CompetitiveFitnessEvaluator;
import edu.mst.cwd8d.evolution.selection.FitnessEvaluator;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class should terminate the simulation after a given number of fitness evaluations
 */
public class FitnessEvaluationsTerminator<T> implements SimulationTerminator {
    private final CompetitiveFitnessEvaluator<T> fitnessEvaluator;
    private int maxEvaluations;

    public FitnessEvaluationsTerminator(CompetitiveFitnessEvaluator<T> fitnessEvaluator, int maxEvaluations) {
        this.fitnessEvaluator = fitnessEvaluator;
        this.maxEvaluations = maxEvaluations;
    }

    @Override
    public boolean terminateSimulation(boolean childEvaluation) {
        return (fitnessEvaluator.numberEvaluations() >= maxEvaluations);
    }
}
