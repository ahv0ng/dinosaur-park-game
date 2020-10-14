package game.ground;

import edu.monash.fit2099.engine.*;
import game.EcoPointsSystem;
import game.actions.PurchasingAction;
import game.portables.*;

import java.util.Scanner;

public class VendingMachine extends Ground {
    private String[] items = {"Fruit", "Hay", "LaserGun", "StegosaurEgg", "VegetarianMealKit", "CarnivoreMealKit"};

    public VendingMachine() { super('Q'); }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new PurchasingAction(this));
        return actions;
    }
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
