package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.behaviours.FollowBehaviour;
import game.scanning.Scan;
import game.portables.Food;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Omnivore extends Dinosaur {
    static final int HUNGER_POINTS_FROM_CORPSE = 50;

    /**
     * Constructor for Omnivores.
     *
     * @param name - String type of the Omnivore's name
     * @param displayChar - Char type for displaying Omnivore on the map
     */
    public Omnivore(boolean flight, String name, Character displayChar) {
        super(flight, name, displayChar);
    }

    /**
     * Evaluate whether Omnivore can eat given Item. Omnivores are allowed to eat
     * Fruit, Grass, Hay and MealKits.
     *
     * @param item - Item object for Dinosaur to eat
     * @return boolean value on whether the Omnivore can eat the item
     */
    @Override
    protected boolean canEat(Item item) { return item instanceof Food; }

    /**
     * Eat at the Location. Omnivores can eat corpses at their current location.
     *
     * @param location - Location type of the current location of Omnivore
     */
    @Override
    protected void eatAtLocation(Location location) {
        Corpse corpse = Scan.getCorpse(location);

        if (corpse != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_CORPSE);
            location.removeItem(corpse);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }
    }

    /**
     * Evaluate behaviour of Omnivore when looking for food. Omnivore will search
     * for Corpses or Dirt with grass.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the Omnivore once food is found
     */
    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        FollowBehaviour behaviour;
        Action action = null;

        // Search for potential foods
        Location corpseLocation = Scan.getLocationOfCorpse(location);
        Location grassLocation = Scan.getLocationOfGrass(location);

        if (corpseLocation != null) {
            // Follow the corpse
            behaviour = new FollowBehaviour(corpseLocation);
            action = behaviour.getFollowLocationAction(this, map);
        }
        else if (grassLocation != null) {
            // Follow the grass
            behaviour = new FollowBehaviour(grassLocation);
            action = behaviour.getFollowLocationAction(this, map);
        }

        // If there is no potential food nearby, it will return null for no Action
        return action;
    }
}
