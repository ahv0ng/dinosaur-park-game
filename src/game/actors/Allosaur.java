package game.actors;

import edu.monash.fit2099.engine.GameMap;
import game.items.corpses.AllosaurCorpse;
import game.items.eggs.AllosaurEgg;

/**
 * Extends Dinosaur.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Allosaur extends Carnivore {

    /**
     * Constructor for Allosaur.
     */
    public Allosaur() { super(false, "Allosaur", 'a'); }

    @Override
    public void die(GameMap map) {
        AllosaurCorpse corpse = new AllosaurCorpse();
        map.locationOf(this).addItem(corpse);
        System.out.println(this + " at (" + map.locationOf(this).x() + "," +
                map.locationOf(this).y() + ") died from hunger or thirst.");
        map.removeActor(this);
    }

    @Override
    protected AllosaurEgg layEgg() { return new AllosaurEgg(); }
}
