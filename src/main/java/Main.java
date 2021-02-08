import controllers.InventoryController;
import views.Console;

public class Main {
    public static void main(String[] args){
        InventoryController inventoryController = new InventoryController();

        Console console = new Console(inventoryController);
        console.run();
    }

}

