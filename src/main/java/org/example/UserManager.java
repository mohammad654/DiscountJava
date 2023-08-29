package org.example;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class UserManager {
    private static final String FILE_NAME = "src/main/resources/users.json";
    private static List<User> users;
    private static int nextId;

    static {
        users = new ArrayList<>();
        nextId = 1;
        loadUsers();
    }

    private static void loadUsers() {
        try {
            FileReader reader = new FileReader(FILE_NAME);
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
            for (Object o : jsonArray) {
                JSONObject obj = (JSONObject) o;
                User user = User.fromJSON(obj);
                users.add(user);
                nextId = Math.max(nextId, user.getId() + 1);
            }
        } catch (Exception e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            JSONArray jsonArray = new JSONArray();
            for (User user : users) {
                jsonArray.add(user.toJSON());
            }
            writer.write(jsonArray.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static boolean register(String firstName, String lastName, String email, String phone, boolean hasStreet, String houseNumber) {
        // Check if email is already registered
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }

        // Create new user
        User user = new User(nextId++, firstName, lastName, email, phone, hasStreet, houseNumber);
        users.add(user);
        saveUsers();
        return true;
    }

    public static User login(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
