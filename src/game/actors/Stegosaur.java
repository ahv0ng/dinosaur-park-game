package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.*;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
    static final int HUNGER_POINTS_FOR_GRAZE_GRASS = 5;

    public Stegosaur(String sex) {
        super(sex, "Stegosaur", 's');
    }

    public Stegosaur() {
        super("Stegosaur", 's');
    }
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Stegosaur is unconscious until fed/dies
        if (this.getHungerLevel() == 0) {
            return this.unconsciousBehaviour(map);
        }
        // Stegosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Stegosaur looks for grass to eat
        else if (this.getHungerLevel() > 0 && this.getHungerLevel() <= 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            graze(location);
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
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        for (Item item : otherActor.getInventory()) {
            if (this.canEat(item)) {
                actions.add(new FeedAction((Food) item, this));
            }
        }
        return actions;
    }
    private boolean canEat(Item item) {
        if (item instanceof Fruit) {
            return true;
        }
        else if (item instanceof Hay) {
            return true;
        }
        else if (item instanceof VegetarianMealKit) {
            return true;
        }
        return false;
    }
    private void graze(Location location) {
        if (location.getGround() instanceof Dirt && ((Dirt) location.getGround()).hasGrass()) {
            ((Dirt) location.getGround()).removeGrass();
            this.increaseHunger(HUNGER_POINTS_FOR_GRAZE_GRASS);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate grass.");
        }
    }
    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        if (ScanSurrounds.getGrass(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getGrass(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        return null;
    }
    @Override
    public Egg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new StegosaurEgg();
    }
}
