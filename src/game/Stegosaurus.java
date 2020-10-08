package game;

/**
 * A herbivorous dinosaur.
 *
 */
public class Stegosaurus extends Dinosaur {
    public Stegosaurus(String sex) { super(sex); }
    public Stegosaurus() { super(); }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        super(actions, lastAction, map, display);
        ground = map.locationOf(this).getGround();
        this.graze(ground);
    }

    public void graze(Ground ground) {
        if (ground instanceOf Dirt && ground.hasGrass()) {
            ground.removeGrass();
            this.increaseHunger(5); // TODO: Refactor this so that grass increase hunger has constant value
        }
    }

    @Override
    public void mate(Stegosaurus stegosaurus) {
        if (this.canMate(stegosaurus)) {
            // TODO: stop wandering, then somehow get the female to lay egg
        }
    }

    @Override
    private Egg layEgg() {
        return new StegosaurusEgg();
    }
}
