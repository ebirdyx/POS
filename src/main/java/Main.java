import controllers.InventoryController;
import domain.POS;
import store.FileStore;
import store.Store;
import views.Console;

public class Main {
    public static void main(String[] args) {
        Store fileStore = new FileStore("db.txt");
        POS pos = new POS(fileStore);
        InventoryController inventoryController = new InventoryController(pos);
        Console console = new Console(inventoryController);
        console.run();
    }
}
