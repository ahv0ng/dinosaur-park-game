package game.ground;

import edu.monash.fit2099.engine.*;
import game.EcoPointsSystem;
import game.actions.PurchaseAction;
import game.items.*;
import game.items.eggs.*;
import game.items.foods.CarnivoreMealKit;
import game.items.foods.Fruit;
import game.items.foods.Hay;
import game.items.foods.VegetarianMealKit;

import java.util.Scanner;

/**
 * Extends Ground.
 *
 * @author Nicholas Chua & Alden Vong
 */
public class VendingMachine extends Ground {
    private String[] items = {
            "Fruit",
            "Hay",
            "LaserGun",
            "StegosaurEgg",
            "AllosaurEgg",
            "AgilisaurEgg",
            "ArchaeopteryxEgg",
            "VegetarianMealKit",
            "CarnivoreMealKit"
    };

    /**
     * Constructor for VendingMachine.
     */
    public VendingMachine() { super('Q'); }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new PurchaseAction(this));
        return actions;
    }

    /**
     * Create a menu of items for user to select an Item to buy. Once selected, spend
     * Eco-Points and add Item to Player's inventory.
     *
     * @return Item object of the selected item
     * @throws Exception when item doesn't exist
     */
    public Item purchase() throws Exception {
        // Create a menu and list all items. Then ask for user input and return Item
        System.out.println("Available items to purchase: ");
        for (String item : items) {
            System.out.println(item);
        }
        System.out.println("Select the item that you want to purchase. Ensure you spell correctly.");

        Scanner inputReader = new Scanner(System.in);
        String userInput = inputReader.nextLine();
        Item purchasedItem;

        switch (userInput) {
            case "Fruit":
                System.out.println("Purchasing Fruit...");
                purchasedItem = new Fruit();
                break;
            case "Hay":
                System.out.println("Purchasing Hay...");
                purchasedItem = new Hay();
                break;
            case "LaserGun":
                System.out.println("Purchasing Laser Gun...");
                purchasedItem = new LaserGun();
                break;
            case "StegosaurEgg":
                System.out.println("Purchasing Stegosaur Egg...");
                purchasedItem = new StegosaurEgg();
                break;
            case "AllosaurEgg":
                System.out.println("Purchasing Allosaur Egg...");
                purchasedItem = new AllosaurEgg();
                break;
            case "AgilisaurEgg":
                System.out.println("Purchasing Agilisaur Egg...");
                purchasedItem = new AgilisaurEgg();
                break;
            case "ArchaeopteryxEgg":
                System.out.println("Purchasing Archaeopteryx Egg...");
                purchasedItem = new ArchaeopteryxEgg();
                break;
            case "VegetarianMealKit":
                System.out.println("Purchasing Vegetarian Meal Kit...");
                purchasedItem = new VegetarianMealKit();
                break;
            case "CarnivoreMealKit":
                System.out.println("Purchasing Carnivore Meal Kit...");
                purchasedItem = new CarnivoreMealKit();
                break;
            default:
                throw new Exception("Item doesn't exist.");
        }

        int cost;
        if (purchasedItem instanceof WeaponItem) {
            cost = ((LaserGun) purchasedItem).getCost();
        }
        else {
            cost = ((PortableItem) purchasedItem).getCost();
        }

        EcoPointsSystem.spend(cost);
        return purchasedItem;
    }
}
