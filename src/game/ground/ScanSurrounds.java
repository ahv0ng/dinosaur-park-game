package game.ground;

import edu.monash.fit2099.engine.*;
import game.Corpse;
import game.actors.Stegosaur;
import game.portables.StegosaurEgg;

import java.util.ArrayList;

/**
 * A class containing all the methods that involve querying the surroundings of a given object in the game.
 *
 * @author Nicholas Chua & Alden Vong
 */
public class ScanSurrounds {

    /**
     * Returns array list of all locations within one tile of a given Location.
     *
     * @param location the Location of interest
     * @return an ArrayList of Location objects
     */
    public static ArrayList<Location> getLocationsWithin1(Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList;
    }

    /**
     * Returns array list of all Ground objects within one tile of a given Location.
     *
     * @param location the Location of interest
     * @return an ArrayList of Ground objects
     */
    public static ArrayList<Ground> getGroundsWithin1(Location location) {
        ArrayList<Ground> groundArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            groundArrayList.add(exit.getDestination().getGround());
        }
        return groundArrayList;
    }

    /**
     * Returns array list of all locations within three tiles of a given Location.
     *
     * @param location the Location of interest
     * @return an ArrayList of Location objects
     */
    public static ArrayList<Location> getLocationsWithin3(Location location) {
        ArrayList<Location> locationArrayList = getLocationsWithin1(location);
        ArrayList<Location> locationArrayList2 = new ArrayList<>();
        for (Location loc : locationArrayList ) {
            for (Exit exit : loc.getExits()) {
                if (!locationArrayList2.contains(exit.getDestination())) {
                    locationArrayList2.add(exit.getDestination());
                }
            }
        }
        ArrayList<Location> locationArrayList3 = new ArrayList<>();
        for (Location loc : locationArrayList2 ) {
            for (Exit exit : loc.getExits()) {
                if (!locationArrayList3.contains(exit.getDestination())) {
                    locationArrayList3.add(exit.getDestination());
                }
            }
        }
        return locationArrayList3;
    }

    /**
     * Returns the number of Tree objects adjacent to a given Location.
     *
     * @param location the Location of interest
     * @return int representing the number of Tree objects adjacent to location
     */
    public static int adjacentTrees(Location location) {
        ArrayList<Ground> groundArrayList = getGroundsWithin1(location);
        int numberOfTrees = 0;

        for (Ground ground : groundArrayList) {
            if (isTree(ground)) {
                numberOfTrees++;
            }
        }
        return numberOfTrees;
    }

    /**
     * Returns true if Ground is a Tree, false otherwise.
     *
     * @param ground Ground object being queried
     * @return Boolean for whether ground is a Tree
     */
    private static boolean isTree(Ground ground) {
        return ground instanceof Tree;
    }

    /**
     * Returns the number of grass tiles adjacent to a given Location.
     *
     * @param location the Location of interest
     * @return int representing the number of grass tiles adjacent to location
     */
    public static int adjacentGrass(Location location) {
        ArrayList<Ground> groundArrayList = getGroundsWithin1(location);
        int numberOfGrass = 0;

        for (Ground ground : groundArrayList) {
            if (isGrass(ground)) {
                numberOfGrass++;
            }
        }
        return numberOfGrass;
    }

    /**
     * Returns true if Ground is a Dirt with grass, false otherwise
     *
     * @param ground Ground object being queried
     * @return Boolean for whether ground is a Dirt with grass
     */
    private static boolean isGrass(Ground ground) {
        if (ground instanceof Dirt) {
            Dirt dirt = (Dirt) ground; // Resolves issue where ground doesn't have hasGrass(), there must be a better way
            return dirt.hasGrass();
        }
        return false;
    }

    /**
     * If there is a Stegosaur within 3 tiles of a given Location, the Stegosaur is returned.
     *
     * @param location the Location of interest
     * @return reference to Stegosaur (null if doesn't exist)
     */
    public static Stegosaur getStegosaur(Location location) {
        ArrayList<Location> locationArrayList = ScanSurrounds.getLocationsWithin3(location);
        for (Location loc : locationArrayList) {
            if (loc.getActor() != null && loc.getActor() instanceof Stegosaur) {
                return (Stegosaur) loc.getActor();
            }
        }
        return null;
    }

    /**
     * If there is a Dirt with grass within 3 tiles of a given Location, the Location of
     * the Dirt object is returned.
     *
     * @param location the Location of interest
     * @return reference to Location of Dirt with grass (null if doesn't exist)
     */
    public static Location getGrass(Location location) {
        for (Location loc : ScanSurrounds.getLocationsWithin3(location)) {
            if (loc.getGround() instanceof Dirt && ((Dirt) loc.getGround()).hasGrass()) {
                return loc;
            }
        }
        return null;
    }

    /**
     * Returns a Location with a Corpse that is within 3 tiles of a given Location (if it exists).
     *
     * @param location the Location of interest
     * @return reference to Location with a Corpse (null if doesn't exist)
     */
    public static Location getLocationOfCorpse(Location location) {
        for (Location loc : ScanSurrounds.getLocationsWithin3(location)) {
            for (Item item : loc.getItems()) {
                if (item instanceof Corpse) {
                    return loc;
                }
            }
        }
        return null;
    }

    /**
     * If a given Location contains a Corpse, the Corpse object is returned.
     *
     * @param location the Location for which a Corpse is checked for
     * @return reference to Corpse at location (null if it doesn't exist)
     */
    public static Item getCorpse(Location location) {
        for (Item item : location.getItems()) {
            if (item instanceof Corpse) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns a Location that is within 3 tiles of a given Location and contains a StegosaurEgg.
     *
     * @param location the Location of interest
     * @return reference to a Location with StegosaurEgg
     */
    public static Location getGroundOfStegosaurEgg(Location location) {
        for (Location loc : ScanSurrounds.getLocationsWithin3(location)) {
            for (Item item : loc.getItems()) {
                if (item instanceof StegosaurEgg) {
                    return loc;
                }
            }
        }
        return null;
    }

    /**
     * If a given Location contains a StegosaurEgg, the StegosaurEgg object is returned.
     *
     * @param location the Location for which a StegosaurEgg is checked for
     * @return reference to StegosaurEgg at location (null if it doesn't exist)
     */
    public static Item getStegosaurEgg(Location location) {
        for (Item item : location.getItems()) {
            if (item instanceof StegosaurEgg) {
                return item;
            }
        }
        return null;
    }
}
