package controllers;

import domain.POS;
import domain.Item;
import errors.*;

import java.util.List;

public class InventoryController {
    private POS pos;

    public InventoryController(POS pos) {
        this.pos = pos;
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

        return this.pos.createNewItem(name, price);
    }

    public List<Item> getItems() {
        return this.pos.getItems();
    }

    public void addQuantityToItem(String codeItem, int quantity) throws ItemNotFound {
        this.pos.addQuantityToItem(codeItem, quantity);
    }

    public void sellItemQuantity(String itemCode, int quantity)
            throws ItemNotFound, NotEnoughItemQuantity {
        this.pos.sellItemQuantity(itemCode, quantity);
    }
}

