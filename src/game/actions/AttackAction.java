package game.actions;

import edu.monash.fit2099.engine.*;
import game.actors.Dinosaur;

import java.util.Random;

/**
 * Special Action for attacking other Dinosaurs. So far, only Dinosaurs can die.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class AttackAction extends Action {
	protected Dinosaur target;
	protected Random rand = new Random();

	/**
	 * Constructor for AttackAction.
	 *
	 * @param target the Actor to attack
	 */
	public AttackAction(Dinosaur target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		Weapon weapon = actor.getWeapon();

		if (rand.nextBoolean()) {
			return actor + " misses " + target + ".";
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		if (!target.isConscious()) {
			result += System.lineSeparator() + target + " is killed.";
			target.die(map);
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
