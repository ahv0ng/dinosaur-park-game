package game.worlds;

import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.World;

import java.util.Scanner;

public class WorldFactory {
    private final static Scanner inputReader = new Scanner(System.in);

    public static World getWorld(String worldName, Display display) {

        if (worldName == "Challenge") {
            int maxTurns = inputValidInt("For Challenge mode, provide the maximum number of turns to complete: ");
            int ecoPointsGoal = inputValidInt("Next, provide the goal of Eco-Points earned at the end of the game: ");
            return new ChallengeWorld(display, maxTurns, ecoPointsGoal);
        }
        else if (worldName == "Sandbox") {
            return new SandboxWorld(display);
        }
        else {
            // Should never enter this code block
            System.out.println("Unknown world: " + worldName);
            return null;
        }
    }

    /**
     * Helper method. Accept positive integer value and continue looping if invalid input.
     * @param message - String of the message to be displayed
     * @return positive integer value
     */
    private static int inputValidInt(String message) {
        boolean invalidInput = true;
        int number = 0;
        
        while (invalidInput) {
            System.out.println(message);
            try {
                number = Integer.parseInt(inputReader.nextLine());
            }
            catch (NumberFormatException e) {
                continue;
            }
            if (number > 0) {
                invalidInput = false;
            }
        }

        return number;
    }
}
