package domain;

import utils.Randomizer;

public class User implements SerializableData {
    private String code;
    private String firstName;
    private String lastName;
    private String pin;
    private Role role;

    public User(String firstName, String lastName, String pin, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.code = Randomizer.generateRandomString(3);
        this.role = role;
    }

    private User(String code, String firstName, String lastName, String pin, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pin = pin;
        this.code = code;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "User{" +
                "code='" + code + '\'' +
                ", fullName='" + getUserFullName() + '\'' +
                ", role='" + role + '\'' +
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
        return code + ";" + firstName + ";" + lastName + ";" + pin + ";" + role;
    }

    public static User deserializeUser(String s) {
        String[] sParts = s.split(";");

        String code = sParts[0];
        String firstName = sParts[1];
        String lastName = sParts[2];
        String pin = sParts[3];
        Role role = Role.valueOf(sParts[4]);

        User user = new User(code, firstName, lastName, pin, role);
        return user;
    }
}
