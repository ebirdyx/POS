package views;

import controllers.InventoryController;

import java.util.Scanner;

public class Console {

    private InventoryController inventoryController;

    public Console(InventoryController inventoryController){
        this.inventoryController = inventoryController;
    }

    public void run() {
        displayMainMenu();
    }

    public void displayMainMenu() {

        System.out.println("POS system");
        System.out.println("1. Create new Item");
        System.out.println("2. List Items ");
        System.out.println("3. Add qty to inventory");
        System.out.println("4. Sell item");
        System.out.println("5. Quit ");

        switch (getUserInput()) {
            case "1":

                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":
                System.out.println("GOODBYE");
                return ;
            default:
                System.out.println("Invalid Input");
                displayMainMenu();
        }
    }

    public String getUserInput() {
        System.out.print("Please choose an option: ");

        Scanner scanner = new Scanner(System.in);
        String UserInput = scanner.next();
        return UserInput;
    }


}
