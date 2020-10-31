package game.actors;

import edu.monash.fit2099.engine.GameMap;
import game.items.corpses.ArchaeopteryxCorpse;
import game.items.eggs.ArchaeopteryxEgg;
import game.items.eggs.Egg;

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
        super(true, "Archaeopteryx", 'x');
    }

    @Override
    public void die(GameMap map) {
        ArchaeopteryxCorpse corpse = new ArchaeopteryxCorpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger or thirst.");
        map.removeActor(this);
    }

    @Override
    protected Egg layEgg() { return new ArchaeopteryxEgg(); }
}
