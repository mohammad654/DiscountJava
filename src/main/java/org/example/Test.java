package org.example;

import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class Test {
    static Scanner sc = new Scanner(System.in);
    static JSONArray data = new JSONArray();
    static int nextId = 1;
    static String filename = "src/main/resources/data.json";


    public static void main(String[] args) {
        loadData();
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            int option = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (option) {
                case 1:
                    create();
                    break;
                case 2:
                    read();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    saveData();
                    System.exit(0);
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static void create() {
        JSONObject obj = new JSONObject();
        obj.put("id", nextId++);
        System.out.print("Enter first name: ");
        obj.put("firstName", sc.nextLine());
        System.out.print("Enter last name: ");
        obj.put("lastName", sc.nextLine());
        System.out.print("Enter email: ");
        obj.put("email", sc.nextLine());
        System.out.print("Enter phone number: ");
        obj.put("phone", sc.nextLine());
        System.out.print("Enter street (Yes/No): ");
        String streetOption = sc.nextLine();
        obj.put("street", streetOption);
        if (streetOption.equalsIgnoreCase("yes")) {
            System.out.print("Enter house number: ");
            obj.put("houseNumber", sc.nextInt());
            sc.nextLine(); // consume newline
        }
        data.add(obj);
        System.out.println("Record added successfully");
    }

    static void read() {
        System.out.print("Enter ID to search: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        for (Object obj : data) {
            JSONObject record = (JSONObject) obj;
            if ((long) record.get("id") == id) {
                System.out.println("ID: " + record.get("id"));
                System.out.println("First name: " + record.get("firstName"));
                System.out.println("Last name: " + record.get("lastName"));
                System.out.println("Email: " + record.get("email"));
                System.out.println("Phone: " + record.get("phone"));
                if (record.get("street").equals("yes")) {
                    System.out.println("House number: " + record.get("houseNumber"));
                }
                return;
            }
        }
        System.out.println("Record not found");
    }

    static void update() {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        for (Object obj : data) {
            JSONObject record = (JSONObject) obj;
            if ((long) record.get("id") == id) {
                System.out.print("Enter new first name (leave blank to keep existing value): ");
                String firstName = sc.nextLine();
                if (!firstName.isBlank()) {
                    record.put("firstName", firstName);
                }
                System.out.print("Enter new last name (leave blank to keep existing value): ");
                String lastName = sc.nextLine();
                if (!lastName.isBlank()) {
                    record.put("lastName", lastName);
                }
                System.out.print("Enter new email (leave blank to keep existing value): ");
                String email = sc.nextLine();
                if (!email.isBlank()) {
                    record.put("email", email);
                }
                System.out.print("Enter new phone number (leave blank to keep existing value): ");
                String phone = sc.nextLine();
                if (!phone.isBlank()) {
                    record.put("phone", phone);
                }
                if (record.get("street").equals("yes")) {
                    System.out.print("Enter new house number (leave blank to keep existing value): ");
                    String houseNumberStr = sc.nextLine();
                    if (!houseNumberStr.isBlank()) {
                        int houseNumber = Integer.parseInt(houseNumberStr);
                        record.put("houseNumber", houseNumber);
                    }
                }
                System.out.println("Record updated successfully");
                return;
            }
        }
        System.out.println("Record not found");
    }

    static void delete() {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        for (int i = 0; i < data.size(); i++) {
            JSONObject record = (JSONObject) data.get(i);
            if ((long) record.get("id") == id) {
                data.remove(i);
                System.out.println("Record deleted successfully");
                return;
            }
        }
        System.out.println("Record not found");
    }

    static void loadData() {
        try {
            FileReader fileReader = new FileReader(filename);
            JSONParser jsonParser = new JSONParser();
            data = (JSONArray) jsonParser.parse(fileReader);
            nextId = (int) ((JSONObject) data.get(data.size() - 1)).get("id") + 1;
            fileReader.close();
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    static void saveData() {
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(data.toJSONString());
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}