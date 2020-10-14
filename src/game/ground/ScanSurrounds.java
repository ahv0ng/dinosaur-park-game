package game.ground;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

import java.util.ArrayList;

public class ScanSurrounds {
    public static ArrayList<Ground> querySurroundings(Location location) {
        ArrayList<Ground> groundArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            groundArrayList.add(exit.getDestination().getGround());
        }
        return groundArrayList;
    }

    /**
     * Returns an ArrayList of all locations within three tiles of the given location.
     * @param map
     * @param location
     * @return
     */
    public static ArrayList<Location> getSurroundingLocations(GameMap map, Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
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
        ArrayList<Ground> groundArrayList = querySurroundings(location);
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
        ArrayList<Ground> groundArrayList = querySurroundings(location);
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
}
