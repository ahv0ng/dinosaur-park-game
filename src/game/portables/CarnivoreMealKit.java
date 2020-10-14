package game.portables;

import game.actors.Dinosaur;
import game.actors.Allosaur;

public class CarnivoreMealKit extends MealKit {
    static final int COST = 500;

    public CarnivoreMealKit() {
        super("Carnivore Meal Kit", 'c');
    }

    public int getCost() { return COST; }

    public void feed(Dinosaur dinosaur) {
        if (dinosaur instanceof Allosaur) {
            try {
                dinosaur.increaseHunger(FILL);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            throw new IllegalArgumentException("Only Allosaur can eat this.");
        }
    }
}
