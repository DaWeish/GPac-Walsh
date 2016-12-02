package edu.mst.cwd8d.ea.geneticprogramming;

import edu.mst.cwd8d.ea.geneticprogramming.function.*;

import java.util.Random;

/**
 * @author Connor Walsh
 *
 * This class holds a GP tree for use as a GPac controller
 */
public abstract class GeneticTree {
    private int size;
    private GPacExpressionTreeNode root;

    /**
     * Create and initialize a GP tree
     * @param random random object
     * @param maxDepth maximum depth of the initialized tree
     * @param grow whether to initialize using the grow method, or the full method
     */
    public GeneticTree(Random random, int maxDepth, boolean grow) {

        if (grow) {
            this.root = grow(maxDepth, null, random);
        } else {
            this.root = full(maxDepth, null, random);
        }

        calculateSize();
    }

    public GeneticTree(GeneticTree other) {
        size = other.size;
        root = other.root.copy();
        getRecursiveCopy(root);
    }

    public GPacExpressionTreeNode grow(int maxDepth, GPacExpressionTreeNode node, Random random) {
        if (node == null) { // If node is null, create a new root node
            GPacExpressionTreeNode root = getRandomFunctionNode(null, random);
            grow(maxDepth, root, random);
            return root;
        } else {
            if (node.terminalNode()) return node; // nothing to do here
            if (maxDepth <= 1) {
                // terminal case
                node.setLeft(getRandomTerminalNode(node, random));
                node.setRight(getRandomTerminalNode(node, random));
                return node;
            } else {
                // Normal case
                node.setLeft(getRandomNode(node, random));
                grow(maxDepth - 1, node.getLeft(), random);

                node.setRight(getRandomNode(node, random));
                grow(maxDepth - 1, node.getRight(), random);
                return node;
            }
        }
    }

    /**
     * This method uses the grow method recursively create a GP tree and returns the root of the new tree
     * @param maxDepth the current depth of the tree
     * @param node the parent node to grow
     * @return the new root node of the tree
     */
    public GPacExpressionTreeNode full(int maxDepth, GPacExpressionTreeNode node, Random random) {
        if (node == null) {
            GPacExpressionTreeNode root = getRandomFunctionNode(null, random);
            full(maxDepth, root, random);
            return root;
        } else {
            if (node.terminalNode()) return node; // nothing to do here

            if (maxDepth <= 1) {
                // terminal case
                node.setLeft(getRandomTerminalNode(node, random));
                node.setRight(getRandomTerminalNode(node, random));
                return node;
            } else {
                // Normal case
                node.setLeft(getRandomFunctionNode(node, random));
                full(maxDepth - 1, node.getLeft(), random);

                node.setRight(getRandomFunctionNode(node, random));
                full(maxDepth - 1, node.getRight(), random);
                return node;
            }
        }
    }

    public abstract GPacExpressionTreeNode getRandomTerminalNode(GPacExpressionTreeNode parent, Random random);

    public GPacExpressionTreeNode getRandomFunctionNode(GPacExpressionTreeNode parent, Random random) {
        int nodeType = random.nextInt(5);
        switch(nodeType) {
            case 0: // Addition Node
                return new AdditionTreeNodeGPac(parent);
            case 1: // Division Node
                return new DivisionTreeNodeGPac(parent);
            case 2: // Multiplication Node
                return new MultiplicationTreeNodeGPac(parent);
            case 3: // Random Tree Node
                return new RandomTreeNodeGPac(parent, random);
            case 4: // Subtraction Node
                return new SubtractionTreeNodeGPac(parent);
            default:
                System.out.println("Invalid NodeType in getRandomFunctionNode!");
                return null;
        }
    }

    public GPacExpressionTreeNode getRandomNode(GPacExpressionTreeNode parent, Random random) {
        // There are currently 5 terminal nodes and 5 function nodes
        if (random.nextBoolean()) {
            return getRandomTerminalNode(parent, random);
        } else {
            return getRandomFunctionNode(parent, random);
        }
    }

    public int sizeOfExpression(GPacExpressionTreeNode root) {
        int leftSize = 0, rightSize = 0;
        if (root.getLeft() != null) {
            leftSize = sizeOfExpression(root.getLeft());
        }
        if (root.getRight() != null) {
            rightSize = sizeOfExpression(root.getRight());
        }
        return leftSize + rightSize + 1;
    }

    public GPacExpressionTreeNode getRandomNode(GPacExpressionTreeNode root, double selectionChance, Random random) {
        if (root.getRight() == null && root.getLeft() == null) return root;

        // check to select current node
        if (random.nextDouble() < selectionChance) return root;

        if (random.nextBoolean()) {
            return getRandomNode(root.getLeft(), selectionChance, random);
        } else {
            return getRandomNode(root.getRight(), selectionChance, random);
        }
    }

    /**
     * This assumes that the first node given is a unique node that points to a duplicated set of
     * sub trees to the left and right
     * @param node current node to duplicate children for
     */
    public void getRecursiveCopy(GPacExpressionTreeNode node) {
        if (node == null) return;

        if (node.getLeft() != null) {
            GPacExpressionTreeNode leftCopy = node.getLeft().copy();
            leftCopy.setParent(node);
            getRecursiveCopy(leftCopy);
        }
        if (node.getRight() != null) {
            GPacExpressionTreeNode rightCopy = node.getRight().copy();
            rightCopy.setParent(node);
            getRecursiveCopy(rightCopy);
        }
    }

    public GPacExpressionTreeNode getRoot() {
        return root;
    }

    public void setRoot(GPacExpressionTreeNode node) {
        this.root = node;
    }

    public int getSize() {
        return size;
    }

    public void calculateSize() {
        size = sizeOfExpression(root);
    }

    public abstract GeneticTree copy();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printExpression(root, sb);
        return sb.toString();
    }

    /**
     * This method recursively builds the expression from a tree
     * @param node the current node to print from
     * @param sb the output of the string builder
     */
    public static void printExpression(GPacExpressionTreeNode node, StringBuilder sb) {
        if (node.terminalNode()) {
            sb.append(node.toString());
            return;
        }
        if (node.getLeft() != null && node.getRight() != null) {
            sb.append("(");
            printExpression(node.getLeft(), sb);
            sb.append(" ");
            sb.append(node.toString());
            sb.append(" ");
            printExpression(node.getRight(), sb);
            sb.append(")");
        }
    }
}
