package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * Special action for when a Dinosaur dies. Use this after an event has occurred which causes a Dinosaur to die.
 * Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
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
