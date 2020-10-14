package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.actors.Dinosaur;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.*;

import java.util.ArrayList;

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
        this.incrementAge();
        Location location = map.locationOf(this);
        if (this.isHungry()) {
            System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.isPregnant()) {
            this.decrementDaysUntilLay();
            if (this.getDaysUntilLay() == 0) {
                map.locationOf(this).addItem(this.layEgg());
                System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") laid an egg!");
            }
        }
        // Stegosaur is either conscious/unconscious from hunger level
        if (this.getHungerLevel() == 0) { return new DoNothingAction(); }
        // Stegosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.increaseHunger(-1);
            Dinosaur potentialMate = getMate(map, location);
            if (potentialMate != null) {
                FollowBehaviour follow = new FollowBehaviour(potentialMate);
                // If the stegosaur is female, not pregnant, and a male is close by, mate
                if (this.isFemale() && !(this.isPregnant()) &&
                        adjacentMate(map, potentialMate)) {
                    this.mate();
                    System.out.println("Dinosaurs at (" + location.x() + ", " + location.y() + ") and (" +
                            map.locationOf(potentialMate).x() + "," +
                                    map.locationOf(potentialMate).y() + ") mated!");
                }
                if (follow.getAction(this, map) != null) {
                    return follow.getAction(this, map);
                }
            }
        }
        // Stegosaur looks for grass
       else if (this.getHungerLevel() > 0 && this.getHungerLevel() <= 50) {
           this.increaseHunger(-1);
            graze(location);
            if (getGrass(map, location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getGrass(map, location));
                if (follow.getFollowLocationAction(this, map) != null) {
                    return follow.getFollowLocationAction(this, map);
                }
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
            if (item instanceof Fruit || item instanceof Hay || item instanceof VegetarianMealKit) {
                actions.add(new FeedAction((Food) item, this));
            }
        }
        return actions;
    }
    public void graze(Location location) {
        if (location.getGround() instanceof Dirt && ((Dirt) location.getGround()).hasGrass()) {
            ((Dirt) location.getGround()).removeGrass();
            this.increaseHunger(HUNGER_POINTS_FOR_GRAZE_GRASS);
            System.out.println("Stegosaur at (" + location.x() + "," + location.y() + ")" + " ate grass");
            System.out.println(this.getHungerLevel());
        }
    }
    /**
     * Returns a reference to a potential mate within 3 tiles of the current actor (if they exist).
     * A potential mate is defined as one that is of opposite sex, of the same species, not already pregnant, and
     * is an adult.
     * @param location
     * @return Actor
     */
    @Override
    protected Dinosaur getMate(GameMap map, Location location) {
        if (!(this.isAdult())) {
            return null;
        }
        if (this.isFemale() && this.isPregnant()) {
            return null;
        }
        for (Location loc : ScanSurrounds.getLocationsWithin3(map, location)) {
            if ((map.getActorAt(loc) instanceof Stegosaur) && this.isOppositeSex((Stegosaur) map.getActorAt(loc))
                    && !((Stegosaur) map.getActorAt(loc)).isPregnant()) {
                return (Stegosaur) map.getActorAt(loc);
            }
        }
        // No potential mate in sight
        return null;
    }
    @Override
    protected Egg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new StegosaurEgg();
    }
}
