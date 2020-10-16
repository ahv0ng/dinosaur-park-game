package game.actions;

import edu.monash.fit2099.engine.*;
import game.ground.VendingMachine;

/**
 * Special action for the Player to purchase an Item when VendingMachine is nearby.
 * Extends Action.
 *
 * @author Nicholas Chua and Alden Vong
 */
public class PurchaseAction extends Action {
    private VendingMachine vendingMachine;

    /**
     * Constructor for PurchaseAction. Select VendingMachine to purchase from.
     *
     * @param vendingMachine Vending Machine to purchase Items from
     */
    public PurchaseAction(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        try {
            Item item = vendingMachine.purchase();
            actor.addItemToInventory(item);
        } catch (Exception e) {
            return System.lineSeparator() + e.getMessage();
        }
        return System.lineSeparator() + "Purchase was successful";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys an item";
    }
}
