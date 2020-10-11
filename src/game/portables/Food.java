package game.portables;

import game.actors.Dinosaur;

public abstract class Food extends PortableItem {
    private int FILL;
    private int COST;

    public Food(String name, char displayChar) {
        super(name, displayChar);
    }

    abstract void feed(Dinosaur dinosaur);
}