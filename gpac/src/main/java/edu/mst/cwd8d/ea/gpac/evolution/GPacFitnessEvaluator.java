package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;
import edu.mst.cwd8d.ea.gpac.configuration.GPacConfig;
import edu.mst.cwd8d.ea.gpac.model.game.GPTreeController;
import edu.mst.cwd8d.ea.gpac.model.game.GPacGame;
import edu.mst.cwd8d.ea.gpac.model.game.Pair;
import edu.mst.cwd8d.evolution.selection.CompetitiveFitnessEvaluator;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class uses the Genetic Programming Tree to implement the Fitness Evaluator Interface
 */
public class GPacFitnessEvaluator implements CompetitiveFitnessEvaluator<GeneticTree> {
    private GPacGame game;
    private GPTreeController pacmanController;
    private GPTreeController ghost1Controller;
    private GPTreeController ghost2Controller;
    private GPTreeController ghost3Controller;
    private int evaluations;
    private long bestFitness;
    public String bestWorld;
    private double parsimonyPressure;


    public GPacFitnessEvaluator(Random random, GPacConfig config, double parsimonyPressure) {
        game = new GPacGame(random, config);
        bestFitness = Long.MIN_VALUE;
        pacmanController = new GPTreeController(0);
        ghost1Controller = new GPTreeController(1);
        ghost2Controller = new GPTreeController(2);
        ghost3Controller = new GPTreeController(3);
        this.parsimonyPressure = parsimonyPressure;
        evaluations = 0;
    }

    @Override
    public Pair<Long, Long> evaluateFitness(GeneticTree attackerGene, GeneticTree defenderGene) {
        return evaluateFitness(attackerGene, defenderGene, true);
    }

    @Override
    public Pair<Long, Long> evaluateFitness(GeneticTree attackerGene, GeneticTree defenderGene, boolean increment) {
        if (increment) ++evaluations;
        pacmanController.setTree(attackerGene);
        ghost1Controller.setTree(defenderGene);
        ghost2Controller.setTree(defenderGene);
        ghost3Controller.setTree(defenderGene);
        long pacmanFitness = game.play(pacmanController, ghost1Controller, ghost2Controller, ghost3Controller);
        long ghostFitness = -pacmanFitness;
        pacmanFitness -= Math.ceil(attackerGene.getSize() * parsimonyPressure);
        ghostFitness -= Math.ceil(defenderGene.getSize() * parsimonyPressure);
        if (pacmanFitness > bestFitness) {
            bestFitness = pacmanFitness;
            bestWorld = game.getOutput();
        }
        return new Pair<>(pacmanFitness, ghostFitness);
    }

    @Override
    public int numberEvaluations() {
        return evaluations;
    }

    @Override
    public void reset() {
        evaluations = 0;
        bestWorld = null;
        bestFitness = Long.MIN_VALUE;
    }
}
