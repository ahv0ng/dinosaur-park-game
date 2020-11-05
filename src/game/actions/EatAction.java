package game.actions;

import edu.monash.fit2099.engine.*;
import game.actors.Dinosaur;
import game.ground.Dirt;
import game.items.WildEdible;

/**
 * Special Action for a Dinosaur to eat. Extends Action
 *
 * @author Nicholas Chua and Alden Vong
 */
public class EatAction extends Action {
    private Ground groundTarget;
    private Item itemTarget;

    /**
     * Constructor for when a Dinosaur eats from a Ground object. At the moment, this only applies
     * to Dirt objects with grass.
     *
     * @param ground - Ground to eat from
     */
    public EatAction(Ground ground) {
        this.groundTarget = ground;
    }

    /**
     * Constructor for when a Dinosaur eats an Item object (lying on the ground). At the moment, this
     * only applied to corpses and eggs of other species.
     *
     * @param item - Item to eat
     */
    public EatAction(Item item) {
        this.itemTarget = item;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        Dinosaur dinosaur = (Dinosaur) actor;

        if (this.groundTarget != null) {
            Dirt dirt = (Dirt) this.groundTarget;
            dinosaur.increaseHungerPoints(dirt.getFill());
            dirt.removeGrass();
        }
        else {
            WildEdible edible = (WildEdible) this.itemTarget;
            dinosaur.increaseHungerPoints(edible.getFill());
            map.locationOf(actor).removeItem((Item) edible);
        }
        return menuDescription(dinosaur);
    }

    @Override
    public String menuDescription(Actor actor) {
        if (this.groundTarget != null) {
            return actor + " eats grass";
        }
        else {
            return actor + " eats " + this.itemTarget;
        }
    }
}
