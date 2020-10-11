package game.ground;

import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class ScanSurrounds {
    public static Ground[] querySurroundings(Location location) {
        GameMap map = location.map();
        int x = location.x();
        int y = location.y();

        Ground north = map.at(x, y-1).getGround();
        Ground east = map.at(x+1, y).getGround();
        Ground south = map.at(x, y+1).getGround();
        Ground west = map.at(x-1, y).getGround();

        return new Ground[] {north, east, south, west};
    }

    public static int adjacentTrees(Location location) {
        Ground[] groundList = querySurroundings(location);
        int numberOfTrees = 0;

        for (Ground ground : groundList) {
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
        Ground[] groundList = querySurroundings(location);
        int numberOfGrass = 0;

        for (Ground ground : groundList) {
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
