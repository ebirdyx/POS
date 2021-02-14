package models;

import errors.ErrorDeserializingData;
import utils.Converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Sale implements SerializableData {
    private final User user;
    private final Item item;
    private final int soldQuantity;
    private final Date soldOn;

    public Sale(User user, Item item, int soldQuantity) {
        this.user = user;
        this.item = item;
        this.soldQuantity = soldQuantity;
        this.soldOn = new Date();
    }

    private Sale(User user, Item item, int soldQuantity, Date soldOn) {
        this.user = user;
        this.item = item;
        this.soldQuantity = soldQuantity;
        this.soldOn = soldOn;
    }

    public double getTotalPrice() {
        return item.getPrice() * soldQuantity;
    }

    @Override
    public String toString() {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return "Sale{" +
                "user=" + user.getUserFullName() +
                ", item=" + item.getName() +
                ", quantity=" + soldQuantity +
                ", soldOn=" + simpleDateFormat.format(soldOn) +
                ", TotalPrice=" + getTotalPrice() +
                '}';
    }

    @Override
    public String serialize() {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String soldOnFormatted = simpleDateFormat.format(soldOn);

        return user.getCode() + ";" + item.getCode() + ";" + soldQuantity + ";" + soldOnFormatted;
    }

    public static Sale deserialize(String s, List<User> userList, List<Item> itemList)
            throws ErrorDeserializingData {
        String pattern = "yyyy-MM-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String[] sParts = s.split(";");

        String userCode = sParts[0];
        String itemCode = sParts[1];
        int soldQuantity = Converters.stringToInteger(sParts[2]);

        Date soldOn;
        try {
            soldOn = simpleDateFormat.parse(sParts[3]);
        } catch (ParseException e) {
            soldOn = null;
        }

        User user = null;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getCode().equals(userCode)) {
                user = userList.get(i);
            }
        }

        Item item = null;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getCode().equals(itemCode)) {
                item = itemList.get(i);
            }
        }

        if (user == null || item == null|| soldOn == null) {
            throw new ErrorDeserializingData();
        }

        return new Sale(user, item, soldQuantity, soldOn);
    }
}
