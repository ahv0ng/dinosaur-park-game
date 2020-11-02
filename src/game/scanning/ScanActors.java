package game.scanning;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Location;
import game.actors.Dinosaur;
import game.actors.Player;

/**
 * Extends ScanComponent. Singleton class to get the nearest Actor. Does not
 * interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanActors extends ScanComponent {

    /**
     * If there is a Dinosaur within 3 tiles of the caller's Location, and the
     * Dinosaur is a different species as the caller, return Dinosaur. Should
     * not return an instance of Player.
     *
     * @param currentLocation - the current Location of the caller
     * @return reference of the Dinosaur
     */
    protected static Dinosaur getOtherSpeciesDinosaur(Location currentLocation) {
        Dinosaur currentDinosaur = (Dinosaur) currentLocation.getActor();
        for (Location location : adjacentLocationsIn3Spaces(currentLocation)) {
            Actor actor = location.getActor();
            if (actor instanceof Player) {
                // Avoid searching for Player
                continue;
            }
            // Prevent searching for same species
            else if (actor.getClass() != currentDinosaur.getClass()) {
                return (Dinosaur) actor;
            }
        }
        return null;
    }
}
