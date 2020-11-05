package game.actions;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.actors.Dinosaur;
import game.ground.Water;

public class DrinkAction {
    private Ground target;

    public DrinkAction(Ground ground) {
        this.target = ground;
    }
    public String execute(Actor actor, GameMap map) {
        Dinosaur dinosaur = (Dinosaur) actor;
        Water water = (Water) this.target;
        dinosaur.increaseThirstPoints(water.getFill());
        return menuDescription(actor);
    }
    public String menuDescription(Actor actor) {
        return actor + " drank water ";
    }
}
