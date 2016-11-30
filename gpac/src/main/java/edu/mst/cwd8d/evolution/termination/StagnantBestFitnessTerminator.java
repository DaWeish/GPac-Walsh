package edu.mst.cwd8d.evolution.termination;

import edu.mst.cwd8d.evolution.genetics.Population;

/**
 * @author Connor Walsh (cwd8d)
 */
public class StagnantBestFitnessTerminator<T> implements SimulationTerminator {
    protected final Population<T> population;
    private final int stagnation;
    private long best = Long.MIN_VALUE;
    private int count = 0;

    public StagnantBestFitnessTerminator(Population<T> population, int stagnation) {
        this.population = population;
        this.stagnation = stagnation;
    }

    @Override
    public boolean terminateSimulation(boolean childEvaluation) {
        if (childEvaluation)
            return false;

        if (population.size() == 0)
            return false;

        if (best != population.getFittest().getFitness()) {
            best = population.getFittest().getFitness();
            count = 1;
        } else {
            ++count;
            if (count > stagnation) {
                return true;
            }
        }

        return false;
    }

    protected void restart() {
        best = Long.MIN_VALUE;
        count = 0;
    }
}
