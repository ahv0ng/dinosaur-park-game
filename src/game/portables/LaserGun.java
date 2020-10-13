package game.portables;

import edu.monash.fit2099.engine.WeaponItem;

public class LaserGun extends WeaponItem {
    static final int COST = 500;

    public LaserGun() {
        super("theLaserGun", 'l', 100, "shoot");
    }

    public int getCost() { return COST; }
}
