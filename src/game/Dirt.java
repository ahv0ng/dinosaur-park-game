package game;

import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents bare dirt.
 */
public class Dirt extends Ground {
	private Boolean grass = false;
	static final int POINTS_WHEN_HARVEST_OR_GROW_GRASS = 1;

	public Dirt() {
		super('.');
	}
}
