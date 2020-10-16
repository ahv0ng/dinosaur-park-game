package game.ground;

import edu.monash.fit2099.engine.*;
import game.Corpse;
import game.actors.Stegosaur;
import game.portables.StegosaurEgg;

import java.util.ArrayList;

public class ScanSurrounds {
    public static ArrayList<Location> getLocationsWithin1(Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList;
    }

    public static ArrayList<Ground> getGroundsWithin1(Location location) {
        ArrayList<Ground> groundArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            groundArrayList.add(exit.getDestination().getGround());
        }
        return groundArrayList;
    }

    /**
     * Returns an ArrayList of all locations within three tiles of the given location.
     * @param location
     * @return
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

    public static int adjacentTrees(Location location) {
        ArrayList<Ground> groundArrayList = getGroundsWithin1(location);
        int numberOfTrees = 0;

        for (Ground ground : groundArrayList) {
            if (isTree(ground)) {
                numberOfTrees++;
            }
        }
        return numberOfTrees;
        /*
        FIXME: Since grass will grow if there is one adjacent tree,
        there must be a more efficient way to return this function
        without needing to iterate the whole array.
        */
    }

    private static boolean isTree(Ground ground) {
        return ground instanceof Tree;
    }

    public static int adjacentGrass(Location location) {
        ArrayList<Ground> groundArrayList = getGroundsWithin1(location);
        int numberOfGrass = 0;

        for (Ground ground : groundArrayList) {
            if (isGrass(ground)) {
                numberOfGrass++;
            }
        }
        return numberOfGrass;
        /*
        FIXME: Since grass will grow if there are at least two grass blocks,
        there must be a more efficient way to return this function
        without needing to iterate the whole array.
        */
    }

    private static boolean isGrass(Ground ground) {
        if (ground instanceof Dirt) {
            Dirt dirt = (Dirt) ground; // Resolves issue where ground doesn't have hasGrass(), there must be a better way
            return dirt.hasGrass();
        }
        return false;
    }
    /**
     *
     * @param location
     * @return reference to Stegosaur within 3 tiles of given location (if it exists)
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
    private Stegosaur adjacentStegosaur(Location location) {
        ArrayList<Location> locationArrayList = ScanSurrounds.getLocationsWithin1(location);
        for (Location loc : locationArrayList) {
            if (loc.getActor() != null && loc.getActor() instanceof Stegosaur) {
                return (Stegosaur) loc.getActor();
            }
        }
        return null;
    }
    /**
     * Returns a reference to a Dirt object with grass attribute set to True (if it exists within
     * 3 tiles of a given location).
     * @param location
     * @return
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
     * Returns a reference to a Ground object with a corpse (if it exists within 3 tiles
     * of a given location).
     * @param location
     * @return
     */
    public static Location getGroundOfCorpse(Location location) {
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
     * Returns a reference to a Corpse object at a given location (if it exists), null otherwise
     * @param location
     * @return
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
     * Returns a reference to a Ground object with a stegosaurus egg (if it exists within 3 tiles
     * of a given location).
     * @param location
     * @return
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
     * Returns a reference to a StegosaurEgg object at a given location (if it exists), null otherwise
     * @param location
     * @return
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
