package game.portables;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Dinosaur;

/**
 * Extends Fruit.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Fruit extends Food {
    private int age;

    static final int FILL = 30;
    static final int COST = 30;
    static final int POINTS_WHEN_FED = 15;
    static final int ROT_AGE = 20;

    /**
     * Constructor for Fruit.
     */
    public Fruit() {
        super("Fruit", 'f');
        this.age = 0;
    }

    /**
     * Return age of the Fruit.
     *
     * @return integer of the age
     */
    public int getAge() { return this.age; }

    /**
     * Call for Item to tick over next day.
     *
     * @param location - Location of the item
     */
    public void tick(Location location) {
        // Evaluate age and rotting status
        this.age++;
        if (this.age >= ROT_AGE) {
            location.removeItem(this);
        }
    }

    /**
     * Feed Fruit to Stegosaur.
     *
     * @param dinosaur - Dinosaur to feed Fruit
     */
    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
        EcoPointsSystem.earn(POINTS_WHEN_FED);
    }
}