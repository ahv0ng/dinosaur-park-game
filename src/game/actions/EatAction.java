package game.actions;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Item;
import game.actors.Dinosaur;
import game.ground.Dirt;
import game.items.WildEdible;

public class EatAction {
    private Ground groundTarget;
    private Item itemTarget;

    public EatAction(Ground ground) {
        this.groundTarget = ground;
    }
    public EatAction(Item item) {
        this.itemTarget = item;
    }
    public String execute(Actor actor, GameMap map) {
        Dinosaur dinosaur = (Dinosaur) actor;
        if (groundTarget != null) {
            Dirt dirt = (Dirt) this.groundTarget;
            dinosaur.increaseHungerPoints(dirt.getFill());
            dirt.removeGrass();
            return menuDescription(dinosaur);
        }
        else {
            WildEdible edible = (WildEdible) itemTarget;
            dinosaur.increaseHungerPoints(edible.getFill());
            return menuDescription(dinosaur);
        }
    }
    public String menuDescription(Actor actor) {
        if (groundTarget != null) {
            return actor + " eats grass";
        }
        else {
            return actor + " eats " + itemTarget;
        }
    }
}
