package game.items.foods;

import game.actors.Dinosaur;

/**
 * Abstract class. Extends Food.
 *
 * @author Nicholas Chua and Alden Vong
 */
public abstract class MealKit extends Food {
    static final int FILL = 100;

    /**
     * Constructor for MealKit.
     *
     * @param name - String representing the name of the object
     * @param displayChar - Char displaying the character on the map
     */
    public MealKit(String name, char displayChar) {
        super(name, displayChar);
    }

    @Override
    public void feed(Dinosaur dinosaur) { dinosaur.increaseHungerPoints(FILL); }
}
