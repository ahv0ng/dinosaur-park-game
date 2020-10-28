package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.HarvestAction;
import game.actions.QuitPlayerAction;
import game.ground.Dirt;
import game.ground.Tree;

/**
 * Class representing the Player.
 * @author Nicholas Chua and Alden Vong
 */
public class Player extends Actor {

	private final Menu menu = new Menu();

	/**
	 * Constructor for the Player.
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
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// Process ground actions
		Ground ground = map.locationOf(this).getGround();
		if (this.hasHarvestAction(ground)) {
			actions.add(new HarvestAction(ground));
		}

		actions.add(new QuitPlayerAction());

		return menu.showMenu(this, actions, display);
	}

	/**
	 * Evaluate whether Player can use HarvestAction on current Location. Should return true
	 * when ground is Tree or Dirt object with grass attribute.
	 *
	 * @param ground - Ground type that represents the ground of the Player's Location
	 * @return boolean value representing if Player can use HarvestAction
	 */
	private boolean hasHarvestAction(Ground ground) {
		if (ground instanceof Dirt && ((Dirt) ground).hasGrass()) {
			return true;
		}
		else return ground instanceof Tree;
	}
}
