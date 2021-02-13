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

    /**
     * Create a new Item type in the inventory
     * @param name
     * @param price
     * @return
     * @throws NameAlreadyExists
     */
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

    /**
     * Checks if an Item name already exists
     * @param name
     * @return
     */
    private boolean nameExists(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns item matching code
     * @param code
     * @return
     */
    private Item getItemByCode(String code) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getCode().equals(code)) {
                return items.get(i);
            }
        }

        return null;
    }

    /**
     * Return items in inventory
     * @return
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Add additional quantity to an Item
     * @param itemCode
     * @param quantity
     * @throws ItemNotFound
     */
    public void addQuantityToItem(String itemCode, int quantity) throws ItemNotFound {
        Item item = getItemByCode(itemCode);

        if (item == null) {
            throw new ItemNotFound();
        }

        item.addQuantity(quantity);

        saveData();
    }

    /**
     * Sell quantity of an Item
     * @param itemCode
     * @param quantity
     * @throws ItemNotFound
     * @throws NotEnoughItemQuantity
     */
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

    /**
     * Seed inventory with fake data
     */
    private void seedItems() {
        Item item;
        item = new Item("Shirt", 12.57);
        items.add(item);

        item = new Item("Pants", 45.83);
        items.add(item);

        item = new Item("Socks", 3.75);
        items.add(item);
    }

    /**
     * Load data into inventory from store
     */
    public void loadData() {
         String[] serializedItems = store.loadData();

        for (int i = 0; i < serializedItems.length; i++) {
            Item item = Item.deserializeItem(serializedItems[i]);
            items.add(item);
        }
    }

    /**
     * Save inventory data using a store
     */
    public void saveData() {
        String[] serializedItems = new String[items.size()];

        for (int i = 0; i < items.size(); i++) {
            serializedItems[i] = items.get(i).serializeItem();
        }

        store.saveData(serializedItems);
    }
}
