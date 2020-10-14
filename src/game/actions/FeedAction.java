package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;
import game.portables.Food;

public class FeedAction extends Action {
    private Food food;
    private Dinosaur target;

    public FeedAction(Food food, Dinosaur dinosaur) {
        this.food = food;
        this.target = dinosaur;
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        this.food.feed(target);
        return System.lineSeparator() + target + " is fed " + food + ".";
    }
    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + target + " " + food;
    }
}
