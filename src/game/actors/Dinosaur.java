package game.actors;

import edu.monash.fit2099.engine.*;
import game.Corpse;
import game.behaviours.Behaviour;
import game.behaviours.BreedingBehaviour;
import game.behaviours.WanderBehaviour;
import game.portables.Egg;



import java.util.Random;

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

    /**
     * Constructor for when egg hatches
     * @param name
     * @param displayChar
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

    public int getHungerLevel() { return this.hungerLevel; }
    public String getSex() { return this.sex; }

    protected boolean isHungry() { return this.hungerLevel <= 50; }

    public void increaseHunger(int hunger) {
        int totalHunger = this.hungerLevel + hunger;
        if (totalHunger >= MAX_HUNGER) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            System.out.println(this.toString() + " is full now.");
            this.hungerLevel = MAX_HUNGER;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerLevel = totalHunger;
        }
    }
    protected int getDaysUnconscious() {
        return this.daysUnconscious;
    }
    protected void resetDaysUnconscious() {
        this.daysUnconscious = 0;
    }
    protected void incrementDaysUnconscious() {
        this.daysUnconscious += 1;
    }
    public void die(GameMap map) {
        Corpse corpse = new Corpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger.");
        map.removeActor(this);
    }
    // Everything to do with mating
    public abstract Egg layEgg();
    public Boolean isPregnant() { return this.pregnant; }
    public Boolean isOppositeSex(Dinosaur target) {
        return !this.getSex().equals(target.getSex());
    }
    public void mate() {
        this.pregnant = true;
        this.resetDaysUntilLay();
    }
    protected void noLongerPregnant() { this.pregnant = false; }
    protected void resetDaysUntilLay() { this.daysUntilLay = 10; }
    protected int getDaysUntilLay() { return this.daysUntilLay; }
    protected void decrementDaysUntilLay() { this.daysUntilLay -= 1; }
    // Everything to do with baby dinosaurs
    public Boolean isAdult() {
        return this.age >= 30;
    }
    protected void incrementAge() {
        this.age += 1;
    }

    // Patterns of behaviour common to all dinosaurs, that are used in a dinosaur's playTurn method
    protected void generalBehaviour(GameMap map, Location location) {
        this.incrementAge();
        if (this.isHungry()) { System.out.println(this + " at (" + location.x() + ", " + location.y() + ") is hungry!"); }
        if (this.isPregnant()) { this.pregnantBehaviour(map, location); }
    }
    // Everything to do with dying stegosaurs
    protected Action unconsciousBehaviour(GameMap map) {
        this.incrementDaysUnconscious();
        if (this.getDaysUnconscious() == 20) {
            this.die(map);
        }
        return new DoNothingAction();
    }
    protected void pregnantBehaviour(GameMap map, Location location) {
        this.decrementDaysUntilLay();
        if (this.getDaysUntilLay() == 0) {
            map.locationOf(this).addItem(this.layEgg());
            System.out.println(this + " at (" + location.x() + ", " + location.y() + ") laid an egg!");
        }
    }
    protected Action breedBehaviour(GameMap map) {
        return new BreedingBehaviour().getAction(this, map);
    }
    protected abstract Action lookForFoodBehaviour(GameMap map, Location location);
}