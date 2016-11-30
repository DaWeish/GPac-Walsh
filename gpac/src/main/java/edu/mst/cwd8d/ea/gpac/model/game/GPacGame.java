package edu.mst.cwd8d.ea.gpac.model.game;

import edu.mst.cwd8d.ea.gpac.configuration.GPacConfig;
import edu.mst.cwd8d.ea.gpac.model.grid.Grid;
import edu.mst.cwd8d.ea.gpac.model.grid.GridSquare;

import java.util.*;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class represents the game GPac
 */
public class GPacGame {
    private final StringBuilder output;
    private final Random random;

    private GPacGameState gameState;

    // User configurable options
    private int timeMultiplier;
    private int fruitScore;
    private double fruitSpawnProbability;
    private double wallDensity;
    private double pillDensity;

    public class GPacGameState {
        // Game State variables
        int time;
        int totalPills;
        int collectedPills;
        int collectedFruit;
        boolean trySpawnFruit;

        // Game Pieces
        GameGrid.GamePiece pacman;
        GameGrid.GamePiece ghost1;
        GameGrid.GamePiece ghost2;
        GameGrid.GamePiece ghost3;
        GameGrid.GamePiece fruit;

        public PieceArrayGameGrid grid;

        GPacGameState(int width, int height) {
            this.grid = new PieceArrayGameGrid(width, height, 0, 0);
        }

        GPacGameState(GPacGameState other) {
            this.time = other.time;
            this.totalPills = other.totalPills;
            this.collectedPills = other.collectedPills;
            this.collectedFruit = other.collectedFruit;
            this.trySpawnFruit = other.trySpawnFruit;

            this.grid = new PieceArrayGameGrid(other.grid);

            this.pacman = grid.getPiece(other.pacman.getLocation(), other.pacman.getId());
            this.ghost1 = grid.getPiece(other.ghost1.getLocation(), other.ghost1.getId());
            this.ghost2 = grid.getPiece(other.ghost2.getLocation(), other.ghost2.getId());
            this.ghost3 = grid.getPiece(other.ghost3.getLocation(), other.ghost3.getId());
        }

        void initialize() {
            initializeGrid();

            time = getTotalTime();
            collectedPills = 0;
            collectedFruit = 0;
            trySpawnFruit = true;
        }

        /**
         * Initializes the grid by resetting the walls, placing pacman and the ghosts, generating walls
         * and placing the pills.
         */
        private void initializeGrid() {
            grid.reset();
            // Place the pacman piece
            pacman = grid.placePiece(0, grid.getHeight() - 1, PieceIds.Pacman.ordinal(), "m");
            // Place ghost pieces
            ghost1 = grid.placePiece(grid.getWidth() - 1, 0, PieceIds.Ghost1.ordinal(), "1");
            ghost2 = grid.placePiece(grid.getWidth() - 1, 0, PieceIds.Ghost2.ordinal(), "2");
            ghost3 = grid.placePiece(grid.getWidth() - 1, 0, PieceIds.Ghost3.ordinal(), "3");

            generateWalls();
            placePills();
        }

        /**
         * Returns the target square given a src square and action
         * @param square start square
         * @param action action to take
         * @return the final square
         */
        private GridSquare getTargetSquare(GridSquare square, Actions action) {
            if (action.equals(Actions.WAIT)) return grid.getSquare(square.getX(), square.getY());

            return grid.getNeighbor(square, convertAction(action));
        }

        /**
         * Applies the given pieces action to the gameState
         * @param piece which piece to move
         * @param action what action to apply for that piece
         * @return true if the game is over, false otherwise
         */
        private boolean processPieceAction(GameGrid.GamePiece piece, Actions action) {
            boolean gameOver = false;

            // Current Locations
            GridSquare currLoc = piece.getLocation();
            GridSquare targetLoc = getTargetSquare(currLoc, action);
            grid.movePiece(piece, targetLoc);

            // is the piece pacman?
            if (piece.getId() == PieceIds.Pacman.ordinal()) { // Check if pacman lands on a ghost
                // Check if pacman is on a ghost
                if (targetLoc.equals(ghost1.getLocation()) ||
                        targetLoc.equals(ghost2.getLocation()) ||
                        targetLoc.equals(ghost3.getLocation())) gameOver = true;

                // Check for fruit collection
                if (fruit != null && fruit.getLocation().equals(targetLoc)) {
                    collectFruit();
                }

                // Check for pill collection
                for (GameGrid.GamePiece pillPiece : grid.getPieces(targetLoc)) {
                    if (pillPiece.getId() == PieceIds.Pill.ordinal()) {
                        collectPill();
                    }
                }
                // The piece is a ghost
            } else {
                // check if the ghost lands on pacman
                if (targetLoc.equals(pacman.getLocation())) gameOver = true;
            }

            return gameOver;
        }

