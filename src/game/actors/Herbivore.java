package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.FollowBehaviour;
import game.ground.Dirt;
import game.ground.ScanSurrounds;
import game.portables.Food;
import game.portables.Fruit;
import game.portables.Hay;
import game.portables.VegetarianMealKit;

public abstract class Herbivore extends Dinosaur {
    static final int HUNGER_POINTS_FOR_GRAZE_GRASS = 5;

    public Herbivore(String sex, String name, Character displayChar) {
        super(sex, name, displayChar);
    }
    public Herbivore(String name, Character displayChar) {
        super(name, displayChar);
    }

    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        actions.add(new AttackAction(this));
        for (Item item : otherActor.getInventory()) {
            if (this.canEat(item)) {
                actions.add(new FeedAction((Food) item, this));
            }
        }
        return actions;
    }

    /**
     * Evaluate whether Herbivore can eat given Item.
     *
     * @param item - Item object for Herbivore to eat
     * @return boolean value on whether the Herbivore can eat the item
     */
    private boolean canEat(Item item) {
        if (item instanceof Fruit) {
            return true;
        }
        else if (item instanceof Hay) {
            return true;
        }
        else if (item instanceof VegetarianMealKit) {
            return true;
        }
        return false;
    }

    /**
     * If on top of Dirt with grass attribute, remove grass for Herbivore to eat.
     *
     * @param location - Location type of the current location of the Herbivore
     */
    void graze(Location location) {
        if (location.getGround() instanceof Dirt && ((Dirt) location.getGround()).hasGrass()) {
            ((Dirt) location.getGround()).removeGrass();
            this.increaseHunger(HUNGER_POINTS_FOR_GRAZE_GRASS);
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate grass.");
        }
    }

    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        if (ScanSurrounds.getGrass(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getGrass(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        return null;
    }
}
