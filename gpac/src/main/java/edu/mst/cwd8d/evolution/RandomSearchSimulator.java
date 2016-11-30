package edu.mst.cwd8d.evolution;

import edu.mst.cwd8d.evolution.genetics.GeneSeeder;
import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;
import edu.mst.cwd8d.evolution.selection.FitnessEvaluator;
import edu.mst.cwd8d.evolution.termination.SimulationTerminator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Connor Walsh (cwd8d)
 * This class simply performs random Search using Genes
 */
public class RandomSearchSimulator<T> implements Simulator<T> {
    private static Logger log = LoggerFactory.getLogger("testing.log");
    private static Logger generalLog = LoggerFactory.getLogger(EvolutionSimulator.class);

    private final FitnessEvaluator<T> fitnessEvaluator;
    private final GeneSeeder<T> geneSeeder;
    private final SimulationTerminator simulationTerminator;
    private final Population<T> population;

    public RandomSearchSimulator(FitnessEvaluator<T> testFitnessEvaluator,
                              GeneSeeder<T> geneSeeder,
                              SimulationTerminator simulationTerminator,
                              Population<T> population) {
        this.fitnessEvaluator = testFitnessEvaluator;
        this.geneSeeder = geneSeeder;
        this.simulationTerminator = simulationTerminator;
        this.population = population;
    }

    public Population<T> simulate() {
        fitnessEvaluator.reset();
        initializePopulation();
        logOutput();

        Individual<T> bestSoFar = population.getFittest();

        while (!simulationTerminator.terminateSimulation(false)) {
            reproduce();
            Individual<T> currentBest = population.getFittest();
            if (currentBest.getFitness() > bestSoFar.getFitness()) {
                bestSoFar = currentBest;
                logOutput();
            }

            population.killWeakest();
        }

        return population;
    }

    private void logOutput() {
        log.info("" + fitnessEvaluator.numberEvaluations()
                + "\t" + population.getFittest().getFitness());
    }

    private void reproduce() {
        T child = geneSeeder.getGene();
        long fitness = fitnessEvaluator.evaluateFitness(child);
        population.add(new Individual<T>(child, fitness));
    }

    private void initializePopulation() {
        generalLog.info("Initializing Population");
        population.killAll();

        T gene = geneSeeder.getGene();
        long fitness = fitnessEvaluator.evaluateFitness(gene);

        generalLog.info("Initial Fitness value of " + fitness);
        population.add(new Individual<>(gene, fitness));
    }
}
