package controllers;

import domain.POS;
import domain.Item;
import domain.User;
import errors.*;

import java.util.List;

public class posController {
    private POS pos;

    public posController(POS pos) {
        this.pos = pos;
    }

    public Item createNewItem(String name, double cost, double price)
            throws PriceCannotBeNegative, NameCannotBeEmpty, ItemNameAlreadyExists, ItemPriceShouldBeGreaterThanCost {

        //price cant be negative
        if (price <= 0) {
            throw new PriceCannotBeNegative();
        }

        //name cannot be empty string
        if (name.length() == 0) {
            throw new NameCannotBeEmpty();
        }

        if (price < cost) {
            throw new ItemPriceShouldBeGreaterThanCost();
        }

        return this.pos.createNewItem(name, cost, price);
    }

    public List<Item> getItems() {
        return this.pos.getItems();
    }

    public List<User> getUsers() {
        return this.pos.getUsers();
    }

    public User authenticate(String pin) throws InvalidPinNumber {
        return this.pos.authenticate(pin);
    }

    public void addQuantityToItem(String codeItem, int quantity) throws ItemNotFound {
        this.pos.addQuantityToItem(codeItem, quantity);
    }

    public void sellItemQuantity(String itemCode, int quantity)
            throws ItemNotFound, NotEnoughItemQuantity {
        this.pos.sellItemQuantity(itemCode, quantity);
    }
}

