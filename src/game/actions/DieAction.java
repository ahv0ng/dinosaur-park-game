package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * An Action for when a Dinosaur dies.
 *
 * Use this after an event has occurred which causes a Dinosaur to die.
 */
public class DieAction extends Action {
    /**
     * Constructor for DieAction.
     */
    public DieAction() {
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " dies";
    }
}
