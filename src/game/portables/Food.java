package game.portables;

import game.actors.Dinosaur;

public abstract class Food extends PortableItem {
    private int FILL;

    public Food(String name, char displayChar) {
        super(name, displayChar);
    }

    public abstract void feed(Dinosaur dinosaur);
}