package edu.mst.cwd8d.ea.gpac.evolution;

import edu.mst.cwd8d.ea.geneticprogramming.GPacExpressionTreeNode;
import edu.mst.cwd8d.ea.geneticprogramming.GeneticTree;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class creates genetic tree's using pacman's terminal nodes
 */
public class PacmanGeneticTree extends GeneticTree {

    public PacmanGeneticTree(Random random, int maxDepth, boolean grow) {
        super(random, maxDepth, grow);
    }

    @Override
    public GPacExpressionTreeNode getRandomTerminalNode(GPacExpressionTreeNode parent, Random random) {
        int nodeType = random.nextInt(3);
        switch(nodeType) {
            case 0: // Constant node
                double constant = random.nextDouble() * 100.0;
                return new ConstantNodeGPac(parent, constant);
            case 1: // Ghost to Pacman
                return new GhostToPacman(parent);
            case 2: // Ghost to Ghost
                return new GhostToGhost(parent);
            default:
                System.out.println("Invalid NodeType in getRandomTerminalNode!");
                return null;
        }
    }
}
