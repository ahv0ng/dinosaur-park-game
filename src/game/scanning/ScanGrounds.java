package game.scanning;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.ground.Tree;

import java.util.ArrayList;

/**
 * Extends ScanComponent. Singleton class to inquire about Grounds. Does not
 * interact with the system externally.
 *
 * @author Nicholas Chua and Alden Vong
 */
class ScanGrounds extends ScanComponent {
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
     * @return int representing the number of Tree objects adjacent to location
     */
    public static int getTrees(Location currentLocation) {
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
     * @param location the current Location of the caller
     * @return integer representing the number of grass tiles adjacent to location
     */
    public static int getGrass(Location currentLocation) {
        int numberOfGrass = 0;
        for (Ground ground : getGrounds(currentLocation)) {
            if (isGrass(ground)) {
                numberOfGrass++;
            }
        }
        return numberOfGrass;
    }
}
