package game.portables;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Extends WeaponItem.
 * @author Nicholas Chua and Alden Vong
 */
public class LaserGun extends WeaponItem {
    static final int COST = 500;

    /**
     * Constructor for LaserGun.
     */
    public LaserGun() {
        super("theLaserGun", 'l', 100, "shoot");
    }

    /**
     * Return the cost of purchasing a LaserGun.
     * @return integer representing the cost
     */
    public int getCost() { return COST; }
}
