package game.items.corpses;

import edu.monash.fit2099.engine.Item;
import game.items.WildEdible;

/**
 * A class representing living things that have died. A corpse of a particular species can be eaten by a Dinosaur of
 *  a different species.
 *
 * @author Nicholas Chua and Alden Vong
 */
public abstract class Corpse extends Item implements WildEdible {
    // Dinosaurs can eat this Item, but not fed by Players, so there is no feed method.
    private int FILL;

    /***
     * Constructor for Corpse.
     */
    public Corpse(String name, char displayChar) {
        super(name, displayChar, false);
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
}
