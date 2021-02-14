package domain;

import errors.*;
import store.Store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class POS {

    private ArrayList<Item> items;
    private ArrayList<User> users;
    private ArrayList<Sale> sales;

    private Store store;

    public POS(Store store) {
        this.store = store;

        // inventory of items
        items = new ArrayList<Item>();
        users = new ArrayList<User>();
        sales = new ArrayList<Sale>();

        // loading data from store
        loadData();

        // seed fake data if items array is empty after loadData
        if (items.size() == 0)
            seedItems();

        // seed fake data if items array is empty after loadData
        if (users.size() == 0)
            seedUsers();

        // seed fake data if items array is empty after loadData
        if (sales.size() == 0)
            seedSales();

        // save data to store
        saveData();
    }

    /**
     * Create a new Item type in the inventory
     *
     * @param name
     * @param price
     * @return
     * @throws ItemNameAlreadyExists
     */
    public Item createNewItem(String name, double cost, double price) throws ItemNameAlreadyExists {
        // check if an item with the same name already exists
        if (itemNameExists(name)) {
            throw new ItemNameAlreadyExists();
        }

        //create item
        Item item = new Item(name, cost, price);

        //add item to inventory
        items.add(item);

        saveData();

        //return generated code of the new item
        return item;
    }

    public User createNewUser(String firstName, String lastName, String pin, Role role) throws UserPinAlreadyExists {
        if (userPinExists(pin)) {
            throw new UserPinAlreadyExists();
        }

        User user = new User(firstName, lastName, pin, role);
        users.add(user);

        saveData();

        return user;
    }

    /**
     * Checks if an Item name already exists
     *
     * @param name
     * @return
     */
    private boolean itemNameExists(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    private boolean userPinExists(String pin) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPin().equals(pin)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns item matching code
     *
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

    public User getUserByCode(String code) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCode().equals(code)) {
                return users.get(i);
            }
        }

        return null;
    }

    /**
     * Return items in inventory
     *
     * @return
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public User switchUserStatus(String code) throws UserNotFound {
        User user = getUserByCode(code);
        if (user == null) {
            throw new UserNotFound();
        }
        user.switchStatus();
        saveData();
        return user;
    }
    public User changeUserRole(String code) throws UserNotFound {
        User user = getUserByCode(code);
        if (user == null) {
            throw new UserNotFound();
        }
        user.changeUserRole();
        saveData();
        return user;
    }


    public User authenticate(String pin) throws InvalidPinNumber {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).authenticate(pin)) {
                return users.get(i);
            }
        }

        throw new InvalidPinNumber();
    }

    /**
     * Add additional quantity to an Item
     *
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
     *
     * @param itemCode
     * @param quantity
     * @throws ItemNotFound
     * @throws NotEnoughItemQuantity
     */
    public Sale sellItemQuantity(User user, String itemCode, int quantity)
            throws ItemNotFound, NotEnoughItemQuantity {
        Item item = getItemByCode(itemCode);

        if (item == null) {
            throw new ItemNotFound();
        }

        if (item.getQuantity() < quantity) {
            throw new NotEnoughItemQuantity();
        }

        item.sellItem(quantity);

        Sale sale = new Sale(user, item, quantity);
        sales.add(sale);

        saveData();

        return sale;
    }

    /**
     * Seed inventory with fake data
     */
    private void seedItems() {
        Item item;
        item = new Item("Shirt", 12.57, 20.85);
        item.addQuantity(250);
        items.add(item);

        item = new Item("Pants", 45.83, 80.28);
        item.addQuantity(467);
        items.add(item);

        item = new Item("Socks", 3.75, 12.58);
        item.addQuantity(385);
        items.add(item);
    }

    /**
     * Seed users with fake data
     */
    private void seedUsers() {
        User user;
        user = new User("Pamela", "Brahollari", "354", Role.ADMIN);
        users.add(user);

        user = new User("Rebekah", "Cala", "579", Role.SELLER);
        users.add(user);

        user = new User("Emy", "Lela", "234", Role.SELLER);
        users.add(user);
    }

    private void seedSales() {
        int quantitySample = 6;

        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < items.size(); j++) {
                items.get(j).sellItem(quantitySample);
                Sale sale = new Sale(users.get(i), items.get(j), quantitySample);
                sales.add(sale);
            }
        }
    }

    /**
     * Load data into inventory from store
     */
    public void loadData() {
        String[] serializedItems = store.loadData();

        for (int i = 0; i < serializedItems.length; i++) {
            String[] sParts = serializedItems[i].split(";");
            String typeOfData = sParts[0];
            String data = String.join(";", Arrays.copyOfRange(sParts, 1, sParts.length));

            switch (typeOfData) {
                case "Item":
                    loadItem(data);
                    break;
                case "User":
                    loadUser(data);
                    break;
                case "Sale":
                    loadSale(data);
                    break;
            }
        }
    }

    private void loadItem(String s) {
        Item item = Item.deserializeItem(s);
        items.add(item);
    }

    private void loadUser(String s) {
        User user = User.deserializeUser(s);
        users.add(user);
    }

    private void loadSale(String s) {
        Sale sale = null;
        try {
            sale = Sale.deserialize(s, users, items);
        } catch (ErrorDeserializingData errorDeserializingData) {
            errorDeserializingData.printStackTrace();
        }
        sales.add(sale);
    }

    /**
     * Save inventory data using a store
     */
    public void saveData() {
        List<String> serializedData = new ArrayList<String>();

        serializedData.addAll(getData("User", users));
        serializedData.addAll(getData("Item", items));
        serializedData.addAll(getData("Sale", sales));

        store.saveData(serializedData.toArray(new String[serializedData.size()]));
    }

    private List<String> getData(String dataType, List data) {
        List<String> serializedData = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            // Cast data element to a Serializable data type
            SerializableData serializableData = (SerializableData) data.get(i);

            // Create a serialized data prefixed with type
            serializedData.add(dataType + ";" + serializableData.serialize());
        }

        return serializedData;
    }
}
