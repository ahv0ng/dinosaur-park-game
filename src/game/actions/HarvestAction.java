package game.actions;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import game.ground.Dirt;
import game.ground.Tree;
import game.portables.Fruit;
import game.portables.Hay;

public class HarvestAction extends Action {
    private Ground target;

    public HarvestAction(Ground ground) {
        this.target = ground;
    }

    @Override
    public String execute(Actor player, GameMap map) {
        if (target instanceof Dirt) {
            Hay hay = (((Dirt) target).harvestGrass());
            player.addItemToInventory(hay);
            return System.lineSeparator() + "Player harvested the grass";
        }
        else if (target instanceof Tree) {
            Fruit fruit = ((Tree) target).harvestFruit();
            if (fruit == null) {
                return System.lineSeparator() + "You searched the tree for fruit, but you couldn't find any ripe ones";
            } else {
                player.addItemToInventory(fruit);
                return System.lineSeparator() + "Fruit was picked from the tree";
            }

        }
        return null;
    }
    @Override
    public String menuDescription(Actor actor) {
        if (target instanceof Dirt) {
            return actor + " harvests the grass";
        }
        else if (target instanceof Tree) {
            return actor + " tries to pick fruit from tree";
        }
        return null;
    }
}