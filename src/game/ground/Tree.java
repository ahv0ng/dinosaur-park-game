package game.ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.items.foods.Fruit;

import java.util.Random;

/**
 * Extends Ground.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Tree extends Ground {
	private int age = 0;
	private final Random random = new Random();

	static final int DROP_CHANCE = 5;
	static final int HARVEST_CHANCE = 40;

	/**
	 * Constructor for Tree.
	 */
	public Tree() {
		super('+');
	}

	/**
	 * Call for Tree to tick over the next day.
	 *
	 * @param location The location of the Ground
	 */
	@Override
	public void tick(Location location) {
		this.increaseAge();
		this.dropFruit(location);
	}

	/**
	 * Increase the age of the Tree.
	 */
	private void increaseAge() {
		this.age++;
		if (age == 10) {
			this.displayChar = 't';
		}
		if (age == 20) {
			this.displayChar = 'T';
		}
	}

	/**
	 * Drop Fruit after a given probability returns true.
	 *
	 * @param location - Location of the Tree
	 */
	private void dropFruit(Location location) {
		if (random.nextInt(100) < Tree.DROP_CHANCE) {
			Fruit fruit = new Fruit();
			location.addItem(fruit);
		}
	}

	/**
	 * Harvest Fruit when Player attempts to harvest Fruit, only when given probability
	 * returns true.
	 *
	 * @return Fruit object to the Player
	 */
	public Fruit harvestFruit() {
		if (random.nextInt(100) < Tree.HARVEST_CHANCE) {
			return new Fruit();
		}
		return null;
	}
}
