package edu.mst.cwd8d.ea.gpac.configuration;

import org.apache.commons.configuration2.Configuration;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class represents the configuration for running an experiment
 *
 * Each method simply returns the requested value and the overridden toString method provides output for use in
 * a log file
 */
public class ExperimentConfig extends AbstractConfig {

    public ExperimentConfig() {}

    public ExperimentConfig(Configuration config) {
        super(config);
    }

    public boolean useTimerSeed() {
        return getBoolean("useTimerSeed", true);
    }

    public long getRandomSeed() {
        return getLong("randomSeed", 10);
    }

    public int getNumberRuns() {
        return getInt("numberRuns", 3);
    }

    public int getNumberEvaluations() {
        return getInt("numberEvaluations", 2000);
    }

    public int getPopulationSize() {
        return getInt("populationSize", 30);
    }

    public int getBirthRate() {
        return getInt("birthRate", 4);
    }

    public int getMatingPoolSize() {
        return getInt("matingPoolSize", 6);
    }

    public int getSurvivalSelectionBracketSize() {
        return getInt("survivalSelectionBracketSize", 5);
    }

    public int getConvergenceTermination() {
        return getInt("convergenceTermination", 10);
    }

    public int getMaxTreeDepth() {
        return getInt("maxTreeDepth", 10);
    }

    public double getParsimonyPressure() {
        return getDouble("parsimonyPressure", 0.01);
    }

    public double getMutationRate() {
        return getDouble("mutationRate", 0.02);
    }

    public String getLogFile() {
        return getString("logFile", "defaultLogFile");
    }

    public String getWorldFile() {
        return getString("worldFile", "defaultWorldFile");
    }

    public String getSolutionFile() {
        return getString("solutionFile", "defaultSolutionFile");
    }

    public String getSurvivalSelection() {
        return getString("survivalSelection", "truncation");
    }

    public String getParentSelection() {
        return getString("parentSelection", "fitnessProportional");
    }

    public String getTerminationStrategy() {
        return getString("terminationStrategy", "numberOfEvals");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Experiment Configuration Properties: \n");
        sb.append("# Using timer seed: ").append(useTimerSeed());
        sb.append("\n# Random seed of ").append(getRandomSeed());
        sb.append("\n# Number of runs is ").append(getNumberRuns());
        sb.append("\n# Number of evaluations is ").append(getNumberEvaluations());
        sb.append("\n# Population size is ").append(getPopulationSize());
        sb.append("\n# Birth rate is ").append(getBirthRate());
        sb.append("\n# Mating pool size is ").append(getMatingPoolSize());
        sb.append("\n# Survival selection bracket size is ").append(getSurvivalSelectionBracketSize());
        sb.append("\n# Max tree depth is ").append(getMaxTreeDepth());
        sb.append("\n# Parsimony pressure is ").append(getParsimonyPressure());
        sb.append("\n# Convergence termination is ").append(getConvergenceTermination());
        sb.append("\n# Mutation rate is ").append(getMutationRate());
        sb.append("\n# Using log file ").append(getLogFile());
        sb.append("\n# Using world file ").append(getWorldFile());
        sb.append("\n# Using solution file ").append(getSolutionFile());
        sb.append("\n# Using survival selection ").append(getSurvivalSelection());
        sb.append("\n# Using parent selection ").append(getParentSelection());
        sb.append("\n# Using termination strategy ").append(getTerminationStrategy());
        return sb.toString();
    }
}
