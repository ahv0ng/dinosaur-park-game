package game.scanning;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.portables.StegosaurEgg;

/**
 * Extends ScanComponent. Singleton class to get the nearest Item. Does not
 * interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanItems extends ScanComponent {

    /**
     * Given a Location, check if it has a Corpse and return.
     *
     * @param currentLocation the Location for which a Corpse is checked for
     * @return reference to Corpse at location (null if it doesn't exist)
     */
    protected static Corpse getCorpse(Location currentLocation) {
        for (Item item : currentLocation.getItems()) {
            if (item instanceof Corpse) {
                return (Corpse) item;
            }
        }
        return null;
    }

    /**
     * Given a Location, check if it has a StegosaurEgg and return.
     *
     * @param currentLocation the Location for which a StegosaurEgg is checked for
     * @return reference to StegosaurEgg at location (null if it doesn't exist)
     */
    protected static StegosaurEgg getStegosaurEgg(Location currentLocation) {
        for (Item item : currentLocation.getItems()) {
            if (item instanceof StegosaurEgg) {
                return (StegosaurEgg) item;
            }
        }
        return null;
    }
}
