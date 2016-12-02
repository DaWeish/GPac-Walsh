package edu.mst.cwd8d.ea.gpac;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.ea.gpac.configuration.ExperimentConfig;
import edu.mst.cwd8d.ea.gpac.configuration.ExperimentConfigProvider;
import edu.mst.cwd8d.ea.gpac.configuration.GPacConfig;
import edu.mst.cwd8d.ea.gpac.configuration.GPacConfigProvider;
import edu.mst.cwd8d.ea.gpac.evolution.GPacFitnessEvaluator;
import edu.mst.cwd8d.ea.gpac.evolution.GPacSimulationFactory;
import edu.mst.cwd8d.evolution.CompCoEvolutionSimulator;
import edu.mst.cwd8d.evolution.EvolutionSimulator;
import edu.mst.cwd8d.evolution.genetics.Individual;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * This class is in charge of getting the configurations, setting up log files, and running the experiments
 * It can optionally accept command line arguments to specify a non-default configuration
 */
public class GPac
{
    private Logger output = LoggerFactory.getLogger("output.log");
    private Logger world = LoggerFactory.getLogger("world.log");
    private Logger solution = LoggerFactory.getLogger("solution.log");
    private Logger log = LoggerFactory.getLogger(GPac.class);

    /**
     * This main function runs the experiments based on the given configurations
     * @param experimentConfig configuration properties for the experiment
     * @param gameConfig configuration properties for the GPac game
     */
    public void run(ExperimentConfig experimentConfig, GPacConfig gameConfig) {
        long seed = System.currentTimeMillis();
        if (!experimentConfig.useTimerSeed()) {
            seed = experimentConfig.getRandomSeed();
        }
        Random random = new Random(seed);

        CompCoEvolutionSimulator<GeneticTree> simulator = GPacSimulationFactory.getSimulation(random, experimentConfig, gameConfig);

        configureOutputLog("output.log", experimentConfig.getLogFile());
        output.info("Result Log");
        output.info(experimentConfig.toString());
        output.info("# Chose timer seed to be " + seed);
        output.info("");
        output.info(gameConfig.toString());

        configureOutputLog("world.log", experimentConfig.getWorldFile());
        configureOutputLog("solution.log", experimentConfig.getSolutionFile());

        log.info("Beginning Experiment");

        Individual<GeneticTree> bestIndividual = null;

        int runs = experimentConfig.getNumberRuns();
        for (int i = 1; i <= runs; ++i) {
            output.info("Run " + i);

            Individual<GeneticTree> individual = simulator.simulate().getFittest();
            log.info("Best individual score for run was " + individual.getFitness());

            if (bestIndividual == null || individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
            output.info("");
        }

        GPacFitnessEvaluator evaluator = (GPacFitnessEvaluator) simulator.trainingFitnessEvaluator;
        solution.info(bestIndividual.getGenes().toString());
        world.info(evaluator.bestWorld);
    }

    public static void main(String[] args) {
        GPacConfig gameConfig;
        ExperimentConfig experimentConfig;

        if (args.length == 1) {
            gameConfig = GPacConfigProvider.getConfiguration(args[0]);
            experimentConfig = ExperimentConfigProvider.getConfiguration(args[0]);
        } else {
            gameConfig = GPacConfigProvider.getConfiguration();
            experimentConfig = ExperimentConfigProvider.getConfiguration();
        }

        new GPac().run(experimentConfig, gameConfig);
    }

    /**
     * Helper function for setting up log4j to output log files for use as output files
     * @param logger name of logger to configure
     * @param logFile name of output file to write log to
     */
    private static void configureOutputLog(String logger, String logFile) {
        org.apache.log4j.Logger outputLogConfig = org.apache.log4j.Logger.getLogger(logger);
        FileAppender fileAppender = new FileAppender();
        fileAppender.setFile(logFile);
        PatternLayout layout = new PatternLayout();
        String pattern = "%m%n";
        layout.setConversionPattern(pattern);
        fileAppender.setLayout(layout);
        fileAppender.setAppend(false);
        fileAppender.activateOptions();
        outputLogConfig.addAppender(fileAppender);
    }
}
