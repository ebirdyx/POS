package domain;

import errors.ItemNotFound;
import errors.NameAlreadyExists;
import errors.NotEnoughItemQuantity;
import store.Store;

import java.util.ArrayList;

public class POS {

    private ArrayList<Item> items;
    private Store store;

    public POS(Store store) {
        this.store = store;

        // inventory of items
        items = new ArrayList<Item>();

        // loading data from store
        loadData();

        // seed fake data if items array is empty after loadData
        if (items.size() == 0)
            seedItems();
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

        saveData();

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

    private Item getItemByCode(String code) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCode().equals(code)) {
                return items.get(i);
            }
        }

        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addQuantityToItem(String itemCode, int quantity) throws ItemNotFound {
        Item item = getItemByCode(itemCode);

        if (item == null) {
            throw new ItemNotFound();
        }

        item.addQuantity(quantity);

        saveData();
    }

    public void sellItemQuantity(String itemCode, int quantity)
            throws ItemNotFound, NotEnoughItemQuantity {
        Item item = getItemByCode(itemCode);

        if (item == null) {
            throw new ItemNotFound();
        }

        if (item.getQuantity() < quantity) {
            throw new NotEnoughItemQuantity();
        }

        item.sellItem(quantity);

        saveData();
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

    public void loadData() {
         String[] serializedItems = store.loadData();

        for (int i = 0; i < serializedItems.length; i++) {
            Item item = Item.deserializeItem(serializedItems[i]);
            items.add(item);
        }
    }

    public void saveData() {
        String[] serializedItems = new String[items.size()];

        for (int i = 0; i < items.size(); i++) {
            serializedItems[i] = items.get(i).serializeItem();
        }

        store.saveData(serializedItems);
    }
}
