package domain;

import utils.Randomizer;

public class User implements SerializableData {
    private String code;
    private String firstName;
    private String lastName;
    private String pin;

    public User(String firstName, String lastName, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.code = Randomizer.generateRandomString(3);
    }

    private User(String code, String firstName, String lastName, String pin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", fullName='" + getUserFullName() + '\'' +
                '}';
    }

    public String getPin() {
        return pin;
    }

    public String getUserFullName() {
        return firstName + ", " + lastName;
    }

    public boolean authenticate(String pin) {
        return this.pin.equals(pin);
    }

    @Override
    public String serialize() {
        return code + ";" + firstName + ";" + lastName + ";" + pin;
    }

    public static User deserializeUser(String s) {
        String[] sParts = s.split(";");

        String code = sParts[0];
        String firstName = sParts[1];
        String lastName = sParts[2];
        String pin = sParts[3];

        User user = new User(code, firstName, lastName, pin);
        return user;
    }
}
