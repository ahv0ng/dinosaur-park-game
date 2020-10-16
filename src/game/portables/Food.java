package game.portables;

import game.actors.Dinosaur;

/**
 * Abstract class. Extends PortableItem.
 *
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Food extends PortableItem {
    private int FILL;

    /**
     * Constructor for Food
     *
     * @param name - String name of the object
     * @param displayChar - Char of display character on the map
     */
    public Food(String name, char displayChar) {
        super(name, displayChar);
    }

    /**
     * Feed Item to Dinosaur.
     *
     * @param dinosaur - Dinosaur to feed Item
     */
    public abstract void feed(Dinosaur dinosaur);
}