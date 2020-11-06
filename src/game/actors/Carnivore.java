package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.EatAction;
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
     * Evaluate behaviour of Carnivore when looking for of eating food. Carnivore will
     * search for Corpses or Egg. If none nearby, it will search for
     * a Dinosaur to attack.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Carnivore
     */
    @Override
    protected Action lookForFoodOrEatBehaviour(GameMap map, Location location) {
        Action action = null;
        FollowBehaviour behaviour;

        Corpse corpse = Scan.getCorpse(location);
        if (corpse != null) {
            // Eat the corpse
            return new EatAction(corpse);
        }

        Egg egg = Scan.getEgg(location);
        if (egg != null) {
            // Eat the egg
            return new EatAction(egg);
        }

        Location corpseLocation = Scan.getLocationOfCorpse(location);
        if (corpseLocation != null) {
            // Follow the corpse
            behaviour = new FollowBehaviour(corpseLocation);
            return behaviour.getAction(this, map);
        }

        Location eggLocation = Scan.getLocationOfEgg(location);
        if (eggLocation != null) {
            // Follow the egg
            behaviour = new FollowBehaviour(eggLocation);
            return behaviour.getAction(this, map);
        }

        Dinosaur dinosaur = Scan.getOtherSpeciesDinosaur(location);
        if (dinosaur != null) {
            // Attack the Dinosaur
            if (Scan.isAdjacent(map.locationOf(this), map.locationOf(dinosaur))) {
                return new AttackAction(dinosaur);
            }
            // If not adjacent, must be nearby, so follow the Dinosaur
            else {
                behaviour = new FollowBehaviour(dinosaur);
                return behaviour.getAction(this, map);
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
