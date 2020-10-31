package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.actors.Dinosaur;
import game.items.foods.Food;

/**
 * Special action to feed a dinosaur. Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class FeedAction extends Action {
    private Food food;
    private Dinosaur target;

    /**
     * Constructor for FeedAction. Configure the Food object to feed to which Dinosaur.
     *
     * @param food - Food item to use
     * @param dinosaur - Dinosaur to feed
     */
    public FeedAction(Food food, Dinosaur dinosaur) {
        this.food = food;
        this.target = dinosaur;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        this.food.feed(target);
        actor.removeItemFromInventory(food);
        return System.lineSeparator() + target + " is fed " + food + ".";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " feeds " + target + " " + food;
    }
}
