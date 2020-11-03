package game.scanning;

import edu.monash.fit2099.engine.*;
import game.ground.Tree;
import game.ground.Water;

import java.util.ArrayList;

/**
 * Extends ScanComponent. Singleton class to inquire about Ground objects. Does not
 * interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanGrounds extends ScanComponent {
    /**
     * Helper method to return all Ground objects adjacent to a given Location. Called by other
     * methods to inquire about instantiations of specific child classes of Ground.
     *
     * @param currentLocation the current Location of the caller
     * @return ArrayList storing all the Ground objects adjacent to currentLocation
     */
    private static ArrayList<Ground> getGrounds(Location currentLocation) {
        ArrayList<Ground> groundArrayList = new ArrayList<>();
        for (Location location : adjacentLocations(currentLocation)) {
            groundArrayList.add(location.getGround());
        }
        return groundArrayList;
    }

    /**
     * Return the number of Tree objects adjacent to a given Location.
     *
     * @param currentLocation the current Location of the caller
     * @return int representing the number of Tree objects adjacent to currentLocation
     */
    protected static int getNumberOfTrees(Location currentLocation) {
        int numberOfTrees = 0;
        for (Ground ground : getGrounds(currentLocation)) {
            if (ground instanceof Tree) { // Extracting condition into a helper method will be unnecessary
                numberOfTrees++;
            }
        }
        return numberOfTrees;
    }

    /**
     * Return the number of grass tiles adjacent to a given Location.
     *
     * @param currentLocation the current Location of the caller
     * @return integer representing the number of grass tiles adjacent to currentLocation
     */
    protected static int getNumberOfGrass(Location currentLocation) {
        int numberOfGrass = 0;
        for (Ground ground : getGrounds(currentLocation)) {
            if (isGrass(ground)) {
                numberOfGrass++;
            }
        }
        return numberOfGrass;
    }

    /**
     * If there is a Water adjacent to a given Location, returns the Water.
     *
     * @param currentLocation the current Location of the caller
     * @return a Water adjacent to currentLocation, null otherwise
     */
    protected static Water adjacentWater(Location currentLocation) {
        ArrayList<Location> locationArrayList = ScanComponent.adjacentLocations(currentLocation);
        for (Location location : locationArrayList) {
            if (location.getGround() instanceof Water) {
                return (Water) location.getGround();
            }
        }
        return null;
    }
}