        /**
         * Attempts to pick up the piece of fruit. Does not check if pacman is on the
         * square, this must be done before calling this method.
         */
        private void collectFruit() {
            if (fruit == null) {
                System.out.println("There is no fruit to collect!");
                return;
            }

            grid.removePiece(fruit);
            fruit = null;
            ++collectedFruit;
            trySpawnFruit = true;
        }

        /**
         * Attempts to pick up the pill on the given square
         */
        private void collectPill() {
            GameGrid.GamePiece pill = grid.getPiece(pacman.getLocation(), PieceIds.Pill.ordinal());
            if (pill == null) {
                System.out.println("Failed to find pill at specified grid square");
                return;
            }

            grid.removePiece(pill);
            ++collectedPills;
        }


        /**
         * This helper function populates the grid with walls where each non-wall square is connected by some
         * path to pacmans start square and the ghost start squares.
         */
        private void generateWalls() {
            // Basic wall generation algorithm #2 from Jacob Drilling Email
            //
            // 1. Start with all squares as walls (except for pacman and the ghost start squares)
            // 2. Make a path from start to end
            // 3. Calculate the expected number of walls with some random deviation
            // 4. While there are more walls than expected, remove randomly one wall which is adjacent to a non-wall square

            grid.setAllInactive(); // Makes all squares walls

            // Set the top and bottom row to be non-walls
            int yTop = grid.getHeight() - 1, yBot = 0;
            for (int x = 0; x < grid.getWidth(); ++x) {
                GridSquare top = grid.getSquare(x, yTop);
                GridSquare bot = grid.getSquare(x, yBot);

                grid.setActive(top);
                grid.setActive(bot);
            }

            // Set the left and right columns to be non-walls
            int xLeft = 0, xRight = grid.getWidth() - 1;
            for (int y = 1; y < grid.getHeight() - 1; ++y) {
                GridSquare left = grid.getSquare(xLeft, y);
                GridSquare right = grid.getSquare(xRight, y);

                grid.setActive(left);
                grid.setActive(right);
            }

            int expectedWalls = (int)(wallDensity * (grid.getWidth() * grid.getHeight() - 2));
            expectedWalls = Math.max(1, expectedWalls);
            List<GridSquare> frontier = grid.getInactiveBorder();

            for (int i = grid.countInactive(); i > expectedWalls; --i) { // for each unexpected wall
                // Get a wall that is adjacent to the currently active walls
                GridSquare current = null;
                if (frontier.isEmpty()) break;
                Collections.shuffle(frontier, random);
                while (current == null) {
                    current = frontier.get(0);
                    if (grid.isActive(current)) { // if its not a wall
                        frontier.remove(0);
                        Collections.shuffle(frontier, random);
                        current = null;
                    }
                }

                grid.setActive(current); // make this no longer a wall

                // Add all the wall neighbors of the current square, some of these may already be in the collection
                for (GridSquare square : grid.getInactiveNeighbors(current)) {
                    frontier.add(square);
                }
            }
        }

        /**
         * Randomly places pills in open squares up to the expected number of pills
         */
        private void placePills() {
            totalPills = 0;
            int expectedPills = (int) (pillDensity * (grid.getWidth() * grid.getHeight() - 1 - grid.countInactive()));
            expectedPills = Math.max(1, expectedPills);
            GridSquare pacmanSquare = pacman.getLocation();

            List<GridSquare> pillSquares = grid.getActive();
            Collections.shuffle(pillSquares, random);
            for (int i = 0; i < expectedPills && !pillSquares.isEmpty(); ++i) {
                GridSquare pillSquare = pillSquares.get(pillSquares.size() - 1);
                if (pillSquare.equals(pacmanSquare)) {
                    pillSquares.remove(pillSquare);
                    pillSquare = pillSquares.get(pillSquares.size() - 1);
                }

                grid.placePiece(pillSquare.getX(), pillSquare.getY(), PieceIds.Pill.ordinal(), "p");
                ++totalPills;
                pillSquares.remove(pillSquare);
            }
        }

