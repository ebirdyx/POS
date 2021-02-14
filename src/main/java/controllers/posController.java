package controllers;

import domain.*;
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

    public User createNewUser(String firstName, String lastName, String pin, Role role)
            throws NameCannotBeEmpty, UserPinAlreadyExists, PinCannotBeEmpty {
        if (firstName.length() == 0 || lastName.length() == 0) {
            throw new NameCannotBeEmpty();
        }
        if(pin.length() == 0){
            throw new PinCannotBeEmpty();
        }

        return this.pos.createNewUser(firstName, lastName, pin, role);
    }

    public List<Item> getItems() {
        return this.pos.getItems();
    }

    public List<User> getUsers() {
        return this.pos.getUsers();
    }

    public List<Sale> getSales() {
        return this.pos.getSales();
    }
    public User switchUserStatus(String code) throws UserNotFound {
        return this.pos.switchUserStatus(code);
    }

    public User changeUserRole(String code) throws UserNotFound {
        return this.pos.changeUserRole(code);
    }

    public User authenticate(String pin) throws InvalidPinNumber {
        return this.pos.authenticate(pin);
    }

    public void addQuantityToItem(String codeItem, int quantity) throws ItemNotFound {
        this.pos.addQuantityToItem(codeItem, quantity);
    }

    public Sale sellItemQuantity(User user, String itemCode, int quantity)
            throws ItemNotFound, NotEnoughItemQuantity {
        return this.pos.sellItemQuantity(user, itemCode, quantity);
    }
}

