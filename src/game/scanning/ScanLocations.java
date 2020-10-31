package game.scanning;

import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.ground.Water;
import game.portables.StegosaurEgg;

/**
 * Extends ScanComponent. Singleton class to find the nearest Locations of
 * Grounds, Actors and Items. Does not interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanLocations extends ScanComponent {

    /**
     * If there is a Dirt with grass within 3 tiles of the current Location,
     * return the Location of this Dirt.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of a Dirt with grass
     */
    protected static Location getLocationOfGrass(Location currentLocation) {
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
            if (isGrass(location.getGround())) {
                return location;
            }
        }
        return null;
    }
    /**
     * If there is a Water within 3 tiles of the current Location,
     * return the Location of the Water.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of a Water
     */
    protected static Location getLocationOfWater(Location currentLocation) {
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
            if (location.getGround() instanceof Water) {
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
    protected static Location getLocationOfCorpse(Location currentLocation) {
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
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
    protected static Location getLocationOfStegosaurEgg(Location currentLocation) {
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
            for (Item item : location.getItems()) {
                if (item instanceof StegosaurEgg) {
                    return location;
                }
            }
        }
        return null;
    }
}
