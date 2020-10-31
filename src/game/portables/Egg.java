package game.portables;

import edu.monash.fit2099.engine.Location;

/**
 * Abstract class. Extends PortableItem.
 *
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Egg extends PortableItem {
    private int timeAlive;
    private String dinosaurName;

    static int POINTS_WHEN_HATCH;
    static final int TIME_TO_HATCH = 10;

    /**
     * Constructor for Egg
     *
     * @param name - String name of the object
     * @param displayChar - Char of display character on the map
     */
    public Egg(String name, char displayChar, String dinosaurName) {
        super(name, displayChar);
        this.dinosaurName = dinosaurName;
    }

    public String getDinosaurName() { return this.dinosaurName; }

    /**
     * Call for Item to tick over next day.
     *
     * @param location - Location of the item
     */
    public void tick(Location location) {
        this.timeAlive++;
        if (this.timeAlive >= TIME_TO_HATCH) {
            try {
                this.hatch(location);
            }
            // Account for egg hatching when an Actor is at the same location as the egg
            catch(IllegalArgumentException e) {
                System.out.println();
            }
        }
    }

    /**
     * Hatch egg. Abstract method.
     *
     * @param location - Location of the item
     */
    public abstract void hatch(Location location);
}
