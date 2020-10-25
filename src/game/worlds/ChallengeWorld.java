package game.worlds;

import edu.monash.fit2099.engine.*;
import game.EcoPointsSystem;

/**
 * Extends World.
 * @author Nicholas Chua and Alden Vong
 */
public class ChallengeWorld  extends World {
    private int turnCounter;
    private final int maxTurns;
    private final int ecoPointsGoal;

    /**
     * Constructor for ChallengeWorld.
     * @param display - Display menu for the player to select actions
     * @param maxTurns - integer value selected by user for maximum turns to be played
     * @param ecoPointsGoal - integer value selected by user for their goal
     */
    public ChallengeWorld(Display display, int maxTurns, int ecoPointsGoal) {
        super(display);
        this.turnCounter = 0;
        this.maxTurns = maxTurns;
        this.ecoPointsGoal = ecoPointsGoal;
    }

    @Override
    public void run() {
        if (this.player == null) {
            throw new IllegalStateException("No Player found.");
        }

        // Initialise the lastActionMap to DoNothingActions
        for (Actor actor : this.actorLocations) {
            this.lastActionMap.put(actor, new DoNothingAction());
        }

        while (this.stillRunning()) {
            GameMap playersMap = this.actorLocations.locationOf(this.player).map();
            playersMap.draw(this.display);

            // Display turns remaining and eco-points earned
            int turnsRemaining = this.maxTurns - this.turnCounter;
            this.display.println("Turns remaining: " + turnsRemaining);
            this.display.println("Total Eco-points: " + EcoPointsSystem.getPoints());

            // Process all the alive actors
            for (Actor actor : this.actorLocations) {
                if (this.stillRunning()) {
                    this.processActorTurn(actor);
                }
            }

            // Tick over all the maps and items
            for (GameMap gameMap : this.gameMaps) {
                gameMap.tick();
            }

            this.turnCounter++;
        }

        this.display.println(endGameMessage());
    }

    @Override
    protected boolean stillRunning() {
        if (!this.actorLocations.contains(this.player)) {
            // Stop running if player is dead or disappears
            return false;
        }
        // Stop running if the number of turns exceed the maxTurns specified
        else return this.turnCounter < this.maxTurns;
    }

    @Override
    protected String endGameMessage() {
        this.display.endLine(); // Starts new line

        if (EcoPointsSystem.getPoints() > this.ecoPointsGoal) {
            this.display.println("You win!");
        }
        else {
            this.display.println("You lose!");
        }

        this.display.println("Number of turns: " + this.maxTurns);
        this.display.println("Eco-points earned: " + EcoPointsSystem.getPoints() + "/" + this.ecoPointsGoal);
        return "Game over.\n";
    }
}
