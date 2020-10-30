package game.scanning;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.actors.Stegosaur;

/**
 * Extends ScanComponent. Singleton class to get the nearest Actor. Does not
 * interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanActors extends ScanComponent {

    /**
     * If there is a Stegosaur within 3 tiles of a given Location, return Stegosaur.
     *
     * @param currentLocation the current Location of the caller
     * @return reference to Stegosaur (null if doesn't exist)
     */
    protected static Stegosaur getStegosaur(Location currentLocation) {
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
            Actor actor = location.getActor();
            if (actor instanceof Stegosaur) {
                return (Stegosaur) actor;
            }
        }
        return null;
    }
}
