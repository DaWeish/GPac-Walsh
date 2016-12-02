package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.ea.gpac.model.game.Pair;

/**
 * @author Connor Walsh (cwd8d)
 * Interface for evaluating the fitness of GenoTypes
 *
 * @param <T> GenoType for determining the fitness of
 */
public interface CompetitiveFitnessEvaluator<T> {
    /**
     * Return the fitness level of the genes given
     * @param attackerGene gene of the attacker
     * @param defenderGene gene of the defender
     * @return the fitness level of the genes
     */
    Pair<Long, Long> evaluateFitness(T attackerGene, T defenderGene);

    /**
     * Return the fitness level of the genes given
     * @param attackerGene gene of the attacker
     * @param defenderGene gene of the defender
     * @param increment whether or not to increment the eval counter
     * @return the fitness level of the genes
     */
    Pair<Long, Long> evaluateFitness(T attackerGene, T defenderGene, boolean increment);


    /**
     * Get the number of fitness evaluations done
     * @return the number of evaluations
     */
    int numberEvaluations();

    /**
     * Resets the number of fitness evaluations so the simulation can be run again
     */
    void reset();
}
