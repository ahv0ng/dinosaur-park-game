package game.actors;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import game.actions.AttackAction;
import game.behaviours.FollowBehaviour;
import game.ground.ScanSurrounds;
import game.portables.CarnivoreMealKit;

public abstract class Carnivore extends Dinosaur {
    static final int HUNGER_POINTS_FROM_CORPSE = 50;
    static final int HUNGER_POINTS_FROM_STEGOSAURUS_EGG = 10;

    public Carnivore(String name, Character displayChar) {
        super(name, displayChar);
    }

    /**
     * Evaluate whether Carnivore can eat given Item. Carnivores can eat CarnivoreMealKit.
     * @param item - Item object for Carnivore to eat
     * @return boolean value on whether the Carnivore can eat the item
     */
    protected boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }

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
        FollowBehaviour follow = null;

        // Find portable Food item
        if (ScanSurrounds.getLocationOfCorpse(location) != null) {
            // If there is a corpse nearby, go towards it
            follow = new FollowBehaviour(ScanSurrounds.getLocationOfCorpse(location));
        }
        else if (ScanSurrounds.getLocationOfStegosaurEgg(location) != null) {
            // If there is a Herbivore egg nearby, go towards it
            follow = new FollowBehaviour(ScanSurrounds.getLocationOfStegosaurEgg(location));
        }

        if (follow.getFollowLocationAction(this, map) != null) {
            return follow.getFollowLocationAction(this, map);
        }

        // Find Actor to attack
        if (ScanSurrounds.getStegosaur(location) != null) {
            // If there is a Stegosaur nearby, go towards it
            follow = new FollowBehaviour(ScanSurrounds.getStegosaur(location));
            if (follow.getAction(this, map) != null) {
                return follow.getAction(this, map);
            }
            // Else if there is a Stegosaur nearby and you cannot go closer, it must be adjacent
            return new AttackAction(ScanSurrounds.getStegosaur(location));
        }

        return null;
    }
}
