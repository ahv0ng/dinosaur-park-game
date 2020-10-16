package game.behaviours;

import edu.monash.fit2099.engine.*;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor/Location.
 *
 * @author Nicholas Chua & Alden Vong
 */
public class FollowBehaviour implements Behaviour {

	private Actor targetActor;
	private Location targetLocation;

	/**
	 * Constructor.
	 *
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.targetActor = subject;
	}

	/**
	 * Constructor.
	 *
	 * @param subject the Location to move towards
	 */
	public FollowBehaviour(Location subject) { this.targetLocation = subject;}

	/**
	 * Returns a MoveAction to get closer to a target Actor.
	 * If no movement is possible, returns null.
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(!map.contains(targetActor) || !map.contains(actor))
			return null;

		Location here = map.locationOf(actor);
		Location there = map.locationOf(targetActor);

		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		return null;
	}

	/**
	 * Returns a MoveAction to get closer to a target Location.
	 * If no movement is possible, returns null.
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return an Action, or null if no MoveAction is possible
	 */
	public Action getFollowLocationAction(Actor actor, GameMap map) {
		Location here = map.locationOf(actor);
		Location there = this.targetLocation;

		int currentDistance = distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		return null;
	}
	/**
	 * Compute the Manhattan distance between two locations.
	 *
	 * @param a the first location
	 * @param b the first location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int distance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}