package domain;

import utils.Converters;

import static utils.Randomizer.generateRandomString;

public class Item {
    private String code;
    private String name;
    private double price;
    private int quantity;
    private int soldQuantity;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.code = generateRandomString(3);
    }

    private Item(String code, String name, double price) {
        this.name = name;
        this.price = price;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void sellItem(int quantity) {
        this.quantity -= quantity;
        this.soldQuantity += quantity;
    }

    public String getCode() {
        return code;
    }

    /**
     * Convert an item into a string form of itself that can be saved
     * @return
     */
    public String serializeItem() {
        return code + ";" + price + ";" + name + ";" + quantity + ";" + soldQuantity;
    }

    public static Item deserializeItem(String s) {
        String[] sParts = s.split(";");

        String c = sParts[0];
        double p = Converters.stringToDouble(sParts[1]);
        String n = sParts[2];
        int q = Converters.stringToInteger(sParts[3]);
        int soldQ = Converters.stringToInteger(sParts[4]);

        Item item = new Item(c, n, p);
        item.quantity = q;
        item.soldQuantity = soldQ;

        return item;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", soldQuantity=" + soldQuantity +
                '}';
    }
}
