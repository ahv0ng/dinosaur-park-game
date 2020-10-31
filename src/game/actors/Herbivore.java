package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.behaviours.FollowBehaviour;
import game.ground.Dirt;
import game.scanning.Scan;
import game.items.foods.Fruit;
import game.items.foods.Hay;
import game.items.foods.VegetarianMealKit;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Herbivore extends Dinosaur {

    /**
     * Constructor for Herbivore when game starts.
     *
     * @see Dinosaur's Constructor
     * @param sex - String type that represent the Herbivore's sex
     * @param name - String name of the Herbivore
     * @param displayChar - Char type for displaying Herbivore on the map
     */
    public Herbivore(String sex, boolean flight, String name, Character displayChar) {
        // Use only for start of game, see Dinosaur constructor
        super(sex, flight, name, displayChar);
    }

    /**
     * Constructor for Herbivore when an Egg hatches.
     *
     * @param name - String name of the Herbivore
     * @param displayChar - Char type for displaying Herbivore on the map
     */
    public Herbivore(boolean flight, String name, Character displayChar) {
        super(flight, name, displayChar);
    }

    /**
     * Evaluate whether Herbivore can eat given Item. Herbivores can eat Fruit, Hay, and
     * VegetarianMealKit.
     *
     * @param item - Item object for Herbivore to eat
     * @return boolean value on whether the Herbivore can eat the item
     */
    @Override
    protected boolean canEat(Item item) {
        if (item instanceof Fruit) {
            return true;
        }
        else if (item instanceof Hay) {
            return true;
        }
        else return item instanceof VegetarianMealKit;
    }

    /**
     * Eat at the Location. Herbivores can eat grass at their current Location.
     *
     * @param location - Location type of the current location of the Herbivore
     */
    @Override
    protected void eatAtLocation(Location location) {
        if (location.getGround() instanceof Dirt) {
            Dirt dirt = (Dirt) location.getGround();
            if (dirt.hasGrass()) {
                this.increaseHunger(dirt.getFill());
                System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate grass.");
            }
        }
    }

    /**
     * Look for Food Behaviour specific to Herbivores. Herbivores will look for grass.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the hungry Herbivore
     */
    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        FollowBehaviour behaviour;
        Action action = null;

        // Search for potential foods
        Location grassLocation = Scan.getLocationOfGrass(location);

        if (grassLocation != null) {
            // Follow the grass
            behaviour = new FollowBehaviour(Scan.getLocationOfGrass(location));
            action = behaviour.getFollowLocationAction(this, map);
        }

        // If there is no potential food nearby, it will return null for no Action
        return action;
    }
}
