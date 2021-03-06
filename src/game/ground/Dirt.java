package game.ground;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.items.foods.Hay;
import game.scanning.Scan;

import java.util.Random;

/**
 * A class that represents bare dirt. Extends Ground.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class Dirt extends Ground {
	private Boolean grass;
	private final Random random = new Random();

	static final int FILL = 5;
	static final int POINTS_WHEN_HARVEST_OR_GROW_GRASS = 1;
	static final int GROW_CHANCE_AT_START = 2;
	static final int GROW_CHANCE_FOR_ADJACENT_TREE = 2;
	static final int GROW_CHANCE_FOR_TWO_GRASS = 10;

	/**
	 * Constructor for Dirt. At start of game, each Dirt has 2% chance of
	 * growing grass.
	 */
	public Dirt() {
		super('.');
		this.grass = false;
		if (random.nextInt(100) < GROW_CHANCE_AT_START) {
			this.grass = true;
			this.displayChar = '^';
		}
	}

	/**
	 * Return the hunger level fill when eaten by Dinosaurs. Use with Dinosaur's eatAtLocation
	 * method.
	 *
	 * There is no feed method because this item cannot be fed by Player, so it is not needed.
	 *
	 * @return integer value of the hunger level fill when eaten by Dinosaurs
	 */
	public int getFill() { return FILL; }

	/**
	 * Return boolean value when Dirt has grass.
	 *
	 * @return boolean whether Dirt has grass
	 */
	public boolean hasGrass() { return this.grass; }

	@Override
	public void tick(Location location) {
		if (!this.grass) {
			if (this.chanceOfGrass(location)) {
				this.growGrass();
			}
		}
	}

	/**
	 * Evaluate whether grass will grow on the next day, if grass is false.
	 *
	 * @param location - Location of the Dirt object
	 * @return boolean value on whether grass will grow
	 */
	public boolean chanceOfGrass(Location location) {
		int numberOfGrass = Scan.getNumberOfGrass(location);
		int numberOfTrees = Scan.getNumberOfTrees(location);

		if (numberOfGrass >= 2 && random.nextInt(100) < GROW_CHANCE_FOR_TWO_GRASS) {
			return true;
		}
		else return numberOfTrees >= 1 && random.nextInt(100) < GROW_CHANCE_FOR_ADJACENT_TREE;
	}

	/**
	 * Convert grass attribute to true. Earn points when grown.
	 */
	private void growGrass() {
		this.grass = true;
		this.displayChar = '^';
		EcoPointsSystem.earn(POINTS_WHEN_HARVEST_OR_GROW_GRASS);
	}

	/**
	 * Convert grass attribute to false. Earn points when harvested. Use only when Player harvests grass.
	 *
	 * @return Hay object given to the Player
	 */
	public Hay harvestGrass() {
		this.removeGrass();
		EcoPointsSystem.earn(POINTS_WHEN_HARVEST_OR_GROW_GRASS);
		return new Hay();
	}

	/**
	 * Convert grass attribute to false and change display character.
	 * Use when Stegosaur eats grass or grass is harvested.
	 */
	public void removeGrass() {
		this.grass = false;
		this.displayChar = '.';
	}
}
