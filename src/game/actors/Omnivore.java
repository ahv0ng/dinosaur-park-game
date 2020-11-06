package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.actions.EatAction;
import game.behaviours.FollowBehaviour;
import game.ground.Dirt;
import game.items.corpses.Corpse;
import game.items.eggs.Egg;
import game.items.foods.Food;
import game.scanning.Scan;

/**
 * Extends Dinosaur.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Omnivore extends Dinosaur {

    /**
     * Constructor for Omnivores.
     *
     * @param name - String type of the Omnivore's name
     * @param displayChar - Char type for displaying Omnivore on the map
     */
    public Omnivore(String name, Character displayChar) {
        super(name, displayChar);
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
     * Evaluate behaviour of Omnivore when looking for or eating food. Omnivore will search
     * for Dirt with grass or Corpses or Eggs.
     *
     * @param map - the game map
     * @param location - the current location of the Dinosaur
     * @return Action of the Omnivore once food is found
     */
    @Override
    protected Action lookForFoodOrEatBehaviour(GameMap map, Location location) {
        Action action = null;
        FollowBehaviour behaviour;

        if (location.getGround() instanceof Dirt) {
            Dirt dirt = (Dirt) location.getGround();
            if (dirt.hasGrass()) {
                // Eat the grass
                return new EatAction(dirt);
            }
        }

        Corpse corpse = Scan.getCorpse(location);
        if (corpse != null) {
            // Eat the corpse
            return new EatAction(corpse);
        }

        Egg egg = Scan.getEgg(location);
        if (egg != null) {
            // Eat the egg
            return new EatAction(egg);
        }

        Location grassLocation = Scan.getLocationOfGrass(location);
        if (grassLocation != null) {
            // Follow the grass
            behaviour = new FollowBehaviour(grassLocation);
            return behaviour.getAction(this, map);
        }

        Location corpseLocation = Scan.getLocationOfCorpse(location);
        if (corpseLocation != null) {
            // Follow the corpse
            behaviour = new FollowBehaviour(corpseLocation);
            return behaviour.getAction(this, map);
        }

        Location eggLocation = Scan.getLocationOfEgg(location);
        if (eggLocation != null) {
            // Follow the egg
            behaviour = new FollowBehaviour(eggLocation);
            return behaviour.getAction(this, map);
        }

        // If there is no potential food nearby, it will return null for no Action
        return action;
    }
}
