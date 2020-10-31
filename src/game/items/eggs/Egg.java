package game.items.eggs;

import edu.monash.fit2099.engine.Location;
import game.items.PortableItem;

/**
 * Abstract class. Extends PortableItem.
 *
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Egg extends PortableItem {
    private int timeAlive;
    private final String dinosaurName;

    private int POINTS_WHEN_HATCH;
    static final int TIME_TO_HATCH = 10;
    // Dinosaurs can eat this Item, but not fed by Players, so there is no feed method.
    static final int FILL = 10;

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

    /**
     * Return the hunger level fill when eaten by Dinosaurs. Use with Dinosaur's eatAtLocation
     * method.
     *
     * There is no feed method because this item cannot be fed by Player, so it is not needed.
     *
     * @return integer value of the hunger level fill when eaten by Dinosaurs
     */
    public int getFill() { return FILL; }

    /**
     * Return the name of the Dinosaur that this Egg will hatch into.
     *
     * @return String of the Dinosaur that will be hatched
     */
    public String getDinosaurName() { return this.dinosaurName; }

    @Override
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
