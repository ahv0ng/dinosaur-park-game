package game.scanning;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.portables.StegosaurEgg;

/**
 * Extends ScanComponent. Singleton class to find the nearest Locations of
 * Grounds, Actors and Items. Does not interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class ScanLocations extends ScanComponent {

    /**
     * If there is a Dirt with grass within 3 tiles of the current Location,
     * return the Location of the nearest Dirt with grass.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of the nearest Dirt with grass
     */
    public static Location getLocationOfGrass(Location currentLocation) {
        for (Location location : adjacentLocationsIn3(currentLocation)) {
            if (isGrass(location.getGround())) {
                return location;
            }
        }
        return null;
    }

    /**
     * If there is a Corpse within 3 tiles of the current Location, return
     * the Location of the nearest Corpse.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of the nearest Corpse
     */
    public static Location getLocationOfCorpse(Location currentLocation) {
        for (Location location : adjacentLocationsIn3(currentLocation)) {
            for (Item item : location.getItems()) {
                if (item instanceof Corpse) {
                    return location;
                }
            }
        }
        return null;
    }

    /**
     * If there is a StegosaurEgg within 3 tiles of the current Location,
     * return the Location of the nearest StegosaurEgg.
     *
     * @param currentLocation the current Location of the caller
     * @return reference to a Location with StegosaurEgg
     */
    public static Location getLocationOfStegosaurEgg(Location currentLocation) {
        for (Location location : adjacentLocationsIn3(currentLocation)) {
            for (Item item : location.getItems()) {
                if (item instanceof StegosaurEgg) {
                    return location;
                }
            }
        }
        return null;
    }
}
