package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Special Action to quit the game. Extends Action.
 * @author Nicholas Chua and Alden Vong
 */
public class QuitPlayerAction extends Action {
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        return "Player has left the game.";
    }

    @Override
    public String menuDescription(Actor actor) {
        return "Player quits the game";
    }
}
