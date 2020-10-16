package game.portables;

import edu.monash.fit2099.engine.Location;

/**
 * Abstract class. Extends PortableItem.
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Egg extends PortableItem {
    private int timeAlive;
    static int POINTS_WHEN_HATCH;

    /**
     * Constructor for Egg
     * @param name - String name of the object
     * @param displayChar - Char of display character on the map
     */
    public Egg(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Call for Item to tick over next day.
     * @param location - Location of the item
     */
    public void tick(Location location) {
        this.timeAlive++;
        if (this.timeAlive == 10) {
            this.hatch(location);
        }
    }

    /**
     * Hatch egg. Abstract method.
     * @param location - Location of the item
     */
    public abstract void hatch(Location location);
}
