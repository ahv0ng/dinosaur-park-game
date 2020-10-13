package game.actions;

import edu.monash.fit2099.engine.*;
import game.ground.VendingMachine;

public class PurchasingAction extends Action {

    public String execute(Actor actor, GameMap map) {
        Location locationOfActor = map.locationOf(actor);

        if (locationOfActor.getGround() instanceof VendingMachine) {
            VendingMachine machine = (VendingMachine) locationOfActor.getGround();
            try {
                Item item = machine.purchase();
                actor.addItemToInventory(item);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return menuDescription(actor);
        }
        else {
            return "Player must be on top of a vending machine!";
        }
    }

    public String menuDescription(Actor actor) {
        return actor + "tries to buys an item";
    }
}
