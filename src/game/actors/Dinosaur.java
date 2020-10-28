package game.actors;

import edu.monash.fit2099.engine.*;
import game.Corpse;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.WanderBehaviour;
import game.portables.Egg;
import game.portables.Food;

import java.util.Random;

/**
 * Extends Actor.
 *
 * @author Nicholas Chua and Alden Vong
 */

// TODO: Increase from eating specific food to be put into food classes themselves
public abstract class Dinosaur extends Actor {
    private int age;
    private int hungerLevel;
    private int thirstLevel;
    private int daysUnconscious;
    private int daysUntilLay;
    private Boolean pregnant;
    protected Boolean canFly; // Assign true to flying dinosaurs like Archaeopteryx, otherwise all Dinosaurs are false
    private final String sex;
    private final Random random = new Random();

    static final int MATING_AGE = 30;
    static final int PREGNANCY_LENGTH = 10;
    static final int MIN_HUNGRY_THIRSTY = 0;
    static final int MAX_HUNGER_THIRSTY = 100;
    static final int MAX_DAYS_UNCONSCIOUS = 20;
    static final int HUNGRY_THIRSTY_THRESHOLD = 50;

    /**
     * Constructor for when game starts, so that there are two opposite sex adult Dinosaurs at
     * the start.
     *
     * @param sex - String type that represent the Dinosaur's sex
     * @param name - String name of the Dinosaur
     * @param displayChar - Char type for displaying Dinosaur on the map
     */
    public Dinosaur(String sex, String name, Character displayChar) {
        super(name, displayChar, 100);
        this.age = 30;
        this.hungerLevel = 50;
        this.daysUnconscious = 0;
        this.sex = sex;
        this.pregnant = false;
        this.canFly = false;
    }

    /**
     * Constructor for when an Egg hatches.
     *
     * @param name - String name of the Dinosaur
     * @param displayChar - Char type for displaying Dinosaur on the map
     */
    public Dinosaur(String name, Character displayChar) {
        super(name, displayChar, 100);
        this.age = 0;
        this.hungerLevel = 10;
        this.daysUnconscious = 0;
        this.pregnant = false;
        this.canFly = false;

        // Randomise sex for this dinosaur
        String[] sexTypes = {"Male", "Female"};
        this.sex = sexTypes[random.nextInt(2)];
    }

    /**
     * Return Dinosaur's sex.
     *
     * @return String representing the sex
     */
    public String getSex() { return this.sex; }

    /**
     * Return pregnant status.
     *
     * @return boolean value representing Dinosaur's sex
     */
    public boolean isPregnant() { return this.pregnant; }

    /**
     * Return whether Dinosaur can fly.
     *
     * @return boolean value determining whether Dinosaur can fly
     */
    public boolean canFly() { return this.canFly; }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        if (this.hungerLevel == MIN_HUNGRY_THIRSTY) {
            // Continue being unconscious until fed/dies
            return this.unconsciousBehaviour(map);
        }

        this.daysUnconscious = 0; // Reset daysUnconscious every turn it is not unconscious
        this.hungerLevel--;

        if (!(this.isHungry())) {
            // Look for a mate
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        else if (this.isHungry()) { // TODO: Figure out why IntelliJ insists on this warning and fix if possible
            // Look for food
            this.eatAtLocation(location);
            if (this.lookForFoodBehaviour(map, location) != null) {
                return this.lookForFoodBehaviour(map, location);
            }
        }

        // Wander around or do nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
    }

    /**
     * Evaluate the general behaviour of all Dinosaurs.
     *
     * @param map - the game map
     * @param location - Location of the Dinosaur
     */
    private void generalBehaviour(GameMap map, Location location) {
        this.age++;
        if (this.isHungry()) { System.out.println(this + " at (" + location.x() + ", " + location.y() + ") is hungry!"); }
        if (this.pregnant) { this.pregnantBehaviour(map, location); }
    }

    /**
     * Helper method to evaluate whether Dinosaur is hungry.
     *
     * @return boolean value whether Dinosaur is hungry
     */
    private boolean isHungry() { return this.hungerLevel <= HUNGRY_THIRSTY_THRESHOLD; }

    /**
     * Eat at the Location. Abstract eating method that differs between types of Dinosaurs.
     *
     * @param location - Location type of the current Location of Dinosaur
     */
    protected abstract void eatAtLocation(Location location);

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
     * Helper method to evaluate whether Dinosaur can eat this item.
     *
     * @param item - Item object for Dinosaur to eat
     * @return boolean value on whether Dinosaur can eat the item
     */
    protected abstract boolean canEat(Item item);

    /**
     * Increase hunger level. Hunger level cannot exceed MAX_HUNGER.
     *
     * @param hunger - integer increase for the hunger level.
     */
    public void increaseHunger(int hunger) {
        if (hunger < 0) {
            throw new IllegalArgumentException();
        }

        int totalHunger = this.hungerLevel + hunger;
        if (totalHunger >= MAX_HUNGER_THIRSTY) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            System.out.println(this.toString() + " is full now.");
            this.hungerLevel = MAX_HUNGER_THIRSTY;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerLevel = totalHunger;
        }
    }

    /**
     * Evaluate the behaviour of an unconscious Dinosaur.
     *
     * @param map - the game map
     * @return Action of the Dinosaur
     */
    protected Action unconsciousBehaviour(GameMap map) {
        this.daysUnconscious++;
        if (this.daysUnconscious == MAX_DAYS_UNCONSCIOUS) {
            this.die(map);
        }
        return new DoNothingAction();
    }

    /**
     * Kill dinosaur and replace with Corpse object at Dinosaur's last location.
     *
     * @param map - the game map
     */
    private void die(GameMap map) {
        Corpse corpse = new Corpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger.");
        map.removeActor(this);
    }

    /**
     * Evaluate the behaviour of a mating Dinosaur.
     *
     * @param map - the game map
     * @return BreedingBehaviour of a mating Dinosaur
     */
    protected Action breedBehaviour(GameMap map) {
        return new BreedingBehaviour().getAction(this, map);
    }

    /**
     * Mate with Dinosaur of same species, become pregnant and start countdown for daysUntilLay.
     */
    public void mate() {
        this.pregnant = true;
        this.daysUntilLay = PREGNANCY_LENGTH;
    }

    /**
     * Evaluate whether baby dinosaur becomes an adult.
     *
     * @return boolean value if baby dinosaur becomes an adult
     */
    public Boolean isAdult() {
        return this.age >= MATING_AGE;
    }

    /**
     * Evaluate whether target Dinosaur is of the opposite sex to the current Dinosaur.
     *
     * @param target Dinosaur object planning to mate with
     * @return boolean value whether target is of the opposite sex
     */
    public boolean isOppositeSex(Dinosaur target) { return !this.sex.equals(target.getSex()); }

    /**
     * Evaluate the behaviour of a pregnant Dinosaur.
     *
     * @param map - the game map
     * @param location - the location of Dinosaur
     */
    private void pregnantBehaviour(GameMap map, Location location) {
        this.daysUntilLay--;
        if (this.daysUntilLay == 0) {
            this.pregnant = false;
            map.locationOf(this).addItem(this.layEgg());
            System.out.println(this + " at (" + location.x() + ", " + location.y() + ") laid an egg!");
        }
    }

    /**
     * Abstract method to lay Egg
     */
    protected abstract Egg layEgg();

    /**
     * Evaluate the behaviour of a hungry Dinosaur. Abstract method that differs between different types
     * of Dinosaurs.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Dinosaur
     */
    protected abstract Action lookForFoodBehaviour(GameMap map, Location location);
}