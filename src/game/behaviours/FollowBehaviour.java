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
	 * Constructor when an Actor object is passed.
	 *
	 * @param actor the Actor to follow
	 */
	public FollowBehaviour(Actor actor) {
		this.targetActor = actor;
	}

	/**
	 * Constructor when a Location object is passed.
	 *
	 * @param location the Location to move towards
	 */
	public FollowBehaviour(Location location) { this.targetLocation = location;}

	/**
	 * Return a MoveAction to get closer to a target Actor. If no movement is
	 * possible, return null.
	 *
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		Location actorLocation = map.locationOf(actor);
		Location targetLocation;

		if (map.contains(this.targetActor)) {
			targetLocation = map.locationOf(this.targetActor);
		}
		else {
			targetLocation = this.targetLocation;
		}

		int currentDistance = this.calculateDistance(actorLocation, targetLocation);
		for (Exit exit : actorLocation.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = calculateDistance(destination, targetLocation);

				// Compare the new distance whether it is closer to target
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
	 * @param a the caller's location
	 * @param b the target's location
	 * @return the number of steps between a and b if you only move in the four cardinal directions.
	 */
	private int calculateDistance(Location a, Location b) {
		return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
	}
}