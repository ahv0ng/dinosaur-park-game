package game.actors;

import edu.monash.fit2099.engine.*;
import game.actors.Dinosaur;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;

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
        if (this.getHungerLevel() == 0) {
            return new DoNothingAction();
        } else if (this.getHungerLevel() > 0) {
            this.increaseHunger(-1);
        }
        if (this.isHungry()) {
            Location location = map.locationOf(this);
            System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.getHungerLevel() > 50) {
            Location location = map.locationOf(this);
            if (getMate(map, location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getMate(map, location));
                if (follow.getAction(this, map) != null) {
                    return follow.getAction(this, map);
                }
            }
        }
        if (this.getHungerLevel() > 0 && this.getHungerLevel() <= 50) {
            Location location = map.locationOf(this);
            graze(location);
            if (getGrass(map, location) != null) {
                FollowBehaviour follow = new FollowBehaviour(getGrass(map, location));
                if (follow.getFollowLocationAction(this, map) != null) {
                    return follow.getFollowLocationAction(this, map);
                }
            }
        }
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();

    }

    public void graze(Location location) {
        if (location.getGround() instanceof Dirt && ((Dirt) location.getGround()).hasGrass()) {
            ((Dirt) location.getGround()).removeGrass();
            this.increaseHunger(HUNGER_POINTS_FOR_GRAZE_GRASS);
            System.out.println("Stegosaur at (" + location.x() + "," + location.y() + ")" + " ate grass");
            System.out.println(this.getHungerLevel());
        }
    }
}

/*    @Override
    public void mate(Stegosaur stegosaur) {
        if (this.canMate(stegosaur)) {
            // TODO: stop wandering, then somehow get the female to lay egg
        }
    }

    @Override
    private Egg layEgg() {
        return new StegosaurEgg();
    }
}*/
