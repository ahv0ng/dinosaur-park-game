package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;

public class MateAction extends Action {
    Dinosaur mate;

    public MateAction(Dinosaur dinosaur) {
        this.mate = dinosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        returnFemale((Dinosaur) actor, mate).mate();
        return menuDescription(actor);
    }
    @Override
    public String menuDescription(Actor actor) {return actor + " mates with " + mate; }

    /**
     * Takes in two dinosaurs of opposite sex
     * @param dinosaur1
     * @param dinosaur2
     * @return reference to the Dinosaur that is female
     */
    private Dinosaur returnFemale(Dinosaur dinosaur1, Dinosaur dinosaur2) {
        if (dinosaur1.getSex().equals("Female")) {
            return dinosaur1;
        }
        return dinosaur2;
    }
}
