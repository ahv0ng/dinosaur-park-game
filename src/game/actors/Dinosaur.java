package game.actors;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.behaviours.Behaviour;
import game.behaviours.BreedingBehaviour;
import game.behaviours.WanderBehaviour;
import game.portables.Egg;

import java.util.Random;

/**
 * Extends Actor.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Dinosaur extends Actor {
    private int age;
    private int hungerLevel;
    private int daysUnconscious;
    private int daysUntilLay;
    private Boolean pregnant = false;
    private String sex;
    private Behaviour behaviour;
    private Random random = new Random();

    static final int MATING_AGE = 30;
    static final int PREGNANCY_LENGTH = 10;
    static final int MIN_HUNGER = 0;
    static final int MAX_HUNGER = 100;
    static final int MAX_DAYS_UNCONSCIOUS = 20;
    static final int HUNGRY_THRESHOLD = 50;

    /**
     * Constructor for when game starts, so that there are two opposite sex adult Dinosaurs at
     * the start.
     * @param sex - String type that represent the Dinosaur's sex
     * @param name - String name of the Dinosaur
     * @param displayChar - Char type for displaying Dinosaur on the map
     */
    public Dinosaur(String sex, String name, Character displayChar) {
        super(name, displayChar, 100);
        this.age = 30;
        this.hungerLevel = 75;
        this.daysUnconscious = 0;
        this.sex = sex;
        this.behaviour = new WanderBehaviour();
    }

    /**
     * Constructor for when an Egg hatches.
     * @param name - String name of the Dinosaur
     * @param displayChar - Char type for displaying Dinosaur on the map
     */
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

    /**
     * Return Dinosaur's hunger level.
     * @return integer representing the hunger level
     */
    public int getHungerLevel() { return this.hungerLevel; }

    /**
     * Return Dinosaur's sex.
     * @return String representing the sex
     */
    public String getSex() { return this.sex; }

    /**
     * Evaluate whether Dinosaur is hungry.
     * @return boolean value whether Dinosaur is hungry
     */
    protected boolean isHungry() { return this.hungerLevel <= HUNGRY_THRESHOLD; }

    /**
     * Increases hunger level. Hunger level cannot exceed MAX_HUNGER, or be lower than MIN_HUNGER.
     * If argument is negative, then hunger level decreases.
     * @param hunger - integer increase for the hunger level.
     */
    public void increaseHunger(int hunger) {
        int totalHunger = this.hungerLevel + hunger;
        if (totalHunger >= MAX_HUNGER) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            System.out.println(this.toString() + " is full now.");
            this.hungerLevel = MAX_HUNGER;
        }
        else if (totalHunger < MIN_HUNGER) {
            this.hungerLevel = MIN_HUNGER;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerLevel = totalHunger;
        }
    }
    /**
     * Return the number of daysUnconscious for Dinosaur.
     * @return integer value of the number of days unconscious
     */
    protected int getDaysUnconscious() { return this.daysUnconscious; }

    /**
     * Reset the number of daysUnconscious back to zero.
     */
    protected void resetDaysUnconscious() { this.daysUnconscious = 0; }

    /**
     * Increase the number of daysUnconscious by one.
     */
    protected void incrementDaysUnconscious() { this.daysUnconscious++; }

    /**
     * Kill dinosaur and replace with Corpse object at Dinosaur's last location.
     * @param map - the game map
     */
    public void die(GameMap map) {
        Corpse corpse = new Corpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger.");
        map.removeActor(this);
    }
    /**
     * Abstract method to lay Egg
     */
    public abstract Egg layEgg();

    /**
     * Return boolean value if Dinosaur is pregnant.
     * @return boolean value if Dinosaur is pregnant
     */
    public Boolean isPregnant() { return this.pregnant; }

    /**
     * Evaluate whether target Dinosaur is of the opposite sex to the current Dinosaur.
     * @param target Dinosaur object planning to mate with
     * @return boolean value whether target is of the opposite sex
     */
    public Boolean isOppositeSex(Dinosaur target) {
        return !this.getSex().equals(target.getSex());
    }

    /**
     * Set Dinosaur to mate and become pregnant.
     */
    public void mate() {
        this.pregnant = true;
        this.resetDaysUntilLay();
    }

    /**
     * Set pregnant to false.
     */
    protected void noLongerPregnant() { this.pregnant = false; }

    /**
     * Reset number of days to lay Egg.
     */
    protected void resetDaysUntilLay() { this.daysUntilLay = PREGNANCY_LENGTH; }

    /**
     * Return number of remaining days to lay Egg.
     * @return integer representing remaining days to lay an Egg
     */
    protected int getDaysUntilLay() { return this.daysUntilLay; }

    /**
     * Decrement remaining number of days to lay Egg.
     */
    protected void decrementDaysUntilLay() { this.daysUntilLay--; }

    /**
     * Evaluate whether baby dinosaur becomes an adult.
     * @return boolean value if baby dinosaur becomes an adult
     */
    public Boolean isAdult() {
        return this.age >= MATING_AGE;
    }

    /**
     * Increment age of Dinosaur.
     */
    protected void incrementAge() {
        this.age++;
    }

    /**
     * Evaluate the general behaviour of a common Dinosaur.
     * @param map - the game map
     * @param location - Location of the Dinosaur
     */
    protected void generalBehaviour(GameMap map, Location location) {
        this.incrementAge();
        if (this.isHungry()) { System.out.println(this + " at (" + location.x() + ", " + location.y() + ") is hungry!"); }
        if (this.isPregnant()) { this.pregnantBehaviour(map, location); }
    }

    /**
     * Evaluate the behaviour of an unconscious Dinosaur.
     * @param map - the game map
     * @return Action of the Dinosaur
     */
    protected Action unconsciousBehaviour(GameMap map) {
        this.incrementDaysUnconscious();
        if (this.getDaysUnconscious() == MAX_DAYS_UNCONSCIOUS) {
            this.die(map);
        }
        return new DoNothingAction();
    }

    /**
     * Evaluate the behaviour of a pregnant Dinosaur.
     * @param map - the game map
     * @param location - the location of Dinosaur
     */
    protected void pregnantBehaviour(GameMap map, Location location) {
        this.decrementDaysUntilLay();
        if (this.getDaysUntilLay() == 0) {
            map.locationOf(this).addItem(this.layEgg());
            System.out.println(this + " at (" + location.x() + ", " + location.y() + ") laid an egg!");
        }
    }

    /**
     * Evaluate the behaviour of a mating Dinosaur.
     * @param map - the game map
     * @return BreedingBehaviour of a mating Dinosaur
     */
    protected Action breedBehaviour(GameMap map) {
        return new BreedingBehaviour().getAction(this, map);
    }

    /**
     * Evaluate the behaviour of a hungry Dinosaur.
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Dinosaur
     */
    protected abstract Action lookForFoodBehaviour(GameMap map, Location location);
}