package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.DrinkAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.Water;
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
    private int hungerPoints;
    private int thirstPoints;
    private int daysUnconscious;
    private int daysUntilLay;
    private boolean pregnant;
    private boolean canFly;
    private final String sex;
    private final Random random = new Random();

    static final int MATING_AGE = 30;
    static final int PREGNANCY_LENGTH = 10;
    static final int MIN_HUNGRY_THIRSTY = 0;
    static final int MAX_HUNGRY_THIRSTY = 100;
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
    public Dinosaur(String name, Character displayChar, String sex) {
        super(name, displayChar, 100);
        this.age = 30;
        this.hungerPoints = 50;
        this.thirstPoints = 50;
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
        this.hungerPoints = 10;
        this.thirstPoints = 50;
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
    protected void setFly(boolean canFly) { this.canFly = canFly; }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        Location location = map.locationOf(this);
        this.generalBehaviour(map, location);

        if (this.hungerPoints == MIN_HUNGRY_THIRSTY || this.thirstPoints == MIN_HUNGRY_THIRSTY) {
            // Continue being unconscious until fed/dies
            return this.unconsciousAction(map);
        }

        this.daysUnconscious = 0; // Reset daysUnconscious every turn it is not unconscious
        this.hungerPoints--;
        this.thirstPoints--;

        Action action;
        if (!(this.isHungry()) && !(this.isThirsty())) {
            Action breedBehaviour = new BreedingBehaviour().getAction(this, map);
            // Look for a mate
            if (breedBehaviour != null) {
                return breedBehaviour;
            }
        }
        else if (this.isHungry()) {
            // Look for or eat food
            action = this.lookForFoodOrEatBehaviour(map, location);
            if (action != null) {
                return action;
            }
        }
        // Look for or drink water
        else if (this.isThirsty()){
            action = this.lookForOrDrinkWaterBehaviour(map, location);
            if (action != null) {
                return action;
            }
        }
        // Wander around or do nothing
        action = new WanderBehaviour().getAction(this, map);
        if (action != null)
            return action;

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
    private boolean isHungry() { return this.hungerPoints <= HUNGRY_THIRSTY_THRESHOLD; }

    /**
     * Helper method to evaluate whether Dinosaur is thirsty.
     *
     * @return boolean value whether Dinosaur is thirsty
     */
    private boolean isThirsty() { return this.thirstPoints <= HUNGRY_THIRSTY_THRESHOLD; }

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
    public void increaseHungerPoints(int hunger) {
        if (hunger < 0) {
            throw new IllegalArgumentException();
        }

        int totalHunger = this.hungerPoints + hunger;
        if (totalHunger >= MAX_HUNGRY_THIRSTY) {
            // Handles the case when given hunger will cause hungerLevel to exceed maximum
            System.out.println(this.toString() + " is full now.");
            this.hungerPoints = MAX_HUNGRY_THIRSTY;
        }
        else {
            // Else increase hungerLevel normally
            this.hungerPoints = totalHunger;
        }
    }

    /**
     * Increase thirst level. Thirst level cannot exceed MAX_HUNGRY_THIRSTY.
     *
     * @param thirst - integer increase for the thirst level.
     */
    public void increaseThirstPoints(int thirst) {
        if (thirst < 0) {
            throw new IllegalArgumentException();
        }
        int totalThirst = this.thirstPoints + thirst;
        if (totalThirst >= MAX_HUNGRY_THIRSTY) {
            // Handles the case when given thirst will cause thirstLevel to exceed maximum
            System.out.println(this.toString() + " is fully quenched now.");
            this.thirstPoints = MAX_HUNGRY_THIRSTY;
        }
        else {
            // Else increase thirstLevel normally
            this.thirstPoints = totalThirst;
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
            System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                    map.locationOf(this).y() + ") has not eaten or drunk for too long.");
            return this.die(map);
        }
        return new DoNothingAction();
    }

    /**
     * Remove Dinosaur from map and replace with Corpse object at Dinosaur's last location.
     * If applicable, print message to console that Dinosaur has died.
     *
     * Used by AttackAction and Dinosaur's unconsciousBehaviour method.
     *
     * @param map - the game map
     * @return DieAction
     */
    public abstract Action die(GameMap map);

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
    public boolean isAdult() {
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
    protected abstract Action lookForFoodOrEatBehaviour(GameMap map, Location location);

    /**
     * Evaluate the behaviour of a thirsty Dinosaur: drinks at the Location or moves towards
     * a nearby Water. Returns null if no water in sight.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the thirsty Dinosaur
     */
    private Action lookForOrDrinkWaterBehaviour(GameMap map, Location location) {
        Action action = null;
        FollowBehaviour behaviour;
        Water water = Scan.adjacentWater(location);
        if (water != null) {
            return new DrinkAction(water);
        }
        Location waterLocation = Scan.getLocationOfWater(location);
        if (waterLocation != null) {
            behaviour = new FollowBehaviour(Scan.getLocationOfWater(location));
            return behaviour.getAction(this, map);
        }
        // If there is no Water nearby to drink or follow, it should return null for no Action
        return action;
    }
}