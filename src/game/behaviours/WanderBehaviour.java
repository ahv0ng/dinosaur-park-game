package game.behaviours;

import edu.monash.fit2099.engine.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class that figures out a MoveAction that will move the actor to a random adjacent Location.
 *
 * @author FIT2099 Teaching Team
 */
public class WanderBehaviour implements Behaviour {
	
	private final Random random = new Random();

	/**
	 * Return a MoveAction to wander to a random Location, if possible.
	 * If no movement is possible, return null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the GameMap that actor is currently on
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		ArrayList<Action> actions = new ArrayList<>();
		
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
            	actions.add(destination.getMoveAction(actor, "around", exit.getHotKey()));
            }
        }
		
		if (!actions.isEmpty()) {
			return actions.get(random.nextInt(actions.size()));
		}
		else {
			return null;
		}

	}

}
