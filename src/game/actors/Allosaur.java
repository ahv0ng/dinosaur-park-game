package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
import game.behaviours.BreedingBehaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.WanderBehaviour;
import game.ground.ScanSurrounds;
import game.portables.AllosaurEgg;
import game.portables.CarnivoreMealKit;
import game.portables.Egg;
import game.portables.Food;

public class Allosaur extends Dinosaur {
    public Allosaur() { super("Allosaur", 'a'); }

    // FIXME: Is there a way to make this method more readable by separating them into code blocks?
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        this.incrementAge();
        Location location = map.locationOf(this);
        if (this.isHungry()) {
            System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") is hungry!");
        }
        if (this.isPregnant()) {
            this.decrementDaysUntilLay();
            if (this.getDaysUntilLay() == 0) {
                map.locationOf(this).addItem(this.layEgg());
                System.out.println("Dinosaur at (" + location.x() + ", " + location.y() + ") laid an egg!");
            }
        }
        if (this.getHungerLevel() == 0) { this.unconsciousUntilFed(map); }
        // Stegosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.resetDaysUnconscious();
            this.increaseHunger(-1);
            Action breed = new BreedingBehaviour().getAction(this, map);
            if (breed != null) {
                return breed;
            }
        }
        // Stegosaur wanders around or does nothing
        Action wander = new WanderBehaviour().getAction(this, map);
        if (wander != null)
            return wander;
        return new DoNothingAction();
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

    private boolean canEat(Item item) { return item instanceof CarnivoreMealKit; }
    public AttackAction attack(Stegosaur stegosaur) {
        return new AttackAction(stegosaur);
    }
    @Override
    public Egg layEgg() {
        this.noLongerPregnant();
        this.resetDaysUntilLay();
        return new AllosaurEgg();
    }
    /**
     * Creates and returns an intrinsic weapon for allosaurs (biting with teeth).
     *
     * @return a freshly-instantiated IntrinsicWeapon
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(20, "bites");
    }
}
