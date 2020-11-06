package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;

/**
 * Special action for Dinosaurs to mate. Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class MateAction extends Action {
    Dinosaur mate;

    /**
     * Constructor for MateAction.
     *
     * @param dinosaur - Chosen Dinosaur to mate with
     */
    public MateAction(Dinosaur dinosaur) {
        this.mate = dinosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        this.returnFemale((Dinosaur) actor, mate).mate();
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {return actor + " mates with " + mate; }

    /**
     * Evaluate which of the two Dinosaurs mating are female.
     *
     * @param dinosaur1 - Dinosaur object of any sex
     * @param dinosaur2 - Dinosaur object of any sex
     * @return reference to the Dinosaur that is female
     */
    private Dinosaur returnFemale(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        if (dinosaur1.getSex().equals("Female")) {
            return dinosaur1;
        }
        return dinosaur2;
    }
}
