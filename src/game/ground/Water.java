package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.actors.Dinosaur;

public class Water extends Ground {
    public Water() {
        super('~');
    }
    // TODO: Add flying attributes and methods to Dinosaur class
    @Override
    public boolean canActorEnter(Actor actor) {
        if ((actor instanceof Dinosaur) && (((Dinosaur) actor).canFly())) {
            return true;
        }
        return false;
    }
}
