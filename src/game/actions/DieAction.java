package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.items.corpses.Corpse;


/**
 * Special action for when a Dinosaur dies. Use this after an event has occurred which causes a Dinosaur to die.
 * Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class DieAction extends Action {
    private final Corpse corpse;

    /**
     * Constructor for DieAction.
     */
    public DieAction(Corpse corpse) {
        this.corpse = corpse;
    }

    @Override
    public String execute(Actor actor, GameMap map) { // actor represents the current Dinosaur that dies
        map.locationOf(actor).addItem(this.corpse);
        map.removeActor(actor);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " dies";
    }
}
