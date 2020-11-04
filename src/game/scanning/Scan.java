package game.scanning;

import edu.monash.fit2099.engine.Location;
import game.ground.Water;
import game.items.corpses.Corpse;
import game.actors.Dinosaur;
import game.items.eggs.Egg;

import java.util.ArrayList;

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
     * Using the base class ScanComponent, return the adjacent Locations within
     * three spaces of the currentLocation.
     *
     * @param currentLocation - current Location of the caller
     * @return ArrayList of the adjacent Locations within three spaces
     */
    public static ArrayList<Location> adjacentLocationsIn3Spaces(Location currentLocation) {
        return ScanLocations.adjacentLocationsIn3Spaces(currentLocation);
    }
    // TODO: Check with Nick that location of this in ScanComponent is correct
    /**
     * Using the base class ScanComponent, return true if the two given locations
     * are adjacent (difference in x and y coordinates are not greater than 1),
     * false otherwise.
     *
     * @param location1 - a Location
     * @param location2 - a Location
     * @return boolean value whether the location1 and location2 are adjacent
     */
    public static boolean isAdjacent(Location location1, Location location2) {
        int xDifference = Math.abs(location1.x() - location2.x());
        int yDifference = Math.abs(location1.y() - location2.y());
        return xDifference <= 1 && yDifference <= 1;
    }

    /**
     * Using ScanLocations, return the Location of a Dirt with
     * grass attribute set to true.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of a Dirt with grass attribute set to true
     */
    public static Location getLocationOfGrass(Location currentLocation) {
        return ScanLocations.getLocationOfGrass(currentLocation);
    }

    /**
     * Using ScanLocations, return the Location of a Water.
     *
     * @param currentLocation - current Location of the caller
     * @return Location of a Water
     */
    public static Location getLocationOfWater(Location currentLocation) {
        return ScanLocations.getLocationOfWater(currentLocation);
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
    public static Location getLocationOfEgg(Location currentLocation) {
        return ScanLocations.getLocationOfEgg(currentLocation);
    }

    /**
     * Using ScanGrounds, return the number of adjacent Trees.
     *
     * @param currentLocation - current Location of the caller
     * @return integer value of adjacent Trees
     */
    public static int getNumberOfTrees(Location currentLocation) {
        return ScanGrounds.getNumberOfTrees(currentLocation);
    }

    /**
     * Using ScanGrounds, return the number of adjacent Dirt with grass attribute.
     *
     * @param currentLocation - current Location of the caller
     * @return integer value of adjacent Dirt objects with grass attribute
     */
    public static int getNumberOfGrass(Location currentLocation) {
        return ScanGrounds.getNumberOfGrass(currentLocation);
    }

    /**
     * Using ScanGrounds, return a Water adjacent to a given Location (if it exists)
     *
     * @param currentLocation - the current Location of the caller
     * @return a Water adjacent to currentLocation (if it exists)
     */
    public static Water adjacentWater(Location currentLocation) { return ScanGrounds.adjacentWater(currentLocation); }

    /**
     * Using ScanActors, return the nearest Dinosaur that is not the same species.
     *
     * @param currentLocation - the current Location of the caller
     * @return nearest Dinosaur
     */
    public static Dinosaur getOtherSpeciesDinosaur(Location currentLocation) {
        return ScanActors.getOtherSpeciesDinosaur(currentLocation);
    }

    /**
     * Using ScanItems, return the nearest Corpse.
     *
     * @param currentLocation - current Location of the caller
     * @return nearest Corpse item
     */
    public static Corpse getCorpse(Location currentLocation) {
        return ScanItems.getCorpse(currentLocation);
    }

    /**
     * Using ScanItems, return the nearest Egg.
     *
     * @param currentLocation - current Location of the caller
     * @return nearest Egg item
     */
    public static Egg getEgg(Location currentLocation) {
        return ScanItems.getEgg(currentLocation);
    }
}
