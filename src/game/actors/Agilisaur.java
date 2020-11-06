package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.DieAction;
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
    public Action die(GameMap map) {
        return new DieAction(new AgilisaurCorpse());
    }

    /**
     * Lay an Agilisaur Egg.
     *
     * @return Egg to be laid on the Agilisaur's current location
     */
    @Override
    protected AgilisaurEgg layEgg() { return new AgilisaurEgg(); }
}
