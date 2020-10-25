package game.actors;

public abstract class Omnivore extends Dinosaur {
    public Omnivore(String sex, String name, Character displayChar) {
        super(sex, name, displayChar);
    }
    public Omnivore(String name, Character displayChar) {
        super(name, displayChar);
    }
}
