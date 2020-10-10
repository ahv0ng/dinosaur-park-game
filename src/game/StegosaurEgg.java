package game;

public class StegosaurEgg extends Egg {
    private int COST = 200;
    static final int POINTS_WHEN_HATCH = 100;

    public StegosaurEgg(String name, char displayChar) {
        super(name, displayChar);
    }

    @Override
    public Dinosaur hatch() {
        // TODO: Add to eco points
        return new Stegosaur();
    }
}
