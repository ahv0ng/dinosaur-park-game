package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.ScanSurrounds;
import game.portables.AllosaurEgg;
import game.portables.CarnivoreMealKit;
import game.portables.Egg;
import game.portables.Food;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public class Allosaur extends Dinosaur {
    static final int HUNGER_POINTS_FROM_CORPSE = 50;
    static final int HUNGER_POINTS_FROM_STEGOSAURUS_EGG = 10;

    /**
     * Constructor for Allosaur.
     */
    public Allosaur() { super("Allosaur", 'a'); }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        // Allosaur is unconscious until fed/dies
        if (this.getHungerLevel() == 0) {
            return this.unconsciousBehaviour(map);
        }
        // Allosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        // Allosaur looks for something to eat/attack
        else if (this.getHungerLevel() > 0 && this.getHungerLevel() <= 50) {
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
     * Evaluate whether Allosaur can eat given Item.
     * @param item - Item object for Allosaur to eat
     * @return boolean value on whether the Allosaur can eat the item
     */
    private boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }

    /**
     * Allows Allosaur to eat a Corpse or StegosaurEgg.
     * @param location - Location type of the current location of Allosaur
     */
    private void eat(Location location) {
        if (ScanSurrounds.getCorpse(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_CORPSE);
            location.removeItem(ScanSurrounds.getCorpse(location));
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }
        else if (ScanSurrounds.getStegosaurEgg(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_STEGOSAURUS_EGG);
            location.removeItem(ScanSurrounds.getStegosaurEgg(location));
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate an egg.");
        }
    }

    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        // If there is a corpse nearby, go towards it
        if (ScanSurrounds.getGroundOfCorpse(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getGroundOfCorpse(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        // If there is a stegosaur egg nearby, go towards it
        else if (ScanSurrounds.getGroundOfStegosaurEgg(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getGroundOfStegosaurEgg(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        // If there is a stegosaur nearby, go towards it
        else if (ScanSurrounds.getStegosaur(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getStegosaur(location));
            if (follow.getAction(this, map) != null) {
                return follow.getAction(this, map);
            }
            // If there is a stegosaur nearby and you cannot go closer, it must be adjacent
            return new AttackAction(ScanSurrounds.getStegosaur(location));
        }
        return null;
    }

    /**
     * Attack adjacent Stegosaur.
     * @param stegosaur - Stegosaur target
     * @return AttackAction object to interact with Stegosaur
     */
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
