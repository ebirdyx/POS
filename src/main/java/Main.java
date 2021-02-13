import controllers.posController;
import domain.POS;
import store.FileStore;
import store.Store;
import views.Console;

public class Main {
    public static void main(String[] args) {
        Store fileStore = new FileStore("db.txt");
        POS pos = new POS(fileStore);
        posController posController = new posController(pos);
        Console console = new Console(posController);
        console.run();
    }
}
