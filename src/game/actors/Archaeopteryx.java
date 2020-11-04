package game.actors;

import edu.monash.fit2099.engine.GameMap;
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
    // TODO: Refactor this in all Dinosaur classes so not repeated
    @Override
    public void die(GameMap map) {
        ArchaeopteryxCorpse corpse = new ArchaeopteryxCorpse();
        map.locationOf(this).addItem(corpse);
        map.removeActor(this);
    }

    @Override
    protected ArchaeopteryxEgg layEgg() { return new ArchaeopteryxEgg(); }
}
