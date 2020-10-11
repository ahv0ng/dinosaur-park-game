package game.portables;

import game.actors.Dinosaur;

public abstract class MealKit extends Food {
    static final int FILL = 100;

    public MealKit(String name, char displayChar) {
        super(name, displayChar);
    }

    public abstract void feed(Dinosaur dinosaur);
}
