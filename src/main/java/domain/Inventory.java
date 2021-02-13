package domain;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();    // inti array
    }

    public Item createNewItem(String name, double price) {
        //create item
        Item item = new Item(name,price);

        //add item to inventory
        items.add(item);

        //return generated code of the new item
        return item;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


}
