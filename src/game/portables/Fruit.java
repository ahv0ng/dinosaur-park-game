package game.portables;

import edu.monash.fit2099.engine.Location;
import game.actors.Dinosaur;
import game.actors.Stegosaur;

public class Fruit extends Food {
    private int age;

    static final int FILL = 30;
    static final int COST = 30;
    static final int ROT_AGE = 20;
    static final int POINTS_WHEN_FED = 15;

    public Fruit() {
        super("aFruit", 'f');
        this.age = 0;
    }

    public int getAge() { return this.age; }
    public int getCost() { return COST; }

    public void tick(Location location) {
        // Evaluate age and rotting status
        this.age++;
        if (this.age >= Fruit.ROT_AGE) {
            location.removeItem(this);
        }
    }

    public void feed(Dinosaur dinosaur) {
        if (!(dinosaur instanceof Stegosaur)) {
            throw new IllegalArgumentException("Fruit can only be fed to a Stegosaur.");
        }
        try {
            dinosaur.increaseHunger(Fruit.FILL);
        }
        // TODO: Decide so that player can choose to do something else, or just bad luck (they wasted a turn)?
        catch(Exception e) {
            System.out.println("Dinosaur is at its maximum food level already.");
        }


    }
}