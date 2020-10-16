package game.portables;

import game.actors.Dinosaur;

/**
 * Extends MealKit.
 * @author Nicholas Chua and Alden Vong
 */
public class CarnivoreMealKit extends MealKit {
    static final int COST = 500;

    /**
     * Constructor for CarnivoreMealKit.
     */
    public CarnivoreMealKit() {
        super("Carnivore Meal Kit", 'c');
    }

    /**
     * Feed CarnivoreMealKit to Allosaur.
     * @param dinosaur - Allosaur to feed CarnivoreMealKit
     */
    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
    }
}
