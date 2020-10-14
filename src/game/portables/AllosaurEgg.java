package game.portables;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Allosaur;

public class AllosaurEgg extends Egg {
    static final int COST = 1000;
    static final int POINTS_WHEN_HATCH = 1000;

    public AllosaurEgg() {
        super("Allosaur Egg", 'o');
    }

    public int getCost() { return COST; }

    public void hatch(Location location) {
        location.addActor(new Allosaur());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
