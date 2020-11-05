package game.items;

/**
 * Interface for items that can be eaten by dinosaurs, but are not fed to dinosaurs.
 *
 * This interface is used in EatAction to determine the fill value of such items.
 */
public interface WildEdible {
    int getFill();
}
