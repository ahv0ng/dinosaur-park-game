package game.actions;

import edu.monash.fit2099.engine.*;
import game.ground.VendingMachine;

public class PurchaseAction extends Action {
    private VendingMachine vendingMachine;

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
