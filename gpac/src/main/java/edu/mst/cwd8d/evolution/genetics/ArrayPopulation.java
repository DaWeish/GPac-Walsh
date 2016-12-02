package edu.mst.cwd8d.evolution.genetics;

import java.util.*;

/**
 * @author Connor Walsh
 *
 * This class implements the Population interface using an ArrayList as the underlying data structure
 * It makes no effor to keep the individuals in sorted order since their fitness values can be changed
 * externally
 */
public class ArrayPopulation<T> implements  Population<T> {
    private final ArrayList<Individual<T>> population;
    private int targetSize;
    private int birthRate;


    public ArrayPopulation(int targetSize, int birthRate) {
        this.targetSize = targetSize;
        this.birthRate = birthRate;
        population = new ArrayList<>();
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
    public List<Individual<T>> getSortedPopulationList() {
        ArrayList<Individual<T>> duplicate = new ArrayList<>(population);
        Collections.sort(duplicate);
        return duplicate;
    }

    @Override
    public Individual<T> getRandom(Random random) {
        return population.get(random.nextInt(population.size()));
    }

    @Override
    public Individual<T> getFittest() {
        Individual<T> fittest = null;

        for (Individual<T> individual : population) {
            if (fittest == null) {
                fittest = individual;
                continue;
            }

            if (individual.compareTo(fittest) > 0) {
                fittest = individual;
            }
        }
        return fittest;
    }

    @Override
    public void add(Individual<T> individual) {
        population.add(individual);
    }

    @Override
    public boolean kill(Individual<T> individual) {
        return population.remove(individual);
    }

    @Override
    public Individual<T> killWeakest() {
        Collections.sort(population);
        return population.remove(0);
    }

    @Override
    public void killAll() {
        population.clear();
    }

    @Override
    public boolean isEmpty() {
        return population.isEmpty();
    }

    @Override
    public long totalFitness() {
        long totalFitness = 0;
        for (Individual<T> individual : population) {
            totalFitness += individual.getFitness();
        }
        return totalFitness;
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
        return population.listIterator();
    }
}
