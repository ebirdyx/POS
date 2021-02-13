import controllers.InventoryController;
import domain.Inventory;
import views.Console;

public class Main {
    public static void main(String[] args){
        Inventory inv = new Inventory();
        InventoryController inventoryController = new InventoryController(inv);
        Console console = new Console(inventoryController);
        console.run();
    }
}
