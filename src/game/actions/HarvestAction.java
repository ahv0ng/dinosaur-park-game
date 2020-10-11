package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.ground.Dirt;
import game.portables.Hay;

public class HarvestAction extends Action {
    private Ground target;

    public HarvestAction(Ground ground) { this.target = ground; }

    public String execute(Actor player, GameMap map) {
        if (this.target instanceof Dirt) {
            Hay hay = ((Dirt) this.target).harvestGrass();
            player.addItemToInventory(hay);
            return menuDescription(player);
        }
        else {
            return "Nothing happened.";
        }
    }

    public String menuDescription(Actor player) { return "Player harvests the grass."; }
}
