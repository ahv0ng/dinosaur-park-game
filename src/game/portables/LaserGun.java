package game.portables;

import edu.monash.fit2099.engine.WeaponItem;

public class LaserGun extends WeaponItem {
    static final int COST = 500;

    public LaserGun(String name, char displayChar, int damage, String verb) {
        super(name, displayChar, damage, verb);
    }

    public int getCost() { return COST; }
}
