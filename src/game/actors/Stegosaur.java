package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
    public Stegosaur(String sex) {
        super(sex, "Stegosaur", 's');
    }

    public Stegosaur() {
        super("Stegosaur", 's');
    }
}
/*
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        super.playTurn(actions, lastAction, map, display);
*/
/*        ground = map.locationOf(this).getGround();
        this.graze(ground);*//*

    }
}
*/

/*
    public void graze(Ground ground) {
        if (ground instanceof Dirt && ground.hasGrass()) {
            ground.removeGrass();
            this.increaseHunger(5); // TODO: Refactor this so that grass increase hunger has constant value
        }
    }

    @Override
    public void mate(Stegosaur stegosaur) {
        if (this.canMate(stegosaur)) {
            // TODO: stop wandering, then somehow get the female to lay egg
        }
    }

    @Override
    private Egg layEgg() {
        return new StegosaurEgg();
    }
}
*/
