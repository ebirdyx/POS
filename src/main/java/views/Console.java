package views;

import controllers.posController;
import domain.Item;
import domain.Role;
import domain.Sale;
import domain.User;
import errors.*;
import utils.Converters;

import java.util.List;
import java.util.Scanner;

public class Console {

    private User user;
    private Scanner scanner;
    private posController posController;

    public Console(posController posController) {
        this.posController = posController;
        scanner = new Scanner(System.in);
    }

    public void run() {
        login();
    }

    public void login() {
        displayTitle("POS login");
        String pin = getUserInput("Please enter your pin number: ");

        try {
            user = this.posController.authenticate(pin);
            System.out.println("Logged as " + user.toString());
            System.out.println();
            displayMainMenu();
        } catch (InvalidPinNumber invalidPinNumber) {
            System.out.println("Incorrect login pin number");
            login();
        }
    }

    public void createItemMenu() {
        //  show item menu
        displayTitle("Create new item Menu");

        // some logic to get item name and price from user
        //  ask the user for name
        String name = getUserInput("Please enter the item's name: ");

        // ask the user for cost
        String costString = getUserInput("Please enter the item's cost: ");

        // ask the user for price
        String priceString = getUserInput("Please enter the item's price: ");

        // convert price to double
        double cost = Converters.stringToDouble(costString);
        double price = Converters.stringToDouble(priceString);

        //create new item with userInput
        try {
            Item newItem = this.posController.createNewItem(name, cost, price);
            System.out.println(newItem.toString());
        } catch (PriceCannotBeNegative e) {
            System.out.println("Price cannot be negative.");
            createItemMenu();
        } catch (NameCannotBeEmpty e) {
            System.out.println("Name cannot be empty.");
            createItemMenu();
        } catch (ItemNameAlreadyExists e) {
            System.out.println("Name already exists.");
            createItemMenu();
        } catch (ItemPriceShouldBeGreaterThanCost itemPriceShouldBeGreaterThanCost) {
            System.out.println("Item price should be greater than cost.");
            createItemMenu();
        }

        System.out.println();
    }

    public void displayInventoryItems() {
        displayTitle("Listing of inventory menu:");

        List<Item> items = this.posController.getItems();
        //  show item menu
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }

        System.out.println();
    }

    public void addQuantityToItemMenu() {
        displayTitle("Add quantity to item menu:");
        displayInventoryItems();
        String codeItem = getUserInput("Please enter item code: ");
        String quantityString = getUserInput("Please enter quantity to add: ");
        try {
            this.posController.addQuantityToItem(codeItem, Converters.stringToInteger(quantityString));
            System.out.println("Item quantity successfully added!");
        } catch (ItemNotFound itemNotFound) {
            System.out.println("I was not able to find your item");
        }

        System.out.println();
    }

    public void displayTitle(String msg) {
        System.out.println(msg);
        for (int i = 0; i < msg.length(); i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public void sellItemMenu() {
        displayTitle("Sell item menu");
        displayInventoryItems();
        String codeItem = getUserInput("Please enter item code: ");
        String quantityString = getUserInput("Please enter quantity to sell: ");
        try {
            Sale sale = this.posController.sellItemQuantity(user, codeItem, Converters.stringToInteger(quantityString));
            System.out.println(sale.toString());
            System.out.println("Item quantity successfully sold!");
        } catch (ItemNotFound itemNotFound) {
            System.out.println("I was not able to find your item");
        } catch (NotEnoughItemQuantity notEnoughItemQuantity) {
            System.out.println("Not enough quantity");
        }

        System.out.println();
    }

    public void displaySalesReport() {
        displayTitle("Sales report");

        List<Sale> sales = this.posController.getSales();
        for (int i = 0; i < sales.size(); i++) {
            System.out.println(sales.get(i).toString());
        }

        System.out.println();
    }

    public void createNewUser() {
        displayTitle("Add new user");

        String firstName = getUserInput("Please enter the user's first name: ");
        String lastName = getUserInput("Please enter the user's last last name: ");
        String pin = getUserInput("Please enter the pin: ");
        String roleString = getUserInput("Please enter the user's role: ").toUpperCase();

        Role role = Role.valueOf(roleString);

        User newUser = null;
        try {
            newUser = this.posController.createNewUser(firstName, lastName, pin, role);
        } catch (NameCannotBeEmpty nameCannotBeEmpty) {
            System.out.println("First name and last name cannot be empty.");
            createNewUser();
        } catch (UserPinAlreadyExists userPinAlreadyExists) {
            System.out.println("User pin already exists.");
            createNewUser();
        } catch (PinCannotBeEmpty pinCannotBeEmpty) {
            System.out.println("User pin cannot be empty.");
            createNewUser();
        }
        System.out.println(newUser.toString());
    }

    public void listUsers() {
        displayTitle("list of Users:");

        List<User> users = this.posController.getUsers();
        //  show users
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).toString());
        }

        System.out.println();
    }

    public void disableUser() {

    }
    //TODO sort items according to sales
    //TODO sort sellers according to sales

    public void manageUsersMenu() {
        displayTitle("Manage users");
        System.out.println("1. Add user");
        System.out.println("2. Disable user");
        System.out.println("3. List users");
        System.out.println("4. Change user role");
        System.out.println("m. Main menu");

        switch (getUserInput("Please choose an option: ").toLowerCase()) {
            case "1":
                createNewUser();
                break;
            case "2":
                //  disableUser();
                break;
            case "3":
                listUsers();
                break;
            case "4":
                //  changeUserRoleMenu();
                break;
            case "m":
                displayMainMenu();
            default:
                System.out.println("Invalid Input");

        }
    }

    public void displayMainMenu() {
        displayTitle("POS system");
        System.out.println("1. Create new Item");
        System.out.println("2. List Items ");
        System.out.println("3. Add quantity to inventory");
        System.out.println("4. Sell item");

        if (user.getRole().equals(Role.ADMIN)) {

            System.out.println("5. Print sale report");
            System.out.println("6. Manage users");
        }
        System.out.println("l. Logout");
        System.out.println("q. Quit ");

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
                sellItemMenu();
                break;
            case "5":
                displaySalesReport();
                break;
            case "6":
                manageUsersMenu();
                break;
            case "l":
                // Assign null to user property
                // to remove previous logged in user
                user = null;
                login();
            case "q":
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
        System.out.println();
        return UserInput;
    }
}
