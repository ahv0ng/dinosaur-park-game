package game.portables;

import edu.monash.fit2099.engine.Location;

public abstract class Egg extends PortableItem {
    private int timeAlive;
    static int POINTS_WHEN_HATCH;

    public Egg(String name, char displayChar) {
        super(name, displayChar);
    }

    public void tick(Location location) {
        this.timeAlive += 1;
        if (this.timeAlive == 10) {
            this.hatch(location);
        }
    }

    public abstract void hatch(Location location);
}
