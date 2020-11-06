package game.scanning;

import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.actors.Dinosaur;
import game.ground.Dirt;
import game.items.eggs.Egg;

import java.util.ArrayList;

/**
 * Abstract class that contains all the methods related to scanning for objects.
 * Extend this class into another subclasses if scanning for a different group of
 * classes.
 *
 * @author Nicholas Chua and Alden Vong
 */
abstract class ScanComponent {

    /**
     * Return an array list of all Locations within one tile of a given Location.
     *
     * @param location the current Location of the caller
     * @return an ArrayList of Location objects
     */
    protected static ArrayList<Location> adjacentLocations(Location location) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList;
    }

    /**
     * Return an array list of all Locations within a list of Locations. Accept multiple Locations
     * and prevent duplicate Locations being returned.
     *
     * @param locationsList - ArrayList of Locations to find exits for
     * @return ArrayList of adjacent Locations
     */
    private static ArrayList<Location> adjacentLocationsFromList(ArrayList<Location> locationsList) {
        ArrayList<Location> adjacentLocations = new ArrayList<>();
        for (Location location : locationsList) {
            for (Exit exit : location.getExits()) {
                Location adjacentLocation = exit.getDestination();
                if (!adjacentLocations.contains(adjacentLocation)) {
                    adjacentLocations.add(adjacentLocation);
                }
            }
        }
        return adjacentLocations;
    }

    /**
     * Return an array list of all locations within three tiles of a given Location.
     *
     * @param currentLocation the current Location of the caller
     * @return an ArrayList of Location objects
     */
    protected static ArrayList<Location> adjacentLocationsIn3Spaces(Location currentLocation) {
        ArrayList<Location> locationArrayList = adjacentLocations(currentLocation);
        ArrayList<Location> locationArrayList2 = adjacentLocationsFromList(locationArrayList);
        return adjacentLocationsFromList(locationArrayList2);
    }

    /**
     * Return true if Ground is a Dirt with grass, false otherwise
     *
     * @param ground Ground object being queried
     * @return Boolean for whether ground is a Dirt with grass
     */
    protected static boolean isGrass(Ground ground) {
        if (ground instanceof Dirt) {
            return ((Dirt) ground).hasGrass();
        }
        return false;
    }

    /**
     * Assess whether Dinosaur can scan for this Egg.
     *
     * Check whether the item is an Egg type, and is of a different species
     * from the Dinosaur.
     *
     * @param item - Item that is being scanned for
     * @param dinosaur - Dinosaur that is requesting to scan the item
     * @return boolean value on whether the Dinosaur can scan for the Item
     */
    protected static boolean canSearchForEgg(Item item, Dinosaur dinosaur) {
        if (item instanceof Egg) {
            Egg egg = (Egg) item;
            return !egg.getDinosaurName().equals(dinosaur.getName());
        }
        return false;
    }
}
