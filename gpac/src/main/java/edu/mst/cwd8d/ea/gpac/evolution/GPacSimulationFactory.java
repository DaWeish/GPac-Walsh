package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.ea.gpac.configuration.ExperimentConfig;
import edu.mst.cwd8d.ea.gpac.configuration.GPacConfig;
import edu.mst.cwd8d.evolution.EvolutionSimulator;
import edu.mst.cwd8d.evolution.genetics.GeneticPopulation;
import edu.mst.cwd8d.evolution.selection.*;
import edu.mst.cwd8d.evolution.termination.FitnessEvaluationsTerminator;
import edu.mst.cwd8d.evolution.termination.SimulationTerminator;
import edu.mst.cwd8d.evolution.termination.StagnantBestFitnessTerminator;

import java.util.Random;

/**
 * @author Connor Walsh
 */
public class GPacSimulationFactory {
    public static EvolutionSimulator<GeneticTree> getSimulation(Random random, ExperimentConfig config, GPacConfig gameConfig) {
        GPacFitnessEvaluator fitnessEvaluator = new GPacFitnessEvaluator(random, gameConfig, config.getParsimonyPressure());

        RampedHalfAndHalfSeeder seeder = new RampedHalfAndHalfSeeder(random, config.getMaxTreeDepth());

        GeneticPopulation<GeneticTree> population = new GeneticPopulation<>(
                config.getPopulationSize(),
                config.getBirthRate());

        ParentSelector<GeneticTree> parentSelector;
        switch (config.getParentSelection()) {
            case "fitnessProportional":
                parentSelector = new FitnessProportionalParentSelector<>(random, config.getMatingPoolSize());
                break;
            case "overSelection":
                parentSelector = new OverSelection(random, config.getMatingPoolSize());
                break;
            default:
                System.out.println("Invalid Parent Selection Scheme!");
                return null;
        }

        SubTreeCrossover crossover = new SubTreeCrossover(random);

        SubTreeMutator mutator = new SubTreeMutator(random, config.getMutationRate());

        SurvivalEvaluator<GeneticTree> survivalEvaluator;
        switch (config.getSurvivalSelection()) {
            case "truncation":
                survivalEvaluator = new TruncationSurvivalSelector<>();
                break;
            case "kTournamentWithoutReplacement":
                survivalEvaluator = new TournamentSurvivalSelector<>(random, config.getSurvivalSelectionBracketSize());
                break;
            default:
                System.out.println("Invalid Survival Selection Scheme!");
                return null;
        }

        SimulationTerminator terminator;
        switch (config.getTerminationStrategy()) {
            case "numberOfEvals":
                terminator = new FitnessEvaluationsTerminator<GeneticTree>(
                        fitnessEvaluator,
                        config.getNumberEvaluations());
                break;
            case "stagnation":
                terminator = new StagnantBestFitnessTerminator<>(population, config.getConvergenceTermination());
                break;
            default:
                System.out.println("Invalid Termination Strategy!");
                return null;
        }

        return new EvolutionSimulator<>(
                fitnessEvaluator,
                seeder,
                parentSelector,
                crossover,
                mutator,
                survivalEvaluator,
                terminator,
                population,
                random);
    }
}
