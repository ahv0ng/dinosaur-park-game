package game.portables;

import edu.monash.fit2099.engine.Location;
import game.actors.Stegosaur;

public class StegosaurEgg extends Egg {
    private int COST = 200;
    static final int POINTS_WHEN_HATCH = 100;

    public StegosaurEgg() {
        super("stegoegg", 'o');
    }

    public int getCost() { return COST; }

    @Override
    public void hatch(Location location) {
        // TODO: Add to eco points
        location.addActor(new Stegosaur());
    }
}
