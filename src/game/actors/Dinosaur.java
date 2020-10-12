package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;

import java.util.ArrayList;
import java.util.Random;

public abstract class Dinosaur extends Actor {
    private int age;
    private int hungerLevel;
    private int daysUnconscious;
    private int daysUntilDeath;
    private String sex;
    private Behaviour behaviour;
    private Random random = new Random();

    static final int MATING_AGE = 30;
    static final int MIN_HUNGER = 0;
    static final int MAX_HUNGER = 100;

    /**
     * Constructor for when game starts, so that there are two opposite sex adult dinosaurs at
     * the start.
     * @param sex: String type that represent the dinosaur's sex
     */
    public Dinosaur(String sex, String name, Character displayChar) {
        super(name, displayChar, 100);
        this.age = 30;
        this.hungerLevel = 50;
        this.daysUnconscious = 0;
        this.sex = sex;
        this.behaviour = new WanderBehaviour();
    }

    //**
    // Constructor for when egg hatches
    //*
    public Dinosaur(String name, Character displayChar) {
        super(name, displayChar, 100);
        this.age = 0;
        this.hungerLevel = 10;
        this.daysUnconscious = 0;
        this.behaviour = new WanderBehaviour();

        // Randomise sex for this dinosaur
        String[] sexTypes = {"Male", "Female"};
        this.sex = sexTypes[random.nextInt(2)];
    }

    public String getSex() { return this.sex; }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hungerLevel > 0) {
            this.hungerLevel -= 1;
        }
        if (this.isHungry()) {
            Location location = map.locationOf(this);
            System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.hungerLevel == 0) {
            return new DoNothingAction();
        }
        // Still to implement
        if (this.hungerLevel > 50) {
            Location location = map.locationOf(this);
            if (getMate(map, location) != null) {
                Behaviour follow = new FollowBehaviour(getMate(map, location));
                if (follow.getAction(this, map) != null) {
                    return follow.getAction(this, map);
                }
                return new DoNothingAction();
            }
        }
        Action wander = behaviour.getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();

    }
    private boolean isHungry() { return this.hungerLevel <= 50; }

    // TODO: Caller needs to be able to handle this exception
    public void increaseHunger(int hunger) throws Exception {
        if (this.hungerLevel >= MAX_HUNGER) {
            throw new Exception("Hunger level is already maximum.");
        }

        int totalHunger = this.hungerLevel + hunger;
        if (totalHunger >= MAX_HUNGER) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            this.hungerLevel = MAX_HUNGER;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerLevel = totalHunger;
        }
    }

    private void resetDeathDays() { this.daysUntilDeath = 0; }

/*    public abstract void mate();
    public abstract Egg layEgg();

    private void canMate(Dinosaur dinosaur) {
        if (this.sex == dinosaur.getSex()) {
            return false;
        }
        if (this.age < MATING_AGE || dinosaur.age < MATING_AGE) {
            return false;
        }
        return true;
    }*/

    /**
     * Returns a reference to a potential mate within 3 tiles of the current actor (if they exist).
     * Applies regardless of dinosaur types.
     * @param location
     * @return Actor
     */
    // TODO: Implement such that types and sexes of dinosaurs are considered
    private Actor getMate(GameMap map, Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        ArrayList<Location> locationArrayList2 = new ArrayList<>();
        for (Location loc : locationArrayList ) {
            for (Exit exit : loc.getExits()) {
                if (!locationArrayList2.contains(exit.getDestination())) {
                    locationArrayList2.add(exit.getDestination());
                }
            }
        }
        ArrayList<Location> locationArrayList3 = new ArrayList<>();
        for (Location loc : locationArrayList2 ) {
            for (Exit exit : loc.getExits()) {
                if (!locationArrayList3.contains(exit.getDestination())) {
                    locationArrayList3.add(exit.getDestination());
                }
            }
        }
        for (Location loc : locationArrayList3) {
            if (map.getActorAt(loc) instanceof Dinosaur) {
                return map.getActorAt(loc);
            }
        }
        return null;
    }

/*    private Boolean nextToMate(GameMap map, Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
    }*/

    public void die() {
        this.hungerLevel = 0;
        this.behaviour = null;
        // TODO: Need to remove dinosaur from map
    }
}