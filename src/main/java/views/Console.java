package views;

import controllers.InventoryController;
import domain.Item;
import errors.ItemNotFound;
import errors.NameAlreadyExists;
import errors.NameCannotBeEmpty;
import errors.PriceCannotBeNegative;
import utils.Converters;

import java.util.List;
import java.util.Scanner;

public class Console {

    private Scanner scanner;
    private InventoryController inventoryController;

    public Console(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
        scanner = new Scanner(System.in);
    }

    public void run() {
        displayMainMenu();
    }

    public void createItemMenu() {
        //  show item menu
        displayTitle("Create new item Menu");

        // some logic to get item name and price from user
        //  ask the user for name
        String name = getUserInput("Please enter the item's name: ");

        // ask the user for price
        String priceString = getUserInput("Please enter the item's price: ");

        // convert price to double
        double price = Converters.stringToDouble(priceString);

        //create new item with userInput
        try {
            Item newItem = this.inventoryController.createNewItem(name, price);
            System.out.println(newItem.toString());
        }catch(PriceCannotBeNegative e){
            System.out.println("Price cannot be negative.");
            createItemMenu();
        }catch(NameCannotBeEmpty e){
            System.out.println("Name cannot be empty.");
            createItemMenu();
        } catch (NameAlreadyExists e) {
            System.out.println("Name already exists.");
            createItemMenu();
        }
    }

    public void displayInventoryItems() {
        displayTitle("Listing of inventory menu:");

        List<Item> items = this.inventoryController.getItems();
        //  show item menu
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }
    }

    public void addQuantityToItemMenu() {
        displayTitle("Add quantity to item menu:");
        displayInventoryItems();
        String codeItem = getUserInput("Please enter item code: ");
        String quantityString = getUserInput("Please enter quantity to add: ");
        try {
            this.inventoryController.addQuantityToItem(codeItem, Converters.stringToInteger(quantityString));
            System.out.println("Item quantity successfully added!");
        } catch (ItemNotFound itemNotFound) {
            System.out.println("I was not able to find your item");
        }
    }

    public void displayTitle(String msg) {
        System.out.println(msg);
        for (int i = 0; i < msg.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public void displayMainMenu() {
        displayTitle("POS system");
        System.out.println("1. Create new Item");
        System.out.println("2. List Items ");
        System.out.println("3. Add quantity to inventory");
        System.out.println("4. Sell item");
        System.out.println("5. Quit ");

        switch (getUserInput("Please choose an option: ")) {
            case "1":
                createItemMenu();
                break;
            case "2":
                displayInventoryItems();
                break;
            case "3":
                addQuantityToItemMenu();
                break;
            case "4":
                break;
            case "5":
                System.out.println("GOODBYE");
                return;
            default:
                System.out.println("Invalid Input");
        }

        displayMainMenu();
    }

    public String getUserInput(String msg) {
        System.out.print(msg);

        String UserInput = scanner.next();
        return UserInput;
    }
}
