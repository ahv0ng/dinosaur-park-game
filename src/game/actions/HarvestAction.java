package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.ground.Dirt;
import game.portables.Hay;

public class HarvestAction extends Action {
    private Dirt target;

    public HarvestAction(Dirt dirt) { this.target = dirt; }
    @Override
    public String execute(Actor player, GameMap map) {
        Hay hay = (target.harvestGrass());
        player.addItemToInventory(hay);
        return System.lineSeparator() + "Player harvested the grass";
        }
    @Override
    public String menuDescription(Actor player) { return "Player harvests the grass"; }
}
