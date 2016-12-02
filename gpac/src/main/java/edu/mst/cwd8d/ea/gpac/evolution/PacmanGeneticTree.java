package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;

import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class represents a controller tree for the GPac Ghosts
 */
public class PacmanGeneticTree extends GeneticTree {
    public PacmanGeneticTree(Random random, int maxDepth, boolean grow) {
        super(random, maxDepth, grow);
    }

    public PacmanGeneticTree(PacmanGeneticTree other) {
        super(other);
    }

    @Override
    public GeneticTree copy() {
        return new PacmanGeneticTree(this);
    }

    @Override
    public GPacExpressionTreeNode getRandomTerminalNode(GPacExpressionTreeNode parent, Random random) {
        int nodeType = random.nextInt(5);
        switch(nodeType) {
            case 0: // Constant node
                double constant = random.nextDouble() * 10.0;
                return new ConstantNodeGPac(parent, constant);
            case 1: // Pacman Num Adjacent Walls
                return new PacmanNumAdjacentWalls(parent);
            case 2: // Pacman To Fruit
                return new PacmanToFruit(parent);
            case 3: // Pacman to Nearest Ghost
                return new PacmanToNearestGhost(parent);
            case 4: // Pacman to Nearest Pill
                return new PacmanToNearestPill(parent);
            default:
                System.out.println("Invalid NodeType in getRandomTerminalNode!");
                return null;
        }
    }
}
