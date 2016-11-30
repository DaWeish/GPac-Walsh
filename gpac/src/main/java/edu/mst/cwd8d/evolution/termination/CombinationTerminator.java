package edu.mst.cwd8d.evolution.termination;

import java.util.ArrayList;

/**
 * @author Connor Walsh (cwd8d)
 */
public class CombinationTerminator implements SimulationTerminator {
    private final ArrayList<SimulationTerminator> terminators = new ArrayList<>();

    public void addTerminator(SimulationTerminator terminator) {
        terminators.add(terminator);
    }

    @Override
    public boolean terminateSimulation(boolean childEvaluation) {
        for (SimulationTerminator terminator : terminators) {
            if (terminator.terminateSimulation(childEvaluation)) {
                return true;
            }
        }

        return false;
    }
}
