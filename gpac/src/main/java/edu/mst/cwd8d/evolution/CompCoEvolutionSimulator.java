package edu.mst.cwd8d.evolution;

import edu.mst.cwd8d.evolution.genetics.*;
import edu.mst.cwd8d.evolution.selection.FitnessEvaluator;
import edu.mst.cwd8d.evolution.selection.ParentSelector;
import edu.mst.cwd8d.evolution.selection.SurvivalEvaluator;
import edu.mst.cwd8d.evolution.termination.SimulationTerminator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class performs competitive co-evolution using separate populations
 * and evaluators
 */
public class CompCoEvolutionSimulator<T> implements Simulator<T> {
/**
 * @author Connor Walsh (cwd8d)
 * This class simulates the evolutionary cycle on a population for a given fitness
 *
 * The cycle consists of multiple phases:
 *      1. Initialization:      Creates starter population
 *      2. Initial Evaluation:  Evaluates Starter Population
 *      3. Parent Selection:    Selects Parents for reproduction
 *      4. Recombination:       Creates children from recombining parents
 *      5. Mutation:            Mutates children for exploration of search space
 *      6. Eval Offspring:      Determines fitness of newly created children
 *      7. Survival Check:      Trims the population based on fitness
 *      8. Termination Check:   Stops if finished, or repeats steps 3-8
 */
    private static Logger outputLog = LoggerFactory.getLogger("output.log");
    private static Logger generalLog = LoggerFactory.getLogger(EvolutionSimulator.class);

    public final FitnessEvaluator<T> trainingFitnessEvaluator;
    private final GeneSeeder<T> geneSeeder;
    private final ParentSelector<T> parentSelector;
    private final GeneRecombinator<T> geneRecombinator;
    private final GeneMutator<T> geneMutator;
    private final SurvivalEvaluator<T> survivalEvaluator;
    private final SimulationTerminator simulationTerminator;
    private final Population<T> attackerPopulation;
    private final Population<T> defenderPopulation;

    private final Random random;

    public CompCoEvolutionSimulator(FitnessEvaluator<T> trainingFitnessEvaluator,
                              GeneSeeder<T> geneSeeder,
                              ParentSelector<T> parentSelector,
                              GeneRecombinator<T> geneRecombinator,
                              GeneMutator<T> geneMutator,
                              SurvivalEvaluator<T> survivalEvaluator,
                              SimulationTerminator simulationTerminator,
                              Population<T> attackerPopulation,
                              Population<T> defenderPopulation,
                              Random random) {
        this.trainingFitnessEvaluator = trainingFitnessEvaluator;
        this.geneSeeder = geneSeeder;
        this.parentSelector = parentSelector;
        this.geneRecombinator = geneRecombinator;
        this.geneMutator = geneMutator;
        this.survivalEvaluator = survivalEvaluator;
        this.simulationTerminator = simulationTerminator;
        this.attackerPopulation = attackerPopulation;
        this.defenderPopulation = defenderPopulation;
        this.random = random;
    }

    /**
     * Returns the attacker population
     * @return The final attacker population
     */
    public Population<T> simulate() {
        trainingFitnessEvaluator.reset();
        initializePopulation();
        logOutput();

        while (!simulationTerminator.terminateSimulation(false)) {
            reproduce(attackerPopulation);
            reproduce(defenderPopulation);
            evaluateFitness(attackerPopulation);
            evaluateFitness(defenderPopulation);
            survivalEvaluator.trimPopulation(attackerPopulation);
            survivalEvaluator.trimPopulation(defenderPopulation);
            logOutput();
        }

        return attackerPopulation;
    }

    public Population<T> getDefenderPopulation() { return defenderPopulation; }

    private void logOutput() {
        long averageFitness = attackerPopulation.totalFitness() / attackerPopulation.size();
        outputLog.info(""
                + trainingFitnessEvaluator.numberEvaluations()
                + "\t" + averageFitness
                + "\t" + attackerPopulation.getFittest().getFitness());
    }

    private void reproduce(Population<T> population) {

        List<Individual<T>> parents = parentSelector.selectParents(population);
        if (parents.size() < 2) {
            // This currently does not allow cloning
            generalLog.error("Failed to reproduce, less than 2 parents selected!");
        }

        List<T> childGenes = new ArrayList<>();
        while (childGenes.size() < population.birthRate()) {
            // Select both the parents for this child
            int firstParentIndex = random.nextInt(parents.size());
            Individual<T> parent1 = parents.get(firstParentIndex);
            int secondParentIndex = firstParentIndex;
            while (secondParentIndex == firstParentIndex) {
                secondParentIndex = random.nextInt(parents.size());
            }
            Individual<T> parent2 = parents.get(secondParentIndex);

            T child = geneRecombinator.recombine(parent1.getGenes(), parent2.getGenes());
            child = geneMutator.mutate(child);
            childGenes.add(child);
        }

        // Add each child to population with the minimum fitness
        for (T child : childGenes) {
            population.add(new Individual<>(child, Long.MIN_VALUE));
        }
    }

    private void initializePopulation() {
        generalLog.info("Initializing Population");
        population.killAll();
        List<T> genes = geneSeeder.getGene(population.targetSize());
        for (T gene : genes) {
            long fitness = trainingFitnessEvaluator.evaluateFitness(gene);
            generalLog.info("Initial Fitness value of " + fitness);
            population.add(new Individual<>(gene, fitness));
        }
    }
}
