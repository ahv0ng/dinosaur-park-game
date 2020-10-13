package game.portables;

import game.actors.Dinosaur;
import game.actors.Stegosaur;

public class Hay extends Food {
    static final int COST = 20;
    static final int FILL = 20;
    static final int POINTS_WHEN_HAY_FED = 10;

    public Hay() {
        super("someHay", 'h');
    }

    public int getCost() { return COST; }

    public void feed(Dinosaur dinosaur) {
        if (dinosaur instanceof Stegosaur) {
            try {
                dinosaur.increaseHunger(FILL);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            throw new IllegalArgumentException("Only Stegosaurs can eat hay.");
        }
    }
}
