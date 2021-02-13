package controllers;

import domain.POS;
import domain.Item;
import errors.NameAlreadyExists;
import errors.NameCannotBeEmpty;
import errors.PriceCannotBeNegative;

import java.util.List;

public class InventoryController {
    private POS POS;

    public InventoryController(POS POS) {
        this.POS = POS;
    }

    public Item createNewItem(String name, double price)
            throws PriceCannotBeNegative, NameCannotBeEmpty, NameAlreadyExists {

        //price cant be negative
        if (price <= 0) {
            throw new PriceCannotBeNegative();
        }

        //name cannot be empty string
        if (name.length() == 0) {
            throw new NameCannotBeEmpty();
        }

        return this.POS.createNewItem(name, price);
    }

    public List<Item> getItems() {
        return this.POS.getItems();
    }
}

