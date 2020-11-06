package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import game.actions.DieAction;
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
    public Action die(GameMap map) {
        return new DieAction(new AllosaurCorpse());
    }

    @Override
    protected AllosaurEgg layEgg() { return new AllosaurEgg(); }
}
