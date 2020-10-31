package game.items.eggs;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Agilisaur;

/**
 * Extends Egg.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class AgilisaurEgg extends Egg {
    static final int COST = 750;
    static final int POINTS_WHEN_HATCH = 750;

    /**
     * Constructor for AgilisaurEgg.
     */
    public AgilisaurEgg() {
        super("Agilisaur Egg", '6', "Agilisaur");
    }

    @Override
    public void hatch(Location location) {
        location.addActor(new Agilisaur());
        location.removeItem(this);
        EcoPointsSystem.earn(POINTS_WHEN_HATCH);
    }
}
