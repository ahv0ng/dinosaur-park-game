package game.portables;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Stegosaur;

public class StegosaurEgg extends Egg {
    private int COST = 200;
    static final int POINTS_WHEN_HATCH = 100;

    public StegosaurEgg() {
        super("Stegosaur Egg", 'o');
    }

    public int getCost() { return COST; }

    @Override
    public void hatch(Location location) {
        location.addActor(new Stegosaur());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
