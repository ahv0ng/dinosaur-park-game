package game.scanning;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.items.corpses.Corpse;
import game.actors.Dinosaur;
import game.items.eggs.Egg;

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
     * Given a Location, check if it has a Egg and return.
     *
     * @param currentLocation the Location for which a Egg is checked for
     * @return reference to Egg at location (null if it doesn't exist)
     */
    protected static Egg getEgg(Location currentLocation) {
        Dinosaur dinosaur = (Dinosaur) currentLocation.getActor();
        for (Item item : currentLocation.getItems()) {
            // Prevent returning a non-Egg item that are the same species
            if (canSearchForEgg(item, dinosaur)) {
                return (Egg) item;
            }
        }
        return null;
    }
}
