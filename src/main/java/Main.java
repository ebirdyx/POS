import controllers.InventoryController;
import domain.POS;
import views.Console;

public class Main {
    public static void main(String[] args){
        POS pos = new POS(true);
        InventoryController inventoryController = new InventoryController(pos);
        Console console = new Console(inventoryController);
        console.run();
    }
}
