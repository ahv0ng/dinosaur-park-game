package game.portables;

import game.EcoPointsSystem;
import game.actors.Dinosaur;

public class Hay extends Food {
    static final int COST = 20;
    static final int FILL = 20;
    static final int POINTS_WHEN_FED = 10;

    public Hay() {
        super("Hay", 'h');
    }

    public int getCost() { return COST; }

    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
        EcoPointsSystem.earn(POINTS_WHEN_FED);
    }
}
