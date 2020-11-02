package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.items.eggs.Egg;
import game.items.foods.Food;
import game.scanning.Scan;

import java.util.Random;

/**
 * Extends Actor.
 *
 * @author Nicholas Chua and Alden Vong
 */

public abstract class Dinosaur extends Actor {
    private int age;
    private int hungerLevel;
    private int thirstLevel;
    private int daysUnconscious;
    private int daysUntilLay;
    private Boolean pregnant;
    private Boolean canFly;
    private final String sex;
    private final Random random = new Random();

    static final int MATING_AGE = 30;
    static final int PREGNANCY_LENGTH = 10;
    static final int MIN_HUNGRY_THIRSTY = 0;
    static final int MAX_HUNGRY_THIRSTY = 100;
    static final int MAX_DAYS_UNCONSCIOUS = 20;
    static final int HUNGRY_THIRSTY_THRESHOLD = 50;
    static final int THIRST_POINTS_FOR_DRINK = 10; // TODO: Move this to water for encapsulation

    /**
     * Constructor for when game starts, so that there are two opposite sex adult Dinosaurs at
     * the start.
     *
     * @param sex - String type that represent the Dinosaur's sex
     * @param name - String name of the Dinosaur
     * @param displayChar - Char type for displaying Dinosaur on the map
     */
    public Dinosaur(String name, Character displayChar, String sex) {
        super(name, displayChar, 100);
        this.age = 30;
        this.hungerLevel = 50;
        this.thirstLevel = 50;
        this.daysUnconscious = 0;
        this.sex = sex;
        this.pregnant = false;
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

        // Randomise sex for this dinosaur
        String[] sexTypes = {"Male", "Female"};
        this.sex = sexTypes[random.nextInt(2)];
    }

    public String getName() { return this.name; }

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
    public boolean checkFly() { return this.canFly; }

    /**
     * Set canFly attribute. Use if Dinosaur subclass can fly.
     *
     * @param canFly - boolean value to be assigned to canFly attribute
     */
    // TODO: Could change this so that it sets automatically, that could be cool and don't need to use this for every Dinosaur
    protected void setFly(boolean canFly) { this.canFly = canFly; }

    // TODO: Make separate Action for Eat and Drink (refactor)
    // TODO: Local variables for behaviours
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        if (this.hungerLevel == MIN_HUNGRY_THIRSTY || this.thirstLevel == MIN_HUNGRY_THIRSTY) {
            // Continue being unconscious until fed/dies
            return this.unconsciousAction(map);
        }

        this.daysUnconscious = 0; // Reset daysUnconscious every turn it is not unconscious
        this.hungerLevel--;
        this.thirstLevel--;

        Action action;
        if (!(this.isHungry()) && !(this.isThirsty())) {
            // Look for a mate
            if (this.breedBehaviour(map) != null) {
                return this.breedBehaviour(map);
            }
        }
        else if (this.isHungry()) {
            // Look for food
            this.eatAtLocation(location); // TODO: Put this into lookForFoodBehaviour (double Action) - same with drinkAtLocation
            action = this.lookForFoodBehaviour(map, location);
            if (action != null) {
                return action;
            }
        }
        else if (this.isThirsty()){ // TODO: Figure out why IntelliJ insists on this warning and fix if possible
            // Look for water
            this.drinkAtLocation(location);
            action = this.lookForWaterBehaviour(map, location);
            if (action != null) {
                return action;
            }
        }
        else {
            // Wander around or do nothing
            action = new WanderBehaviour().getAction(this, map);
            if (action != null)
                return action;
        }
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
        if (this.isHungry()) {
            System.out.println(this + " at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.isThirsty()) {
            System.out.println(this + " at (" + location.x() + ", " + location.y() + ") is thirsty!");
        }
        if (this.pregnant) {
            this.pregnantBehaviour(map, location);
        }
    }

    /**
     * Helper method to evaluate whether Dinosaur is hungry.
     *
     * @return boolean value whether Dinosaur is hungry
     */
    private boolean isHungry() { return this.hungerLevel <= HUNGRY_THIRSTY_THRESHOLD; }

    /**
     * Helper method to evaluate whether Dinosaur is thirsty.
     *
     * @return boolean value whether Dinosaur is thirsty
     */
    private boolean isThirsty() { return this.thirstLevel <= HUNGRY_THIRSTY_THRESHOLD; }

    /**
     * Eat at the Location. Abstract eating method that differs between types of Dinosaurs.
     *
     * @param location - the current Location of Dinosaur
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
     * Increase hunger level. Hunger level cannot exceed MAX_HUNGRY_THIRSTY.
     *
     * @param hunger - integer increase for the hunger level.
     */
    public void increaseHunger(int hunger) {
        if (hunger < 0) {
            throw new IllegalArgumentException();
        }

        int totalHunger = this.hungerLevel + hunger;
        if (totalHunger >= MAX_HUNGRY_THIRSTY) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            System.out.println(this.toString() + " is full now.");
            this.hungerLevel = MAX_HUNGRY_THIRSTY;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerLevel = totalHunger;
        }
    }
    // TODO: Change method names and hungerLevel, thirstLevel
    /**
     * Increase thirst level. Thirst level cannot exceed MAX_HUNGRY_THIRSTY.
     *
     * @param thirst - integer increase for the thirst level.
     */
    public void increaseThirst(int thirst) {
        if (thirst < 0) {
            throw new IllegalArgumentException();
        }
        int totalThirst = this.thirstLevel + thirst;
        if (totalThirst >= MAX_HUNGRY_THIRSTY) {
            // Handles the case when given thirst will cause thirstLevel to exceed maximum
            System.out.println(this.toString() + " is fully quenched now.");
            this.thirstLevel = MAX_HUNGRY_THIRSTY;
        }
        else {
            // Else increase thirstLevel normally
            this.thirstLevel = totalThirst;
        }
    }

    /**
     * Evaluate the behaviour of an unconscious Dinosaur.
     *
     * @param map - the game map
     * @return Action of the Dinosaur
     */
    protected Action unconsciousAction(GameMap map) {
        this.daysUnconscious++;
        if (this.daysUnconscious == MAX_DAYS_UNCONSCIOUS) {
            this.die(map);
        }
        return new DoNothingAction();
    }

    /**
     * Kill Dinosaur and replace with Corpse object at Dinosaur's last location.
     *
     * Used by AttackAction and Dinosaur's unconsciousBehaviour method.
     *
     * @param map - the game map
     */
    public abstract void die(GameMap map);

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

    /**
     * Evaluate the behaviour of a thirsty Dinosaur
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the thirsty Dinosaur
     */
    private Action lookForWaterBehaviour(GameMap map, Location location) {
        FollowBehaviour behaviour;
        Action action = null;

        Location waterLocation = Scan.getLocationOfWater(location);
        if (waterLocation != null) {
            behaviour = new FollowBehaviour(Scan.getLocationOfWater(location));
            action = behaviour.getAction(this, map);
        }
        // If there is no Water nearby, it should return null for no Action
        return action;
    }

    /**
     * Drink at the Location. Attempt to drink is successful only if there is an adjacent Water.
     *
     * @param location - the current Location of Dinosaur
     */
    private void drinkAtLocation(Location location) {
        if (Scan.adjacentWater(location)) {
            this.increaseThirst(THIRST_POINTS_FOR_DRINK);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " drank water.");
            }
        }
}