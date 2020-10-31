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
    public Stegosaur(String sex) { super(sex, false, "Stegosaur", 's'); }

    /**
     * Constructor for Stegosaur when a StegosaurEgg hatches.
     */
    public Stegosaur() { super(false, "Stegosaur", 's'); }

    @Override
    public void die(GameMap map) {
        StegosaurCorpse corpse = new StegosaurCorpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger or thirst.");
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
