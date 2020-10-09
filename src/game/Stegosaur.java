package game;

import edu.monash.fit2099.engine.*;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaur extends Dinosaur {
    public Stegosaur(String sex) { super(sex); }}
/*    public Stegosaur() { super(); }}*/

/*    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        super(actions, lastAction, map, display);
        ground = map.locationOf(this).getGround();
        this.graze(ground);
    }

    public void graze(Ground ground) {
        if (ground instanceof Dirt && ground.hasGrass()) {
            ground.removeGrass();
            this.increaseHunger(5); // TODO: Refactor this so that grass increase hunger has constant value
        }
    }*/

/*    @Override
    public void mate(Stegosaur stegosaur) {
        if (this.canMate(stegosaur)) {
            // TODO: stop wandering, then somehow get the female to lay egg
        }
    }

    @Override
    private Egg layEgg() {
        return new StegosaurEgg();
    }
}*/
