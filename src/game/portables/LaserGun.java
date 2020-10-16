package game.portables;

import edu.monash.fit2099.engine.WeaponItem;

/**
 * Extends WeaponItem.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class LaserGun extends WeaponItem {
    static final int COST = 500;

    /**
     * Constructor for LaserGun. Able to kill a Dinosaur in one shot.
     */
    public LaserGun() {
        super("Laser Gun", 'l', 100, "shoots");
    }

    /**
     * Return the cost of purchasing a LaserGun.
     *
     * @return integer representing the cost
     */
    public int getCost() { return COST; }
}
