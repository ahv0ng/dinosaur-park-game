package game.items.foods;

import game.actors.Dinosaur;
import game.items.PortableItem;

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
     * Feed Item to Dinosaur through FeedAction.
     *
     * Players have a choice to feed Food item to Dinosaur. Whereas other
     * types of Items cannot be fed by the Player, the Dinosaur chooses
     * to eat those other Items.
     *
     * @param dinosaur - Dinosaur to feed Item
     */
    public abstract void feed(Dinosaur dinosaur);
}