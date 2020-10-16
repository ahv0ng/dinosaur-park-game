package game.portables;

import edu.monash.fit2099.engine.Item;

/**
 * Base class for any item that can be picked up and dropped.
 */
public class PortableItem extends Item {
	private int COST;

	/**
	 * Constructor for PortableItem.
	 *
	 * @param name - String representing the name of the Item
	 * @param displayChar - Character display on the map
	 */
	public PortableItem(String name, char displayChar) {
		super(name, displayChar, true);
	}

	/**
	 * Return the cost of purchasing an Item.
	 *
	 * @return integer representing the cost
	 */
	public int getCost() { return COST; }
}
