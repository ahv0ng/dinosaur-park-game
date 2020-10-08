package game;

public abstract class Food extends PortableItem {
    private int FILL;
    private int COST;

    private abstract void feed(Dinosaur dinosaur)
}