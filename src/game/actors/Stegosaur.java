package game.actors;

import game.portables.StegosaurEgg;

/**
 * A herbivorous dinosaur.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Stegosaur extends Herbivore {

    /**
     * Constructor for Stegosaur when game start.
     *
     * @param sex - String of the Dinosaur's sex
     */
    public Stegosaur(String sex) { super(sex, "Stegosaur", 's'); }

    /**
     * Constructor for Stegosaur when a StegosaurEgg hatches.
     */
    public Stegosaur() { super("Stegosaur", 's'); }

    /**
     * Lay a StegosaurEgg.
     *
     * @return Egg to be laid on the Stegosaur's current location
     */
    @Override
    protected StegosaurEgg layEgg() { return new StegosaurEgg(); }
}
