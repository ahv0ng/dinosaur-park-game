package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.*;

/**
 * A herbivorous dinosaur.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Stegosaur extends Herbivore {

    /**
     * Constructor for Stegosaur when game start.
     *
     * @param sex - String of the Dinosaur's sex
     */
    public Stegosaur(String sex) { super(sex, "Stegosaur", 's'); }

    /**
     * Constructor for Stegosaur when a StegosaurEgg hatches.
     */
    public Stegosaur() { super("Stegosaur", 's'); }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Stegosaur is unconscious until fed/dies
        if (this.getHungerLevel() == MIN_HUNGER) {
            return this.unconsciousBehaviour(map);
        }
        // Stegosaur looks for a mate
        else if (!(this.isHungry())) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Stegosaur looks for grass to eat
        else if (this.isHungry()) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            this.graze(location);
            if (this.lookForFoodBehaviour(map, location) != null) {
                return this.lookForFoodBehaviour(map, location);
            }
        }
        // Stegosaur wanders around or does nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
    }
    /**
     * Lay a StegosaurEgg.
     *
     * @return Egg to be laid on the Stegosaur's current location
     */
    @Override
    public StegosaurEgg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new StegosaurEgg();
    }
}
