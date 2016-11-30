package edu.mst.cwd8d.ea.gpac.model.game;

import java.util.List;
import java.util.Random;

/**
 * @author Connor Walsh (cwd8d)
 *
 * This class immplements a controller which randomly chooses one of the availble actions for the
 * actor
 */
public class RandomController implements GPacGame.Controller {
    private final Random random;
    private GPacGame.Actor actor;

    public RandomController(Random random) {
        this.random = random;
    }

    @Override
    public void setActor(GPacGame.Actor actor) {
        this.actor = actor;
    }

    @Override
    public GPacGame.Actions getAction() {
        List<GPacGame.Actions> actions = actor.getActions();
        actor.getActionsWithState();
        return actions.get(random.nextInt(actions.size()));
    }
}
