package domain;

import errors.NameAlreadyExists;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<Item>();    // inti array
    }

    public Item createNewItem(String name, double price) throws NameAlreadyExists {
        // check if an item with the same name already exists
        if (nameExists(name)) {
            throw new NameAlreadyExists();
        }

        //create item
        Item item = new Item(name,price);

        //add item to inventory
        items.add(item);

        //return generated code of the new item
        return item;
    }

    private boolean nameExists(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


}
