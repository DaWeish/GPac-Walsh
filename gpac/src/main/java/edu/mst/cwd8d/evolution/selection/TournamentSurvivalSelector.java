package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This selector uses a k-tournament without replacement style survival selection trim the population
 * to the target size.
 */
public class TournamentSurvivalSelector<T> implements SurvivalEvaluator<T> {
    private final Random random;
    private int bracketSize;

    public TournamentSurvivalSelector(Random random, int bracketSize) {
        this.random = random;
        this.bracketSize = bracketSize;
    }

    /**
     * This selector holds tournaments with brackets of size bracketSize to decide which individuals
     * continue on in the population. The loser of each bracket is removed. Individuals only have to survive
     * one bracket in order to continue living. The population must be of size (excess)*bracketSize in order
     * to have enough participants for the tournament
     * @param population the population to trim
     */
    @Override
    public void trimPopulation(Population<T> population) {
        if (population.size() == population.targetSize()) return;

        ArrayList<Individual<T>> tournamentPool = getTournamentPool(population);

        while (population.size() > population.targetSize()) {
            Collections.shuffle(tournamentPool, random);
            ArrayList<Individual<T>> bracket = new ArrayList<>();

            for (int i = 0; i < bracketSize; ++i) {
                if (tournamentPool.isEmpty()) break;
                Individual<T> individual = tournamentPool.get(tournamentPool.size() - 1);
                bracket.add(individual);
                tournamentPool.remove(tournamentPool.size() - 1);
            }

            if (bracket.isEmpty()) break;
            population.kill(getLoser(bracket));
        }

    }

    /**
     * Creates a duplicate list of the population to draw potential parents from
     * @param population the population to copy
     * @return the list of individuals in a new structure
     */
    private ArrayList<Individual<T>> getTournamentPool(Population<T> population) {
        ArrayList<Individual<T>> pool = new ArrayList<>(population.size());
        for (Individual<T> individual : population) {
            pool.add(individual);
        }
        return pool;
    }

    private Individual<T> getLoser(ArrayList<Individual<T>> bracket) {
        Collections.sort(bracket);
        return bracket.get(0);
    }
}
