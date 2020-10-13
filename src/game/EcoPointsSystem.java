package game;

public class EcoPointsSystem {
    private static int ecoPoints = 0;

    public static int getPoints() { return ecoPoints; }

    public static void earn(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points earned must be greater than zero.");
        }

        ecoPoints += points;
        System.out.println("Total points: " + ecoPoints);
    }

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
