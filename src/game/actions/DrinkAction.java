package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;
import game.ground.Water;

/**
 * Special Action for a Dinosaur to drink. Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class DrinkAction extends Action {
    private Water target;

    /**
     * Constructor for when a Dinosaur drinks from a Water object.
     *
     * @param water - Water to drink from
     */
    public DrinkAction(Water water) {
        this.target = water;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Dinosaur dinosaur = (Dinosaur) actor;
        dinosaur.increaseThirstPoints(this.target.getFill());
        return menuDescription(actor);
    }
    @Override
    public String menuDescription(Actor actor) {
        return actor + " drank water ";
    }
}
