package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.DieAction;
import game.items.corpses.ArchaeopteryxCorpse;
import game.items.eggs.ArchaeopteryxEgg;

/**
 * Extends Carnivore.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Archaeopteryx extends Carnivore {

    /**
     * Constructor for Archaeopteryx.
     */
    public Archaeopteryx() {
        super("Archaeopteryx", 'x');
        this.setFly(true);
    }

    @Override
    public Action die(GameMap map) {
        return new DieAction(new ArchaeopteryxCorpse());
    }

    @Override
    protected ArchaeopteryxEgg layEgg() { return new ArchaeopteryxEgg(); }
}
