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
    public Allosaur() {
        super("Allosaur", 'a');
        this.setFly(false);
    }

    @Override
    public void die(GameMap map) {
        AllosaurCorpse corpse = new AllosaurCorpse();
        map.locationOf(this).addItem(corpse);
        map.removeActor(this);
    }

    @Override
    protected AllosaurEgg layEgg() { return new AllosaurEgg(); }
}
