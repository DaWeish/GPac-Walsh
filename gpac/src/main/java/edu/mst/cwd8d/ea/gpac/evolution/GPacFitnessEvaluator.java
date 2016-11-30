package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.ea.gpac.configuration.GPacConfig;
import edu.mst.cwd8d.ea.gpac.model.game.GPTreeController;
import edu.mst.cwd8d.ea.gpac.model.game.GPacGame;
import edu.mst.cwd8d.ea.gpac.model.game.RandomController;
import edu.mst.cwd8d.evolution.selection.FitnessEvaluator;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class uses the Genetic Programming Tree to implement the Fitness Evaluator Interface
 */
public class GPacFitnessEvaluator implements FitnessEvaluator<GeneticTree> {
    private GPacGame game;
    private GPTreeController pacmanController;
    private RandomController ghost1Controller;
    private RandomController ghost2Controller;
    private RandomController ghost3Controller;
    private int evaluations;
    private long bestFitness;
    public String bestWorld;
    private double parsimonyPressure;


    public GPacFitnessEvaluator(Random random, GPacConfig config, double parsimonyPressure) {
        game = new GPacGame(random, config);
        bestFitness = Long.MIN_VALUE;
        pacmanController = new GPTreeController();
        ghost1Controller = new RandomController(random);
        ghost2Controller = new RandomController(random);
        ghost3Controller = new RandomController(random);
        this.parsimonyPressure = parsimonyPressure;
        evaluations = 0;
    }

    @Override
    public long evaluateFitness(GeneticTree gene) {
        return evaluateFitness(gene, true);
    }

    @Override
    public long evaluateFitness(GeneticTree gene, boolean increment) {
        if (increment) ++evaluations;
        pacmanController.setTree(gene);
        long fitness = game.play(pacmanController, ghost1Controller, ghost2Controller, ghost3Controller);
        fitness -= Math.ceil(gene.getSize() * parsimonyPressure);
        if (fitness > bestFitness) {
            bestFitness = fitness;
            bestWorld = game.getOutput();
        }
        return fitness;
    }

    @Override
    public int numberEvaluations() {
        return evaluations;
    }

    @Override
    public void reset() {
        evaluations = 0;
    }
}
