package domain;

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
        this.code = generateRandomString(8);

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

}
