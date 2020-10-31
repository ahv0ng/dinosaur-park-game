package game.behaviours;

import edu.monash.fit2099.engine.*;
import game.actions.MateAction;
import game.actors.Dinosaur;
import game.scanning.Scan;

import java.util.ArrayList;

/**
 * A class that figures out an Action (either a MateAction or MoveAction) that will move an acting Actor one tile
 * closer to a target Actor, or breed with a target Actor. Uses the FollowBehaviour class.
 *
 * @author Nicholas Chua & Alden Vong
 */
public class BreedingBehaviour implements Behaviour {
    /**
     * Returns an Action such that the acting Actor moves closer to the target Actor, or breeds with the target Actor.
     *
     * @param actor the Actor looking for a mate
     * @param map the GameMap containing the Actor
     * @return an Action, or null if none are possible
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
        for (Location loc : Scan.adjacentLocationsIn3Spaces(map.locationOf(actor))) {
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
     * Evaluates whether two given dinosaurs are of the same species.
     *
     * @param dinosaur1 some Dinosaur
     * @param dinosaur2 some Dinosaur
     * @return Boolean value
     */
    private Boolean sameSpecies(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return dinosaur1.getClass().equals(dinosaur2.getClass());
    }
    /**
     * Evaluates whether target is in an adjacent tile to a given location
     *
     * @param map the GameMap containing target
     * @param location the location for which adjacent tiles are checked for target
     * @param target Actor being looked for
     * @return Boolean value
     */
    // TODO: Decide if this should be in a scanning package class or not
    private Boolean adjacentActor(GameMap map, Location location, Actor target) {
        ArrayList<Location> locationArrayList = new ArrayList<>();
        for (Exit exit : location.getExits()) {
            locationArrayList.add(exit.getDestination());
        }
        return locationArrayList.contains(map.locationOf(target));
    }
    /**
     * Evaluates whether dinosaur2 is a suitable mate for dinosaur1 (i.e. same species, opposite sex and not already pregnant).
     *
     * @param dinosaur1 Dinosaur looking for a mate
     * @param dinosaur2 the potential mate for dinosaur1
     * @return Boolean value
     */
    private Boolean suitableMate(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return (sameSpecies(dinosaur1, dinosaur2) && dinosaur1.isOppositeSex(dinosaur2)
                && !(dinosaur2.isPregnant()));
    }
}
