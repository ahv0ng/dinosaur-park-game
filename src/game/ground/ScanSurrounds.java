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
