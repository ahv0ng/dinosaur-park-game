package game.actors;

import edu.monash.fit2099.engine.*;
import game.behaviours.WanderBehaviour;
import game.portables.AllosaurEgg;

/**
 * Extends Dinosaur.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Allosaur extends Carnivore {
    static final int BITE_DAMAGE = 20;

    /**
     * Constructor for Allosaur.
     */
    public Allosaur() { super("Allosaur", 'a'); }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Allosaur is unconscious until fed/dies
        if (this.getHungerLevel() == MIN_HUNGER) {
            return this.unconsciousBehaviour(map);
        }
        // Allosaur looks for a mate
        else if (!(this.isHungry())) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Allosaur looks for something to eat/attack
        else if (this.isHungry()) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            this.eat(location);
            if (this.lookForFoodBehaviour(map, location) != null) {
                return this.lookForFoodBehaviour(map, location);
            }
            }
        // Allosaur wanders around or does nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
    }

    @Override
    public AllosaurEgg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new AllosaurEgg();
    }

    /**
     * Creates and returns an intrinsic weapon for allosaurs (biting with teeth).
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(BITE_DAMAGE, "bites");
    }
}
