package edu.mst.cwd8d.evolution.genetics;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Connor Walsh (cwd8d)
 * Abstract base class for a population
 * This class has a messy implementation and should be cleaned up
 */
public class GeneticPopulation<T> implements Population<T> {
    private final TreeSet<Individual<T>> population;
    private int targetSize;
    private int birthRate;
    private long totalFitness = 0;

    public GeneticPopulation(int targetSize, int birthRate) {
        this.targetSize = targetSize;
        this.birthRate = birthRate;
        population = new TreeSet<>();
    }

    @Override
    public void setBirthRate(int rate) {
        birthRate = rate;
    }

    @Override
    public int birthRate() {
        return birthRate;
    }

    @Override
    public boolean contains(Individual<T> individual) {
        return population.contains(individual);
    }

    @Override
    public Individual<T> getFittest() {
        return population.last();
    }

    @Override
    public boolean add(Individual<T> individual) {
        totalFitness += individual.getFitness();
        return population.add(individual);
    }

    @Override
    public boolean kill(Individual<T> individual) {
        totalFitness -= individual.getFitness();
        return population.remove(individual);
    }

    @Override
    public void killAll() {
        totalFitness = 0;
        population.clear();
    }

    @Override
    public boolean isEmpty() {
        return population.isEmpty();
    }

    @Override
    public long totalFitness() {
        return totalFitness;
    }

    @Override
    public Individual<T> killWeakest() {
        Individual<T> weakest = population.pollFirst();
        totalFitness -= weakest.getFitness();
        return weakest;
    }

    @Override
    public int size() {
        return population.size();
    }

    @Override
    public void setTargetSize(int size) {
        targetSize = size;
    }

    @Override
    public int targetSize() {
        return targetSize;
    }

    @Override
    public Iterator<Individual<T>> iterator() {
        return population.descendingIterator();
    }
}
