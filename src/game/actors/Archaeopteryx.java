package game.actors;

import game.portables.ArchaeopteryxEgg;
import game.portables.Egg;

public class Archaeopteryx extends Carnivore {

    public Archaeopteryx() {
        super(true, "Archaeopteryx", 'x');
    }

    @Override
    protected Egg layEgg() { return new ArchaeopteryxEgg(); }
}