        /**
         * Calculate the stats and populate a given stats object
         * @param stats stats object to store stats in
         */
        public void calculateStats(GPacStats stats) {
            stats.pacmanNumAdjacentWalls = 0;
            for (GridSquare square : grid.getSquareNeighbors(pacman.getLocation())) {
                if (!grid.isActive(square)) ++stats.pacmanNumAdjacentWalls;
            }

            if (fruit != null) {
                stats.pacmanToFruit = grid.manhattanDistance(pacman.getLocation(), fruit.getLocation());
            } else {
                stats.pacmanToFruit = Integer.MAX_VALUE;
            }

            // distance to nearest ghost
            int shortest = grid.manhattanDistance(pacman.getLocation(), ghost1.getLocation());
            int test = grid.manhattanDistance(pacman.getLocation(), ghost2.getLocation());
            if (test < shortest) shortest = test;
            test = grid.manhattanDistance(pacman.getLocation(), ghost3.getLocation());
            if (test < shortest) shortest = test;
            stats.pacmanToNearestGhost = shortest;

            // distance to nearest pill
            stats.pacmanToNearestPill = Integer.MAX_VALUE;
            for (GameGrid.GamePiece piece : grid.getAllPieces()) {
                if (piece.getId() == PieceIds.Pill.ordinal()) {
                    int check = grid.manhattanDistance(pacman.getLocation(), piece.getLocation());
                    if (check < stats.pacmanToNearestPill) stats.pacmanToNearestPill = check;
                }
            }
        }
    }

    enum Actions { UP, DOWN, LEFT, RIGHT, WAIT }

    private enum PieceIds { Pacman, Ghost1, Ghost2, Ghost3, Pill, Fruit }

    /**
     * Interface for defining an actor which can return a list of actions that can be taken. For use
     * by the Controller interface so the controller knows what the valid actions are.
     */
    interface Actor {
        List<Actions> getActions();
        List<Pair<Actions, GPacGameState>> getActionsWithState();
    }

    /**
     * This interface allows for controllers to integrate into the game of GPac and control pacman
     * and the ghosts.
     */
    interface Controller {
        void setActor(Actor actor);
        Actions getAction();
    }

    /**
     * All argument constructor
     * @param random the random object
     * @param width number of squares wide for the grid
     * @param height number of squares tall for the grid
     * @param fruitScore the number of points per piece of fruit
     * @param timeMultiplier scaling factor for total time
     * @param pillDensity density of pill placement
     * @param wallDensity density of wall placement
     * @param fruitSpawnProbability possibility of spawning a piece of fruit
     */
    private GPacGame(Random random, int width, int height, int fruitScore, int timeMultiplier,
                    double pillDensity, double wallDensity, double fruitSpawnProbability) {
        this.random = random;
        this.output = new StringBuilder();

        this.timeMultiplier = timeMultiplier;
        this.fruitScore = fruitScore;
        this.fruitSpawnProbability = fruitSpawnProbability;
        this.wallDensity = wallDensity;
        this.pillDensity = pillDensity;

        this.gameState = new GPacGameState(width, height);
    }

    public GPacGame(Random random, GPacConfig config) {
        this(random, config.getWidth(), config.getHeight(), config.getFruitScore(), config.getTimeMultiplier(),
                config.getPillDensity(), config.getWallDensity(), config.getFruitSpawnProbability());
    }

    /**
     * This helper function converts a difference of two GridSquares into the first matching action. Therefore
     * diagonal movements will be converted into the first vertical or horizontal action which would lead to the target.
     * @param current starting square
     * @param target ending square
     * @return The action to take to move towards the target square
     */
    private Actions convertToAction(GridSquare current, GridSquare target) {
        if (current.equals(target)) return Actions.WAIT;

        if (current.getY() < target.getY()) { // Moving Up
            return Actions.UP;
        } else if (current.getY() > target.getY()) { // Moving Down
            return Actions.DOWN;
        } else if (current.getX() > target.getX()) { // Moving Left
            return Actions.LEFT;
        } else {
            return Actions.RIGHT;
        }

    }

