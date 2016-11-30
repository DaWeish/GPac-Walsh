package edu.mst.cwd8d.evolution.termination;

import edu.mst.cwd8d.evolution.genetics.GeneSeeder;
import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;
import edu.mst.cwd8d.evolution.selection.FitnessEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class does not actually terminate the simulation. It simply restarts the population when stagnation
 * has occured. It should be used in combination with another termination specifier to correctly stop
 * the algorithm.
 */
public class ElitistRestartTerminator<T> extends StagnantBestFitnessTerminator<T> {
    private static Logger log = LoggerFactory.getLogger(ElitistRestartTerminator.class);
    private final int survivors;
    private final GeneSeeder<T> seeder;
    private final FitnessEvaluator<T> fitnessEvaluator;

    public ElitistRestartTerminator(Population<T> population, int stagnation, int survivors,
                                    GeneSeeder<T> seeder, FitnessEvaluator<T> fitnessEvaluator) {
        super(population, stagnation);
        this.survivors = survivors;
        this.seeder = seeder;
        this.fitnessEvaluator = fitnessEvaluator;
    }

    @Override
    public boolean terminateSimulation(boolean childEvaluation) {
        if (super.terminateSimulation(childEvaluation)) {
            log.info("Stagnation, restarting Population saving the " + survivors + " best individual(s).");
            // Population has stagnated
            while(population.size() > survivors) {
                population.killWeakest();
            }

            while(population.size() < population.targetSize()) {
                T gene = seeder.getGene();
                long fitness = fitnessEvaluator.evaluateFitness(gene);
                log.info("Adding new individual with fitness " + fitness);
                population.add(new Individual<>(gene, fitness));
            }
            restart(); // Resets the stagnation count
        }

        return false;
    }
}
