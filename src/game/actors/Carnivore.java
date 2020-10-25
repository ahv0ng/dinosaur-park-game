package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.FollowBehaviour;
import game.ground.ScanSurrounds;
import game.portables.CarnivoreMealKit;
import game.portables.Food;

public abstract class Carnivore extends Dinosaur {
    static final int HUNGER_POINTS_FROM_CORPSE = 50;
    static final int HUNGER_POINTS_FROM_STEGOSAURUS_EGG = 10;

    public Carnivore(String name, Character displayChar) {
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
     * Evaluate whether Carnivore can eat given Item.
     *
     * @param item - Item object for Carnivore to eat
     * @return boolean value on whether the Carnivore can eat the item
     */
    private boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }

    /**
     * Allows Carnivore to eat a Corpse or StegosaurEgg.
     *
     * @param location - Location type of the current location of Carnivore
     */
    void eat(Location location) {
        if (ScanSurrounds.getCorpse(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_CORPSE);
            location.removeItem(ScanSurrounds.getCorpse(location));
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate a corpse.");
        }
        else if (ScanSurrounds.getStegosaurEgg(location) != null) {
            this.increaseHunger(HUNGER_POINTS_FROM_STEGOSAURUS_EGG);
            location.removeItem(ScanSurrounds.getStegosaurEgg(location));
            System.out.println(this + " at (" + location.x() + "," + location.y() + ")" + " ate an egg.");
        }
    }

    @Override
    protected Action lookForFoodBehaviour(GameMap map, Location location) {
        // If there is a corpse nearby, go towards it
        if (ScanSurrounds.getLocationOfCorpse(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getLocationOfCorpse(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        // If there is a stegosaur egg nearby, go towards it
        else if (ScanSurrounds.getLocationOfStegosaurEgg(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getLocationOfStegosaurEgg(location));
            if (follow.getFollowLocationAction(this, map) != null) {
                return follow.getFollowLocationAction(this, map);
            }
        }
        // If there is a stegosaur nearby, go towards it
        else if (ScanSurrounds.getStegosaur(location) != null) {
            FollowBehaviour follow = new FollowBehaviour(ScanSurrounds.getStegosaur(location));
            if (follow.getAction(this, map) != null) {
                return follow.getAction(this, map);
            }
            // If there is a stegosaur nearby and you cannot go closer, it must be adjacent
            return new AttackAction(ScanSurrounds.getStegosaur(location));
        }
        return null;
    }
}
