package game;

/**
 * An Eco-Points system that keeps track of points in the game. All methods are
 * static so that it can be used globally, without needing to create an
 * instance/dependency. There is always one EcoPointsSystem throughout the
 * entire game.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class EcoPointsSystem {
    private static int ecoPoints = 0;

    /**
     * Return the total number of points each round. Must be called every round.
     * Static method.
     *
     * @return integer value of the number of Eco-Points
     */
    public static int getPoints() { return ecoPoints; }

    /**
     * Earn points when a specific scenario happens in the game. Static method.
     * - Grass grows or is harvested (1 point)
     * - Hay is fed to a dinosaur (10 points)
     * - Fruit is fed to a dinosaur (15 points)
     * - Stegosaur egg hatches (100 points)
     * - Allosaur egg hatches (1000 points)
     *
     * @param points integer value of points to be earned
     */
    public static void earn(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points earned must be greater than zero.");
        }

        ecoPoints += points;
        System.out.println("Total points: " + ecoPoints);
    }

    /**
     * Spend points when Items are bought from VendingMachine. Static method.
     * - Hay (20 points)
     * - Fruit (30 points)
     * - Vegetarian Meal Kit (100 points)
     * - Carnivore Meal Kit (500 points)
     * - Stegosaur Egg (200 points)
     * - Allosaur Egg (1000 points)
     * - Laser Gun (500 points)
     *
     * @param points integer value of points to be spent
     */
    public static void spend(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points spent must be greater than zero.");
        }
        else if (points > ecoPoints) {
            throw new IllegalArgumentException("Points spent exceeds the total amount available.");
        }

        ecoPoints -= points;
        System.out.println("Total points: " + ecoPoints);
    }
}
