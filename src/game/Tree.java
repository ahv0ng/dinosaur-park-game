package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import java.util.Random;

public class Tree extends Ground {
	private int age = 0;
	private Random random = new Random();

	static final int DROP_CHANCE = 5;
	static final int HARVEST_CHANCE = 40;

	public Tree() {
		super('+');
	}

	@Override
	public void tick(Location location) {
		this.increaseAge();
		this.dropFruit(location);
	}

	private void increaseAge() {
		this.age++;
		if (age == 10) {
			this.displayChar = 't';
		}
		if (age == 20) {
			this.displayChar = 'T';
		}
	}

	private void dropFruit(Location location) {
		int number = random.nextInt(100);
		if (number < Tree.DROP_CHANCE) {
			Fruit fruit = new Fruit();
			location.addItem(fruit);
		}
	}

	public Fruit harvestFruit() {
		int number = random.nextInt(100);
		if (number < Tree.HARVEST_CHANCE) {
			return new Fruit();
		}
		System.out.println("You search the tree for fruit, but you can't find any ripe ones.");
		// To account for case where nothing is returned
		return null;
	}
}
