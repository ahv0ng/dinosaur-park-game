package game;

public class Allosaurus extends Dinosaur {
    public Allosaurus() { super(); }

    public AttackAction attack(Stegosaurus stegosaurus) {
        return new AttackAction(stegosaurus);
    }

    public void eat(Stegosaurus stegosaurus) {
        stegosaurus.die();
    }

    @Override
    public void mate(Allosaurus allosaurus) {
        if (this.canMate(allosaurus)) {
            // TODO: stop wandering, then somehow get the female to lay egg
        }
    }

    @Override
    private Egg layEgg() {
        return new AllosaurusEgg();
    }
}