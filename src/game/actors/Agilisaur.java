package game.actors;

import edu.monash.fit2099.engine.GameMap;
import game.items.corpses.AgilisaurCorpse;
import game.items.eggs.AgilisaurEgg;

/**
 * Extends Omnivore.
 */
public class Agilisaur extends Omnivore {

    /**
     * Constructor for Agilisaur.
     */
    public Agilisaur() {
        super("Agilisaur", 'g');
        this.setFly(false);
    }

    @Override
    public void die(GameMap map) {
        AgilisaurCorpse corpse = new AgilisaurCorpse();
        map.locationOf(this).addItem(corpse);
        map.removeActor(this);
    }

    /**
     * Lay an Agilisaur Egg.
     *
     * @return Egg to be laid on the Agilisaur's current location
     */
    @Override
    protected AgilisaurEgg layEgg() { return new AgilisaurEgg(); }
}
