package game.portables;

import game.actors.Dinosaur;

public abstract class Egg extends PortableItem {
    private int timeAlive;
    static int POINTS_WHEN_HATCH;

    public Egg(String name, char displayChar) {
        super(name, displayChar);
    }

    public abstract Dinosaur hatch();

    public void tick() {
        this.timeAlive += 1;
        if (this.timeAlive == 10) {
            this.hatch();
        }
    }
}
