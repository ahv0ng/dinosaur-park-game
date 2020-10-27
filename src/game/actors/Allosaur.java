package game.actors;

import game.portables.AllosaurEgg;

/**
 * Extends Dinosaur.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Allosaur extends Carnivore {

    /**
     * Constructor for Allosaur.
     */
    public Allosaur() { super("Allosaur", 'a'); }

    @Override
    protected AllosaurEgg layEgg() { return new AllosaurEgg(); }
}
