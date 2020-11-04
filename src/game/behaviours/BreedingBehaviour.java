package game.behaviours;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.actions.MateAction;
import game.actors.Dinosaur;
import game.actors.Player;
import game.scanning.Scan;

/**
 * A class that figures out an Action (either a MateAction or MoveAction) that will move an acting Actor one tile
 * closer to a target Actor, or breed with a target Actor. Uses the FollowBehaviour class.
 *
 * @author Nicholas Chua & Alden Vong
 */
public class BreedingBehaviour implements Behaviour {

    /**
     * Return an Action such that the acting Actor moves closer to the target Actor,
     * or breeds with the target Actor.
     *
     * @param actor the Actor seeking for a mate
     * @param map the GameMap containing the Actor
     * @return an Action, or null if none are possible
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Dinosaur seekingDinosaur = (Dinosaur) actor;
        if (!this.canBreed(seekingDinosaur)) {
            return null;
        }

        Location seekingLocation = map.locationOf(seekingDinosaur);
        for (Location location : Scan.adjacentLocationsIn3Spaces(map.locationOf(seekingDinosaur))) {
            Actor targetActor = location.getActor();

            // Pre-condition check to avoid attempt to breed with Player
            if (targetActor == null || targetActor instanceof Player) {
                continue;
            }
            Dinosaur targetDinosaur = (Dinosaur) targetActor;
            if (!this.satisfiesMatingConditions(seekingDinosaur, targetDinosaur)) {
                continue;
            }
            else if (Scan.isAdjacent(seekingLocation, location)) {
                return new MateAction(targetDinosaur);
            }
            return new FollowBehaviour(targetDinosaur).getAction(seekingDinosaur, map);
        }

        // No suitable mates in range
        return null;
    }


    /**
     * Evaluate whether the two Dinosaurs have satisfied the mating conditions. This
     * means that the two Dinosaurs are adult, not pregnant, of the same species
     * and of the opposite sex.
     *
     * @param seekingDinosaur - Dinosaur asking to mate
     * @param targetDinosaur - the target Dinosaur
     * @return boolean value evaluating whether the mating conditions have been met
     */
    private boolean satisfiesMatingConditions(Dinosaur seekingDinosaur, Dinosaur targetDinosaur) {
        if (!this.canBreed(targetDinosaur)) {
            return false;
        }
        else if (!this.isSameSpecies(seekingDinosaur, targetDinosaur)) {
            return false;
        }
        else return this.isOppositeSex(seekingDinosaur, targetDinosaur);
    }

    /**
     * Evaluate whether the Dinosaur is able to breed. Return true if Dinosaur
     * is not an adult and/or pregnant.
     *
     * @param dinosaur - the Dinosaur seeking to mate
     * @return boolean value whether Dinosaur is able to breed
     */
    private boolean canBreed(Dinosaur dinosaur) {
        // While this method is always inverted, this way is more readable
        return dinosaur.isAdult() && !dinosaur.isPregnant();
    }

    /**
     * Evaluate whether the two Dinosaurs are of the same species.
     *
     * @param dinosaur1 - Any Dinosaur
     * @param dinosaur2 - Another Dinosaur
     * @return boolean value whether the two Dinosaurs are the same species
     */
    private boolean isSameSpecies(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return dinosaur1.getClass() == dinosaur2.getClass();
    }

    /**
     * Evaluate whether the two Dinosaurs are of the opposite sex.
     *
     * @param dinosaur1 - Any Dinosaur
     * @param dinosaur2 - Another Dinosaur
     * @return boolean value whether the two Dinosaurs are the opposite sex
     */
    private boolean isOppositeSex(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        return !dinosaur1.getSex().equals(dinosaur2.getSex());
    }
}
