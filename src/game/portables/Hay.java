package game.portables;

import game.EcoPointsSystem;
import game.actors.Dinosaur;

/**
 * Extends Food.
 * @author Nicholas Chua and Alden Vong
 */
public class Hay extends Food {
    static final int COST = 20;
    static final int FILL = 20;
    static final int POINTS_WHEN_FED = 10;

    /**
     * Constructor for Hay.
     */
    public Hay() {
        super("Hay", 'h');
    }

    /**
     * Feed Hay to Dinosaur.
     * @param dinosaur - Dinosaur to feed Hay
     */
    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
        EcoPointsSystem.earn(POINTS_WHEN_FED);
    }
}
