package game.scanning;

import edu.monash.fit2099.engine.Location;
import game.Corpse;
import game.actors.Stegosaur;
import game.portables.StegosaurEgg;

/**
 * Singleton class that contains all methods for scanning Locations, Grounds,
 * Actors and Items. This class acts as the external class that interacts with
 * the rest of the system.
 *
 * If implementing more methods, it should only return the Scan methods and
 * have no internal logic.
 *
 * @author Nicholas Chua & Alden Vong
 */

public class Scan {

    /**
     * Using ScanLocations, return the Location of the nearest Dirt object with
     * grass attribute.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of the nearest Dirt with grass attribute
     */
    public static Location getLocationOfGrass(Location currentLocation) {
        return ScanLocations.getLocationOfGrass(currentLocation);
    }

    /**
     * Using ScanLocations, return the Location of the nearest Corpse.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of the nearest Corpse
     */
    public static Location getLocationOfCorpse(Location currentLocation) {
        return ScanLocations.getLocationOfCorpse(currentLocation);
    }

    /**
     * Using ScanLocations, return the Location of the nearest StegosaurEgg.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of the nearest StegosaurEgg
     */
    public static Location getLocationOfStegosaurEgg(Location currentLocation) {
        return ScanLocations.getLocationOfStegosaurEgg(currentLocation);
    }

    /**
     * Using ScanGrounds, return the number of adjacent Trees.
     *
     * @param currentLocation - current Location of the caller
     * @return integer value of adjacent Trees
     */
    public static int getTrees(Location currentLocation) {
        return ScanGrounds.getTrees(currentLocation);
    }

    /**
     * Using ScanGrounds, return the number of adjacent Dirt with grass attribute.
     *
     * @param currentLocation - current Location of the caller
     * @return integer value of adjacent Dirt objects with grass attribute
     */
    public static int getGrass(Location currentLocation) {
        return ScanGrounds.getGrass(currentLocation);
    }

    /**
     * Using ScanGrounds, return the nearest Stegosaur.
     *
     * @param currentLocation - current Location of the caller
     * @return nearest Stegosaur actor
     */
    public static Stegosaur getStegosaur(Location currentLocation) {
        return ScanActors.getStegosaur(currentLocation);
    }

    /**
     * Using ScanGrounds, return the nearest Corpse.
     *
     * @param currentLocation - current Location of the caller
     * @return nearest Corpse item
     */
    public static Corpse getCorpse(Location currentLocation) {
        return ScanItems.getCorpse(currentLocation);
    }

    /**
     * Using ScanGrounds, return the nearest StegosaurEgg.
     *
     * @param currentLocation - current Location of the caller
     * @return nearest StegosaurEgg item
     */
    public static StegosaurEgg getStegosaurEgg(Location currentLocation) {
        return ScanItems.getStegosaurEgg(currentLocation);
    }
}
