package game.actors;

import game.portables.AgilisaurEgg;

public class Agilisaur extends Omnivore {

    public Agilisaur() { super("Agilisaur", 'g'); }

    @Override
    protected AgilisaurEgg layEgg() { return new AgilisaurEgg(); }
}
