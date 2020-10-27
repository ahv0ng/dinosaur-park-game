package game.actors;

import edu.monash.fit2099.engine.*;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.Fruit;
import game.portables.Hay;
import game.portables.VegetarianMealKit;

public abstract class Herbivore extends Dinosaur {
    static final int HUNGER_POINTS_FOR_GRAZE_GRASS = 5;

    public Herbivore(String sex, String name, Character displayChar) {
        // Only to be used for the start of the game, @see Dinosaur constructor
        super(sex, name, displayChar);
    }
    public Herbivore(String name, Character displayChar) {
        super(name, displayChar);
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Herbivore is unconscious until fed/dies
        if (this.getHungerLevel() == MIN_HUNGRY_THIRSTY) {
            return this.unconsciousBehaviour(map);
        }

        this.resetDaysUnconscious();
        this.decreaseHunger(1);

        // Herbivore looks for a mate
        if (!(this.isHungry())) {
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Herbivore looks for grass to eat
        else if (this.isHungry()) {
            this.graze(location); // TODO: Change this to a general eat method?
            if (this.lookForFoodBehaviour(map, location) != null) {
                return this.lookForFoodBehaviour(map, location);
            }
        }

        // Herbivore wanders around or does nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
    }

    /**
     * Evaluate whether Herbivore can eat given Item. Herbivores can eat Fruit, Hay, and
     * VegetarianMealKit.
     * @param item - Item object for Herbivore to eat
     * @return boolean value on whether the Herbivore can eat the item
     */
    protected boolean canEat(Item item) {
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

    /**
     * If on top of Dirt with grass attribute, remove grass for Herbivore to eat.
     *
     * @param location - Location type of the current location of the Herbivore
     */
    void graze(Location location) {
        if (location.getGround() instanceof Dirt) {
            Dirt dirt = (Dirt) location.getGround();
            if (dirt.hasGrass()) {
                this.increaseHunger(HUNGER_POINTS_FOR_GRAZE_GRASS);
                System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate grass.");
            }
        }
    }

    /**
     * Look for Food Behaviour specific to Herbivores. Herbivores will look for grass.
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Herbivore
     */
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        if (ScanSurrounds.getGrass(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getGrass(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        return null;
    }
}
