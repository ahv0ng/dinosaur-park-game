package game.portables;

import edu.monash.fit2099.engine.Location;
import game.EcoPointsSystem;
import game.actors.Dinosaur;

public class Fruit extends Food {
    private int age;

    static final int FILL = 30;
    static final int COST = 30;
    static final int POINTS_WHEN_FED = 15;
    static final int ROT_AGE = 20;

    public Fruit() {
        super("Fruit", 'f');
        this.age = 0;
    }

    public int getAge() { return this.age; }
    public int getCost() { return COST; }

    public void tick(Location location) {
        // Evaluate age and rotting status
        this.age++;
        if (this.age >= ROT_AGE) {
            location.removeItem(this);
        }
    }

    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
        EcoPointsSystem.earn(POINTS_WHEN_FED);
    }
}