package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;

/**
 * @author Connor Walsh
 *
 * This class uses a GP Tree to implement a controller for GPac
 */
public class GPTreeController implements GPacGame.Controller {
    private GPacGame.Actor actor;
    private GeneticTree tree;
    private GPacStats stats;
    private int ghostIndex;


    public GPTreeController(int ghostIndex) {
        stats = new GPacStats();
        this.ghostIndex = ghostIndex;
    }

    public void setTree(GeneticTree tree) { this.tree = tree; }

    @Override
    public void setActor(GPacGame.Actor actor) {
        this.actor = actor;
    }

    @Override
    public GPacGame.Actions getAction() {
        double bestScore = 0;
        GPacGame.Actions bestAction = null;

        for (Pair<GPacGame.Actions, GPacGame.GPacGameState> pair : actor.getActionsWithState()) {
            // For each action and games state, get the game stats
            pair.getRight().calculateStats(stats, ghostIndex);
            double score = tree.getRoot().evaluate(stats);
            if (bestAction == null) {
                bestScore = score;
                bestAction = pair.getLeft();
            } else if (score > bestScore) {
                bestScore = score;
                bestAction = pair.getLeft();
            }
        }
        return bestAction;
    }
}
