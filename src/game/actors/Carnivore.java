package game.actors;

import edu.monash.fit2099.engine.*;
import game.Corpse;
import game.actions.AttackAction;
import game.behaviours.FollowBehaviour;
import game.portables.CarnivoreMealKit;
import game.portables.Egg;
import game.scanning.Scan;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Carnivore extends Dinosaur {
    static final int BITE_DAMAGE = 20;
    static final int HUNGER_POINTS_FROM_CORPSE = 50;
    static final int HUNGER_POINTS_FROM_STEGOSAURUS_EGG = 10;

    /**
     * Constructor for Carnivore.
     *
     * @param name - String type for Carnivore name
     * @param displayChar - Char type for displaying Carnivore on the map
     */
    public Carnivore(boolean flight, String name, Character displayChar) {
        super(flight, name, displayChar);
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
     * Eat at the Location. Carnivores can eat Corpses and StegosaurEggs at their
     * current location.
     *
     * @param location - Location type of the current location of Carnivore
     */
    @Override
    protected void eatAtLocation(Location location) {
        Corpse corpse = Scan.getCorpse(location);
        Egg egg = Scan.getEgg(location);

        if (corpse != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_CORPSE);
            location.removeItem(corpse);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }
        else if (egg != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_STEGOSAURUS_EGG);
            location.removeItem(egg);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate an egg.");
        }
    }

    /**
     * Evaluate behaviour of Carnivore when looking for food. Carnivore will
     * search for Corpses or StegosaurEgg. If none nearby, it will search for
     * a Stegosaur to attack.
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
            action = behaviour.getFollowLocationAction(this, map);
        }

        Location stegosaurEggLocation = Scan.getLocationOfStegosaurEgg(location);
        if (stegosaurEggLocation != null) {
            // Follow the stegosaurEgg
            behaviour = new FollowBehaviour(stegosaurEggLocation);
            action = behaviour.getFollowLocationAction(this, map);
        }

        Dinosaur dinosaur = Scan.getOtherSpeciesDinosaur(location);
        if (dinosaur != null) {
            // Follow the Dinosaur
            behaviour = new FollowBehaviour(dinosaur);
            action = behaviour.getAction(this, map);

            if (action != null) {
                action = behaviour.getAction(this, map);
            }
            else {
                // Else if there is a Dinosaur nearby and you cannot go closer, it must be adjacent
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
