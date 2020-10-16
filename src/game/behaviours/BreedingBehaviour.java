package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.MateAction;
import game.actors.Dinosaur;
import game.ground.ScanSurrounds;

import java.util.ArrayList;

public class BreedingBehaviour implements Behaviour {
    /**
     * Uses FollowBehaviour. Either the actors move closer to each other, or they breed.
     * @param actor the Actor looking for a mate
     * @param map the GameMap containing the Actor
     * @return
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        // Baby dinosaurs cannot breed
        if (!(((Dinosaur) actor).isAdult())) {
            return null;
        }
        // Pregnant dinosaurs cannot breed until egg has been laid
        if (((Dinosaur) actor).isPregnant()) {
            return null;
        }
        // Check if there is a suitable mate in range. If next to one, mate. If not next to one but
        // one is in range, then move towards them.
        for (Location loc : ScanSurrounds.getLocationsWithin3(map.locationOf(actor))) {
            if (map.getActorAt(loc) != null && suitableMate((Dinosaur) actor, (Dinosaur) map.getActorAt(loc))) {
                Dinosaur potentialMate = (Dinosaur) map.getActorAt(loc);
                if (adjacentActor(map, map.locationOf(actor), potentialMate)) {
                    return new MateAction(potentialMate);
                }
                return new FollowBehaviour(potentialMate).getAction(actor, map);
                }
            }
        // No suitable mates in range
        return null;
    }
    /**
     * @param dinosaur1
     * @param dinosaur2
     * @return true if both dinosaurs are of the same species, false otherwise
     */
    private Boolean sameSpecies(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return dinosaur1.getClass().equals(dinosaur2.getClass());
    }
    /**
     * @param map
     * @param location
     * @param target
     * @return true if target Actor is in an adjacent tile to a given location, False otherwise
     */
    private Boolean adjacentActor(GameMap map, Location location, Actor target) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList.contains(map.locationOf(target));
    }
    /**
     * If a null value is passed into either argument, method will return false.
     * @param dinosaur1
     * @param dinosaur2
     * @return true if dinosaur2 is a suitable mate for dinosaur1 (i.e. same species, opposite sex and not already pregnant)
     */
    private Boolean suitableMate(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return (sameSpecies(dinosaur1, dinosaur2) && dinosaur1.isOppositeSex(dinosaur2)
                && !(dinosaur2.isPregnant()));
    }
}
