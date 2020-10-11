package game.portables;

import game.actors.Dinosaur;
import game.actors.Stegosaur;

public class VegetarianMealKit extends MealKit {
    static final int COST = 100;

    public VegetarianMealKit(String name, char displayChar) {
        super(name, displayChar);
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
            throw new IllegalArgumentException("Only Stegosaur can eat this.");
        }
    }
}
