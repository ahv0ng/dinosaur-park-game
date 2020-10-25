package game.worlds;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.World;

/**
 * Extends World.
 * @author Nicholas Chua and Alden Vong
 */
public class SandboxWorld extends World {
    /**
     * Constructor for SandboxWorld
     * @param display - Display menu for the player to select actions
     */
    public SandboxWorld(Display display) {
        super(display);
    }

    @Override
    protected String endGameMessage() {
        return "Game over.\n";
    }
}