    /**
     * Returns all valid actions for pacman
     * @return a list specifying all the valid actions for pacman
     */
    private List<Actions> pacmanActions() {
        ArrayList<Actions> actions = new ArrayList<>();
        actions.add(Actions.WAIT);

        // Get a list of all adjacent squares that are on the grid
        for (GridSquare square : gameState.grid.getSquareNeighbors(gameState.pacman.getLocation())) {
            if (gameState.grid.isActive(square)) { // Check to see if the square doesn't have a wall
                actions.add(convertToAction(gameState.pacman.getLocation(), square));
            }
        }

        return actions;
    }

    private List<Pair<Actions, GPacGameState>> pacmanActionsAndState() {
        ArrayList<Pair<Actions, GPacGameState>> result = new ArrayList<>();
        for (Actions action : pacmanActions()) {
            GPacGameState state = new GPacGameState(gameState);
            state.processPieceAction(state.pacman, action);
            result.add(new Pair<>(action, state));
        }
        return result;
    }

    /**
     * Gets all valid actions for the given ghost
     * @param ghost which ghost to get actions for
     * @return a list of all valid actions for that ghost
     */
    private List<Actions> ghostActions(GameGrid.GamePiece ghost) {
        ArrayList<Actions> actions = new ArrayList<>();

        // Get a list of all adjacent squares that are on the grid
        for (GridSquare square : gameState.grid.getSquareNeighbors(ghost.getLocation())) {
            if (gameState.grid.isActive(square)) { // Check to see if the square doesn't have a wall
                actions.add(convertToAction(ghost.getLocation(), square));
            }
        }

        return actions;
    }

    /**
     * Gets the total time according to the formula
     * @return integer specifying the total number of moves per game of GPac
     */
    private int getTotalTime() {
        return gameState.grid.getWidth() * gameState.grid.getHeight() * timeMultiplier;
    }

    /**
     * Initializes the game of GPac
     * @param p controller for pacman
     * @param g1 controller for the first ghost
     * @param g2 controller for the second ghost
     * @param g3 controller for the third ghost
     */
    private void initialize(Controller p, Controller g1, Controller g2, Controller g3) {
        gameState.initialize();

        output.delete(0, output.length());

        p.setActor(new Actor() {
            @Override
            public List<Actions> getActions() {
                return pacmanActions();
            }

            @Override
            public List<Pair<Actions, GPacGameState>> getActionsWithState() {
                return pacmanActionsAndState();
            }
        });

        g1.setActor(new Actor() {
            @Override
            public List<Actions> getActions() {
                return ghostActions(gameState.ghost1);
            }

            @Override
            public List<Pair<Actions, GPacGameState>> getActionsWithState() {
                return null;
            }
        });

        g2.setActor(new Actor() {
            @Override
            public List<Actions> getActions() {
                return ghostActions(gameState.ghost2);
            }

            @Override
            public List<Pair<Actions, GPacGameState>> getActionsWithState() {
                return null;
            }
        });

        g3.setActor(new Actor() {
            @Override
            public List<Actions> getActions() {
                return ghostActions(gameState.ghost3);
            }

            @Override
            public List<Pair<Actions, GPacGameState>> getActionsWithState() {
                return null;
            }
        });
    }

    /**
     * This function runs a new game of GPac with the given controllers and returns the total score
     * @return the total score of the game
     */
    public int play(Controller pControl, Controller g1Control, Controller g2Control, Controller g3Control) {
        initialize(pControl, g1Control, g2Control, g3Control);

        logInitialState();

        boolean gameOver = false;
        while (!gameOver) {
            // Check for fruit spawn
            if (gameState.trySpawnFruit) {
                spawnFruit();
            }

            // Pacman Turn
            Actions pacmanAction = pControl.getAction();
            gameOver = gameState.processPieceAction(gameState.pacman, pacmanAction);

            // Ghost1 Turn
            if (!gameOver) {
                Actions ghost1Actions = g1Control.getAction();
                gameOver = gameState.processPieceAction(gameState.ghost1, ghost1Actions);
            }

            // Ghost2 Turn
            if (!gameOver) {
                Actions ghost2Actions = g2Control.getAction();
                gameOver = gameState.processPieceAction(gameState.ghost2, ghost2Actions);
            }

            // Ghost3 Turn
            if (!gameOver) {
                Actions ghost3Actions = g3Control.getAction();
                gameOver = gameState.processPieceAction(gameState.ghost3, ghost3Actions);
            }

            // If all the pills are collected, its game over this turn
            if (gameState.collectedPills == gameState.totalPills) gameOver = true;

            // Adjust the time and check for game over
            --gameState.time;
            if (gameState.time == 0) gameOver = true;

            logSnapShot();
        }

        return calculateScore();
    }

