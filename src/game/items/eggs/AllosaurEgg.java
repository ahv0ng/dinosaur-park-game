package game.items.eggs;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Allosaur;

/**
 * Extends Egg.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class AllosaurEgg extends Egg {
    static final int COST = 1000;
    static final int POINTS_WHEN_HATCH = 1000;

    /**
     * Constructor for AllosaurEgg.
     */
    public AllosaurEgg() {
        super("Allosaur Egg", '0', "Allosaur");
    }

    @Override
    public void hatch(Location location) {
        location.addActor(new Allosaur());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
