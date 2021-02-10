package views;

import controllers.InventoryController;
import errors.NameCannotBeEmpty;
import errors.PriceCannotBeNegative;
import utils.Converters;

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
        System.out.println("Create new item Menu");

        // some logic to get item name and price from user
        //  ask the user for name
        String name = getUserInput("Please enter the item's name: ");

        // ask the user for price
        String priceString = getUserInput("Please enter the item's price: ");

        // convert price to double
        double price = Converters.stringToDouble(priceString);

        //create new item with userInput
        try {
            this.inventoryController.createNewItem(name, price);
        }catch(PriceCannotBeNegative e){
            System.out.println("Price cannot be negative.");
            createItemMenu();
        }catch(NameCannotBeEmpty e){
            System.out.println("Name cannot be empty.");
            createItemMenu();
        }
    }

    public void displayMainMenu() {

        System.out.println("POS system");
        System.out.println("1. Create new Item");
        System.out.println("2. List Items ");
        System.out.println("3. Add qty to inventory");
        System.out.println("4. Sell item");
        System.out.println("5. Quit ");

        switch (getUserInput("Please choose an option: ")) {
            case "1":
                createItemMenu();
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                System.out.println("GOODBYE");
                return;
            default:
                System.out.println("Invalid Input");
                displayMainMenu();
        }
    }

    public String getUserInput(String msg) {
        System.out.print(msg);

        String UserInput = scanner.next();
        return UserInput;
    }


}
