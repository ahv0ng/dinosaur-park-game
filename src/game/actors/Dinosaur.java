package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.behaviours.Behaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.Egg;

import java.util.ArrayList;
import java.util.Random;

public abstract class Dinosaur extends Actor {
    private int age;
    private int hungerLevel;
    private int daysUnconscious;
    private int daysUntilDeath;
    private int daysUntilLay;
    private Boolean pregnant = false;
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

    private String getSex() { return this.sex; }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        return new Actions(new AttackAction(this));
    }
    protected boolean isHungry() { return this.hungerLevel <= 50; }

    public void increaseHunger(int hunger) {
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
    public int getHungerLevel() { return this.hungerLevel; }

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

/*    private Boolean nextToMate(GameMap map, Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
    }*/

    /**
     * Returns a reference to a Dirt object with grass attribute set to True (if it exists).
     * @param map
     * @param location
     * @return
     */
    public Location getGrass(GameMap map, Location location) {
        for (Location loc : ScanSurrounds.getSurroundingLocations(map, location)) {
            if (loc.getGround() instanceof Dirt && ((Dirt) loc.getGround()).hasGrass()) {
                return loc;
            }
        }
        return null;
    }

    public void die() {
        this.hungerLevel = 0;
        this.behaviour = null;
        // TODO: Need to remove dinosaur from map - see Attack Action for corpse
    }
    // Everything to do with mating
    protected abstract Egg layEgg();
    protected abstract Dinosaur getMate(GameMap map, Location location);
    protected Boolean isPregnant() { return this.pregnant; }
    protected Boolean isFemale() { return this.getSex().equals("Female"); }
    protected Boolean isOppositeSex(Dinosaur target) {
        return !this.getSex().equals(target.getSex());
    }
    protected void mate() {
        this.pregnant = true;
        this.resetDaysUntilLay();
    }
    protected void noLongerPregnant() { this.pregnant = false; }
    protected void resetDaysUntilLay() { this.daysUntilLay = 10; }
    protected int getDaysUntilLay() { return this.daysUntilLay; }
    protected void decrementDaysUntilLay() { this.daysUntilLay -= 1; }
    protected Boolean adjacentMate(GameMap map, Dinosaur target) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : map.locationOf(this).getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList.contains(map.locationOf(target));
    }
    // Everything to do with baby dinosaurs
    protected Boolean isAdult() {
        return this.age >= 30;
    }
    protected void incrementAge() {
        this.age += 1;
    }
    protected int getAge() {
        return this.age;
    }
}