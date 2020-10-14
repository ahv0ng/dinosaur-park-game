package game.portables;

import game.actors.Dinosaur;

public class VegetarianMealKit extends MealKit {
    static final int COST = 100;

    public VegetarianMealKit() {
        super("Vegetarian Meal Kit", 'v');
    }

    public int getCost() { return COST; }

    public void feed(Dinosaur dinosaur) {
        dinosaur.increaseHunger(FILL);
    }
}
