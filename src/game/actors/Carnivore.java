package game.actors;

import edu.monash.fit2099.engine.*;
import game.items.corpses.Corpse;
import game.actions.AttackAction;
import game.behaviours.FollowBehaviour;
import game.items.foods.CarnivoreMealKit;
import game.items.eggs.Egg;
import game.scanning.Scan;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Carnivore extends Dinosaur {
    static final int BITE_DAMAGE = 20;

    /**
     * Constructor for Carnivore.
     *
     * @param name - String type for Carnivore name
     * @param displayChar - Char type for displaying Carnivore on the map
     */
    public Carnivore(String name, Character displayChar) {
        super(name, displayChar);
    }

    /**
     * Evaluate whether Carnivore can eat given Item. Carnivores are allowed to
     * eat CarnivoreMealKit.
     *
     * @param item - Item object for Carnivore to eat
     * @return boolean value on whether the Carnivore can eat the item
     */
    @Override
    protected boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }

    /**
     * Eat at the Location. Carnivores can eat Corpses or Egg at their
     * current location.
     *
     * @param location - Location type of the current location of Carnivore
     */
    @Override
    protected void eatAtLocation(Location location) {
        Corpse corpse = Scan.getCorpse(location);
        if (corpse != null) {
            this.increaseHunger(corpse.getFill());
            location.removeItem(corpse);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }

        Egg egg = Scan.getEgg(location);
        if (egg != null) {
            this.increaseHunger(egg.getFill());
            location.removeItem(egg);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate an egg.");
        }
    }

    /**
     * Evaluate behaviour of Carnivore when looking for food. Carnivore will
     * search for Corpses or Egg. If none nearby, it will search for
     * a Dinosaur to attack.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Carnivore
     */
    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        FollowBehaviour behaviour;
        Action action = null;

        Location corpseLocation = Scan.getLocationOfCorpse(location);
        if (corpseLocation != null) {
            // Follow the corpse
            behaviour = new FollowBehaviour(corpseLocation);
            action = behaviour.getAction(this, map);
        }

        Location eggLocation = Scan.getLocationOfEgg(location);
        if (eggLocation != null) {
            // Follow the egg
            behaviour = new FollowBehaviour(eggLocation);
            action = behaviour.getAction(this, map);
        }

        Dinosaur dinosaur = Scan.getOtherSpeciesDinosaur(location);
        if (dinosaur != null) {
            // Follow the Dinosaur
            behaviour = new FollowBehaviour(dinosaur);
            action = behaviour.getAction(this, map);

            // TODO: Fix this implementation, as it means that an attack will not be made if they are diagonal
            if (action != null) {
                // If no action to move closer, then dinosaur must be adjacent already
                action = new AttackAction(dinosaur);
            }
        }
        // If there is no potential food nearby, it will return null for no Action
        return action;
    }

    /**
     * Return an intrinsic weapon (biting with teeth) for Carnivores.
     *
     * @return IntrinsicWeapon to attack
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(BITE_DAMAGE, "bites");
    }
}
