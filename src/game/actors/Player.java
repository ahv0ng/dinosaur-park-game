package game.actors;

import edu.monash.fit2099.engine.*;
import game.EcoPointsSystem;
import game.actions.HarvestAction;
import game.ground.Dirt;
import game.ground.Tree;

/**
 * Class representing the Player.
 */
public class Player extends Actor {

	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		System.out.println("Total Eco-Points: " + EcoPointsSystem.getPoints());
		Ground actorGround = map.locationOf(this).getGround();
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		if (actorGround instanceof Tree || actorGround instanceof Dirt && ((Dirt) actorGround).hasGrass()) {
			actions.add(new HarvestAction(actorGround));
		}
		return menu.showMenu(this, actions, display);
	}
}
