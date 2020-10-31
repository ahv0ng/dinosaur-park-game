package game.items.eggs;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Archaeopteryx;

/**
 * Extends Egg.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class ArchaeopteryxEgg extends Egg {
    static final int COST = 1000;
    static final int POINTS_WHEN_HATCH = 1000;

    /**
     * Constructor for ArchaeopteryxEgg.
     */
    public ArchaeopteryxEgg() {
        super("Archaeopteryx Egg", '0', "Archaeopteryx");
    }

    @Override
    public void hatch(Location location) {
        location.addActor(new Archaeopteryx());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
