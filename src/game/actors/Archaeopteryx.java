package game.actors;

import game.portables.ArchaeopteryxEgg;
import game.portables.Egg;

public class Archaeopteryx extends Carnivore {

    public Archaeopteryx() {
        super("Archaeopteryx", 'x');
        this.canFly = true;
    }

    @Override
    protected Egg layEgg() { return new ArchaeopteryxEgg(); }
}