    /**
     * Get the world file generated by the most recent run of GPac
     * @return a string representing the world file for GPac
     */
    public String getOutput() { return output.toString(); }

    /**
     * Uses the scoring formula to calculate pacman's current score
     * @return the current score
     */
    private int calculateScore() {
        int base = (int)((((double)gameState.collectedPills)/gameState.totalPills) * 100);
        int timeBonus = 0;
        if (gameState.collectedPills == gameState.totalPills) {
            timeBonus = (int)(((double)gameState.time)/(gameState.grid.getWidth()
                    * gameState.grid.getHeight() * timeMultiplier) * 100);
        }
        return base + gameState.collectedFruit * fruitScore + timeBonus;
    }

    /**
     * Attempts to spawn a piece of fruit using the fruitSpawnProbability at a random location
     */
    private void spawnFruit() {
        // Spawn fruit with a percent chance of success
        // if success, try spawn fruit = false;
        if (random.nextDouble() < fruitSpawnProbability) {
            // Spawn a piece of fruit
            List<GridSquare> activeSquares = gameState.grid.getActive();
            List<? extends GameGrid.GamePiece> pieces = gameState.grid.getAllPieces();
            Collections.shuffle(activeSquares, random);
            for (GridSquare potentialSquare : activeSquares) {
                boolean valid = true;
                for (GameGrid.GamePiece piece : pieces) {
                    if (piece.getLocation().equals(potentialSquare)) {
                        if (piece.getId() == PieceIds.Pacman.ordinal() || piece.getId() == PieceIds.Pill.ordinal()) {
                            valid = false;
                        }
                    }
                }

                if (valid) {
                    gameState.fruit = gameState.grid.placePiece(potentialSquare.getX(), potentialSquare.getY(), PieceIds.Fruit.ordinal(), "f");
                    logGamePiece(gameState.fruit);
                    gameState.trySpawnFruit = false;
                    break;
                }
            }
        }
    }

    /**
     * Converts Actions into Directions for grid movement
     * @param action to convert
     * @return the converted action, Null if action is Wait
     */
    private static Grid.Direction convertAction(Actions action) {
        switch (action) {
            case UP:
                return Grid.Direction.UP;
            case DOWN:
                return Grid.Direction.DOWN;
            case LEFT:
                return Grid.Direction.LEFT;
            case RIGHT:
                return Grid.Direction.RIGHT;
            default:
                return null;
        }
    }

    /**
     * Logs a game piece to the output in the world format.
     * @param piece which piece to log
     */
    private void logGamePiece(GameGrid.GamePiece piece) {
        output.append(piece.toString()).append("\n");
    }

    /**
     * Logs the end of turn info the the output buffer
     */
    private void logEndOfTurn() {
        output.append("t ").append(gameState.time).append(" ").append(calculateScore()).append("\n");
    }

    /**
     * Logs a wall to the world output buffer
     * @param wall square where the wall is
     */
    private void logWall(GridSquare wall) {
        output.append("w ").append(wall).append("\n");
    }

    /**
     * Logs the total snapshot each turn including the pieces and end of turn info
     */
    private void logSnapShot() {
        logGamePiece(gameState.pacman);
        logGamePiece(gameState.ghost1);
        logGamePiece(gameState.ghost2);
        logGamePiece(gameState.ghost3);

        logEndOfTurn();
    }

    /**
     * Logs the initial state to the output buffer
     */
    private void logInitialState() {
        output.append("").append(gameState.grid.getWidth()).append("\n");
        output.append("").append(gameState.grid.getHeight()).append("\n");

        for (GridSquare wall : gameState.grid.getInactive()) {
            logWall(wall);
        }

        for (GameGrid.GamePiece piece : gameState.grid.getAllPieces()) {
            if (piece.getId() == PieceIds.Pill.ordinal()) {
                logGamePiece(piece);
            }
        }

        logSnapShot();
    }
}
