package game.items.eggs;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Stegosaur;

/**
 * Extends Egg.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class StegosaurEgg extends Egg {
    static final int COST = 200;
    static final int POINTS_WHEN_HATCH = 100;

    /**
     * Constructor for StegosaurEgg.
     */
    public StegosaurEgg() {
        super("Stegosaur Egg", 'o', "Stegosaur");
    }

    @Override
    public void hatch(Location location) {
        location.addActor(new Stegosaur());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
