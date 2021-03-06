package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.DieAction;
import game.items.corpses.StegosaurCorpse;
import game.items.eggs.StegosaurEgg;

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
    public Stegosaur(String sex) {
        super("Stegosaur", 's', sex);
        this.setFly(false);
    }

    /**
     * Constructor for Stegosaur when a StegosaurEgg hatches.
     */
    public Stegosaur() {
        super("Stegosaur", 's');
        this.setFly(false);
    }

    @Override
    public Action die(GameMap map) {
        return new DieAction(new StegosaurCorpse());
    }

    /**
     * Lay a StegosaurEgg.
     *
     * @return Egg to be laid on the Stegosaur's current location
     */
    @Override
    protected StegosaurEgg layEgg() { return new StegosaurEgg(); }
}
