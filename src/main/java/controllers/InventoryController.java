package controllers;

import domain.Inventory;
import domain.Item;
import errors.NameCannotBeEmpty;
import errors.PriceCannotBeNegative;

public class InventoryController {
    private Inventory inventory;

    public InventoryController(Inventory inventory) {
        this.inventory = inventory;
    }

    public String createNewItem(String name, double price)
            throws PriceCannotBeNegative, NameCannotBeEmpty {

        //price cant be negative
        if (price <= 0) {
            throw new PriceCannotBeNegative();
        }

        //name cannot be empty string
        if (name.length() == 0) {
            throw new NameCannotBeEmpty();
        }

        return this.inventory.createNewItem(name, price);
    }

}

