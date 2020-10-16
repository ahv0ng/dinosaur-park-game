package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.ScanSurrounds;
import game.portables.AllosaurEgg;
import game.portables.CarnivoreMealKit;
import game.portables.Egg;
import game.portables.Food;

import java.util.ArrayList;

public class Allosaur extends Dinosaur {
    static final int HUNGER_POINTS_FROM_CORPSE = 50;
    static final int HUNGER_POINTS_FROM_STEGOSAURUS_EGG = 10;

    public Allosaur() { super("Allosaur", 'a'); }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        this.incrementAge();
        Location location = map.locationOf(this);
        if (this.isHungry()) {
            System.out.println("Allosaur at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.isPregnant()) {
            this.decrementDaysUntilLay();
            if (this.getDaysUntilLay() == 0) {
                map.locationOf(this).addItem(this.layEgg());
                System.out.println("Allosaur at (" + location.x() + ", " + location.y() + ") laid an egg!");
            }
        }
        // Allosaur is unconscious until fed/dies
        if (this.getHungerLevel() == 0) {
            this.incrementDaysUnconscious();
            if (this.getDaysUnconscious() == 20) {
                this.die(map);
            }
            return new DoNothingAction();
        }
        // Allosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            Action breed = new BreedingBehaviour().getAction(this, map);
            if (breed != null) {
                return breed;
            }
        }
        // Allosaur looks for something to eat/attack
        else if (this.getHungerLevel() > 0 && this.getHungerLevel() <= 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            this.eat(location);
            // If there is a corpse nearby, go towards it
            if (getGroundOfCorpse(location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getGroundOfCorpse(location));
                if (follow.getFollowLocationAction(this, map) != null) {
                    return follow.getFollowLocationAction(this, map);
                }
            }
            // If there is a stegosaur egg nearby, go towards it
            else if (getGroundOfStegosaurEgg(location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getGroundOfStegosaurEgg(location));
                if (follow.getFollowLocationAction(this, map) != null) {
                    return follow.getFollowLocationAction(this, map);
                }
            }
            // If there is a stegosaur nearby, go towards it
            else if (getStegosaur(location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getStegosaur(location));
                if (follow.getAction(this, map) != null) {
                    return follow.getAction(this, map);
                }
                // If there is a stegosaur nearby and you cannot go closer, it must be adjacent
                return new AttackAction(this.getStegosaur(location));
                }
            }
        // Allosaur wanders around or does nothing
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

    /**
     *
     * @param location
     * @return reference to Stegosaur within 3 tiles of given location (if it exists)
     */
    private Stegosaur getStegosaur(Location location) {
        ArrayList<Location> locationArrayList = ScanSurrounds.getLocationsWithin3(location);
        for (Location loc : locationArrayList) {
            if (loc.getActor() != null && loc.getActor() instanceof Stegosaur) {
                return (Stegosaur) loc.getActor();
            }
        }
        return null;
    }
    private Stegosaur adjacentStegosaur(Location location) {
        ArrayList<Location> locationArrayList = ScanSurrounds.getLocationsWithin1(location);
        for (Location loc : locationArrayList) {
            if (loc.getActor() != null && loc.getActor() instanceof Stegosaur) {
                return (Stegosaur) loc.getActor();
            }
        }
        return null;
    }

    private boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }

    /**
     * Allows Allosaur to eat a Corpse or StegosaurEgg.
     * @param location
     */
    private void eat(Location location) {
        if (this.getCorpse(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_CORPSE);
            location.removeItem(this.getCorpse(location));
            System.out.println("Allosaur at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }
        else if (this.getStegosaurEgg(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_STEGOSAURUS_EGG);
            location.removeItem(this.getStegosaurEgg(location));
            System.out.println("Allosaur at (" + location.x() + "," + location.y() + ")" + " ate an egg.");
        }
    }
    public AttackAction attack(Stegosaur stegosaur) {
        return new AttackAction(stegosaur);
    }
    @Override
    public Egg layEgg() {
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
        return new IntrinsicWeapon(20, "bites");
    }
}
