package edu.mst.cwd8d.evolution.selection;

import edu.mst.cwd8d.evolution.genetics.Individual;
import edu.mst.cwd8d.evolution.genetics.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh
 */
public class TournamentParentSelector<T> implements ParentSelector<T> {
    private final Random random;
    private int bracketSize;
    private int matingPoolSize;

    public TournamentParentSelector(Random random, int matingPoolSize, int bracketSize) {
        this.random = random;
        this.bracketSize = bracketSize;
        this.matingPoolSize = matingPoolSize;
    }

    /**
     * For this selector, the population must at least have matingPoolSize members
     * @param population the population to select parents from
     * @return list of parents for mating
     */
    @Override
    public List<Individual<T>> selectParents(Population<T> population) {
        ArrayList<Individual<T>> matingPool = new ArrayList<>();
        ArrayList<Individual<T>> tournamentPool = getTournamentPool(population);

        while (matingPool.size() < matingPoolSize) {
            Collections.shuffle(tournamentPool, random);
            ArrayList<Individual<T>> bracket = new ArrayList<>();
            for (int i = 0; i < bracketSize; ++i) {
                bracket.add(tournamentPool.get(i));
            }

            Individual<T> winner = getWinner(bracket);
            matingPool.add(winner);
            tournamentPool.remove(winner);
        }

        return matingPool;
    }

    private ArrayList<Individual<T>> getTournamentPool(Population<T> population) {
        ArrayList<Individual<T>> pool = new ArrayList<>(population.size());
        for (Individual<T> individual : population) {
            pool.add(individual);
        }
        return pool;
    }

    private Individual<T> getWinner(ArrayList<Individual<T>> bracket) {
        Individual<T> bestIndividual = null;
        for (Individual<T> individual : bracket) {
            if (bestIndividual == null)
                bestIndividual = individual;
            else {
               if (individual.compareTo(bestIndividual) > 0) {
                   bestIndividual = individual;
               }
            }
        }

        return bestIndividual;
    }
}
