package game.actors;

import edu.monash.fit2099.engine.*;
import game.actions.AttackAction;
import game.actions.FeedAction;
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
        // Allosaur is either conscious/unconscious from hunger level
        if (this.getHungerLevel() == 0) { return new DoNothingAction(); }
        // Allosaur looks for a mate
        else if (this.getHungerLevel() > 50) {
            this.increaseHunger(-1);
            Dinosaur potentialMate = getMate(map, location);
            if (potentialMate != null) {
                FollowBehaviour follow = new FollowBehaviour(potentialMate);
                // If the allosaur is female, not pregnant, and a male is close by, mate
                if (this.isFemale() && !(this.isPregnant()) &&
                        adjacentMate(map, potentialMate)) {
                    this.mate();
                    System.out.println("Dinosaurs at (" + location.x() + ", " + location.y() + ") and (" +
                            map.locationOf(potentialMate).x() + "," +
                            map.locationOf(potentialMate).y() + ") mated!");
                }
                if (follow.getAction(this, map) != null) {
                    return follow.getAction(this, map);
                }
            }
        }
        // Allosaur wanders around or does nothing
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
    protected Dinosaur getMate(GameMap map, Location location) {
        if (!(this.isAdult())) {
            return null;
        }
        if (this.isFemale() && this.isPregnant()) {
            return null;
        }
        for (Location loc : ScanSurrounds.getLocationsWithin3(map, location)) {
            if ((map.getActorAt(loc) instanceof Allosaur) && this.isOppositeSex((Allosaur) map.getActorAt(loc))
                    && !((Allosaur) map.getActorAt(loc)).isPregnant()) {
                return (Allosaur) map.getActorAt(loc);
            }
        }
        // No potential mate in sight
        return null;
    }

    @Override
    protected Egg layEgg() {
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
