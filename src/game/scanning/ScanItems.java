package game.scanning;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.actors.Dinosaur;
import game.portables.Egg;

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
        Dinosaur currentDinosaur = (Dinosaur) currentLocation.getActor();
        for (Item item : currentLocation.getItems()) {
            if (canSearchForEgg(item, currentDinosaur)) {
                return (Egg) item;
            }
        }
        return null;
    }

    /**
     * Assess whether Dinosaur can scan for this Egg. Check whether the
     * item is an Egg type and is of a different species from the
     * Dinosaur.
     *
     * @param item - Item that is being scanned for
     * @param dinosaur - Dinosaur that is requesting to scan the item
     * @return
     */
    private static boolean canSearchForEgg(Item item, Dinosaur dinosaur) {
        if (item instanceof Egg) {
            Egg egg = (Egg) item;
            return egg.getDinosaurName().equals(dinosaur.getName());
        }
        return false;
    }
}
