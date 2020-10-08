package game;

public class Fruit extends Food {
    private int age;

    static final int FILL = 30;
    static final int COST = 30;
    static final int ROT_AGE = 20;
    static final int POINTS_WHEN_FED = 15;

    Fruit() {
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
        if (!(dinosaur instanceOf Stegosaurus)) {
            raise new IllegalArgumentMethod("Fruit can only be fed to Stegosaurus.")
        }

        dinosaur.increaseHunger(Fruit.FILL);
    }
}