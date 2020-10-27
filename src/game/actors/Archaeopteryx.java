package game.actors;

import edu.monash.fit2099.engine.*;
import game.behaviours.WanderBehaviour;
import game.portables.ArchaeopteryxEgg;
import game.portables.Egg;
import game.portables.StegosaurEgg;

public class Archaeopteryx extends Carnivore {
    public Archaeopteryx() {
        super("Archaeopteryx", 'x');
        changeFly();
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Archaeopteryx is unconscious until fed/dies
        if (this.getHungerLevel() == MIN_HUNGRY_THIRSTY) {
            return this.unconsciousBehaviour(map);
        }

        this.resetDaysUnconscious();
        this.decreaseHunger(1);

        // Archaeopteryx looks for a mate
        if (!(this.isHungry())) {
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Archaeopteryx looks for something to eat/attack
        else if (this.isHungry()) {
            this.eat(location); // TODO: Change this to a general eat method?
            if (this.lookForFoodBehaviour(map, location) != null) {
                return this.lookForFoodBehaviour(map, location);
            }
        }
        // Archaeopteryx wanders around or does nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
    }

    @Override
    public Egg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new ArchaeopteryxEgg();
    }
}
