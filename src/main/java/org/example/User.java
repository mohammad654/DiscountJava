package org.example;

import org.json.simple.JSONObject;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean hasStreet;
    private String houseNumber;

    public User(int id, String firstName, String lastName, String email, String phone, boolean hasStreet, String houseNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.hasStreet = hasStreet;
        this.houseNumber = houseNumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean hasStreet() {
        return hasStreet;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("firstName", firstName);
        obj.put("lastName", lastName);
        obj.put("email", email);
        obj.put("phone", phone);
        obj.put("hasStreet", hasStreet);
        obj.put("houseNumber", houseNumber);
        return obj;
    }

    public static User fromJSON(JSONObject obj) {
        int id = ((Long) obj.get("id")).intValue();
        String firstName = (String) obj.get("firstName");
        String lastName = (String) obj.get("lastName");
        String email = (String) obj.get("email");
        String phone = (String) obj.get("phone");
        boolean hasStreet = (boolean) obj.get("hasStreet");
        String houseNumber = (String) obj.get("houseNumber");
        return new User(id, firstName, lastName, email, phone, hasStreet, houseNumber);
    }
}
