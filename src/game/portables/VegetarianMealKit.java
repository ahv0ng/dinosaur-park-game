package game.portables;

import game.actors.Dinosaur;

/**
 * Extends MealKit.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class VegetarianMealKit extends MealKit {
    static final int COST = 100;

    /**
     * Constructor for VegetarianMealKit.
     */
    public VegetarianMealKit() {
        super("Vegetarian Meal Kit", 'v');
    }

    /**
     * Feed VegetarianMealKit to Dinosaur.
     *
     * @param dinosaur - Dinosaur to feed MealKit
     */
    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
    }
}
