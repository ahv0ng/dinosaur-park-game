package game.actors;

import edu.monash.fit2099.engine.GameMap;
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
    public void die(GameMap map) {
        StegosaurCorpse corpse = new StegosaurCorpse();
        map.locationOf(this).addItem(corpse);
        map.removeActor(this);
    }

    /**
     * Lay a StegosaurEgg.
     *
     * @return Egg to be laid on the Stegosaur's current location
     */
    @Override
    protected StegosaurEgg layEgg() { return new StegosaurEgg(); }
}
