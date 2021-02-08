package domain;

import java.util.AbstractList;
import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();    // inti array
    }

    public void addNewItems(Item item) {
        items.add(item);
    }



    public ArrayList<Item> getItems() {
        return items;
    }
}
