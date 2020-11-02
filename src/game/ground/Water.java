package game.ground;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import game.actors.Dinosaur;

public class Water extends Ground {
    static final int FILL = 10;

    /**
     * Constructor for Water
     */
    public Water() {
        super('~');
    }

    @Override
    public boolean canActorEnter(Actor actor) {
        return (actor instanceof Dinosaur) && (((Dinosaur) actor).checkFly());
    }

    /**
     * Return the thirst level fill when drank from by a Dinosaur. Use with Dinosaur's drinkAtLocation
     * method.
     *
     * @return integer value of the thirst level fill when drank from by Dinosaurs
     */
    public int getFill() { return FILL; }
}
