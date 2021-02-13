package domain;

import errors.ItemNotFound;
import errors.NameAlreadyExists;

import java.util.ArrayList;

public class POS {

    private ArrayList<Item> items;

    public POS(boolean seedFakedData) {
        items = new ArrayList<Item>();    // inti array

        if (seedFakedData) {
            seedItems();
        }
    }

    public Item createNewItem(String name, double price) throws NameAlreadyExists {
        // check if an item with the same name already exists
        if (nameExists(name)) {
            throw new NameAlreadyExists();
        }

        //create item
        Item item = new Item(name, price);

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

    public void addQuantityToItem(String itemCode, int quantity) throws ItemNotFound {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCode().equals(itemCode)) {
                items.get(i).addQuantity(quantity);
                return;
            }
        }

        throw new ItemNotFound();
    }

    private void seedItems() {
        Item item;
        item = new Item("Shirt", 12.57);
        items.add(item);

        item = new Item("Pants", 45.83);
        items.add(item);

        item = new Item("Socks", 3.75);
        items.add(item);
    }
}
