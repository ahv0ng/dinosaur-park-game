package game.portables;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	private int COST;

	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}

	public int getCost() { return COST; }
}
