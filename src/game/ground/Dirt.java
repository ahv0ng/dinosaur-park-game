package game.ground;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.actions.HarvestAction;
import game.portables.Food;
import game.portables.Fruit;
import game.portables.Hay;
import game.portables.VegetarianMealKit;

import java.util.Random;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	private Boolean grass;
	private final Random random = new Random();

	static final int POINTS_WHEN_HARVEST_OR_GROW_GRASS = 1;
	static final int GROW_CHANCE_AT_START = 2;
	static final int GROW_CHANCE_FOR_ADJACENT_TREE = 2;
	static final int GROW_CHANCE_FOR_TWO_GRASS = 10;

	public Dirt() {
		super('.');
		this.grass = random.nextInt(100) < GROW_CHANCE_AT_START;
	}
	@Override
	public Actions allowableActions(Actor actor, Location location, String direction){
		Actions actions = new Actions();
		if (this.hasGrass()) {
			actions.add(new HarvestAction(this));
		}
		return actions;
	}
	public boolean hasGrass() { return this.grass; }

	public void tick(Location location) {
		if (!this.grass) {
			if (this.chanceOfGrass(location))
			{
				this.growGrass();
			}
		}
	}

	public boolean chanceOfGrass(Location location) {
		int numberOfGrass = ScanSurrounds.adjacentGrass(location);
		int numberOfTrees = ScanSurrounds.adjacentTrees(location);

		if (numberOfGrass > 2 && random.nextInt(100) < GROW_CHANCE_FOR_TWO_GRASS) {
			return true;
		}
		else return numberOfTrees > 1 && random.nextInt(100) < GROW_CHANCE_FOR_ADJACENT_TREE;
	}

	private void growGrass() {
		this.grass = true;
		this.displayChar = '^';
	}

	public Hay harvestGrass() {
		this.grass = false;
		this.displayChar = '.';
		return new Hay();
	}

	public void removeGrass() {
		this.grass = false;
		this.displayChar = '.';
	}
}
