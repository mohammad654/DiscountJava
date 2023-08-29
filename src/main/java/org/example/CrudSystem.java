package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CrudSystem {

    private static final String FILENAME = "src/main/resources/data.json";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Select an option:");
            System.out.println("1. Add a record");
            System.out.println("2. View all records");
            System.out.println("3. Update a record");
            System.out.println("4. Delete a record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addRecord();
                    break;
                case 2:
                    viewAllRecords();
                    break;
                case 3:
                    updateRecord();
                    break;
                case 4:
                    deleteRecord();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void addRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Street? (Yes/No): ");
        String street = scanner.nextLine();

        String houseNumber = "";
        if (street.equalsIgnoreCase("yes")) {
            System.out.print("Enter house number: ");
            houseNumber = scanner.nextLine();
        }

        JSONObject record = new JSONObject();
        record.put("id", getNextId());
        record.put("firstName", firstName);
        record.put("lastName", lastName);
        record.put("email", email);
        record.put("phone", phone);
        record.put("street", street);
        record.put("houseNumber", houseNumber);

        JSONArray records = readRecords();
        records.put(record);

        writeRecords(records);

        System.out.println("Record added successfully.");

        scanner.close();
    }

    private static void viewAllRecords() {
        JSONArray records = readRecords();

        if (records.length() == 0) {
            System.out.println("No records found.");
            return;
        }

        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            System.out.println("ID: " + record.getInt("id"));
            System.out.println("First Name: " + record.getString("firstName"));
            System.out.println("Last Name: " + record.getString("lastName"));
            System.out.println("Email: " + record.getString("email"));
            System.out.println("Phone: " + record.getString("phone"));
            System.out.println("Street: " + record.getString("street"));
            if (record.getString("street").equalsIgnoreCase("yes")) {
                System.out.println("House Number: " + record.getString("houseNumber"));
            }
            System.out.println("---------------------------");
        }
    }

    private static void updateRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of record to update: ");
        int id = scanner.nextInt();

        JSONArray records = readRecords();
        boolean found = false;

        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            if (record.getInt("id") == id) {
                found = true;
                System.out.print("Enter new first name (blank to keep current value of " + record.getString("firstName") + "): ");
                String firstName = scanner.nextLine();
                if (firstName.isEmpty()) {
                    firstName = record.getString("firstName");
                }

                System.out.print("Enter new last name (blank to keep current value of " + record.getString("lastName") + "): ");
                String lastName = scanner.nextLine();
                if (lastName.isEmpty()) {
                    lastName = record.getString("lastName");
                }

                System.out.print("Enter new email (blank to keep current value of " + record.getString("email") + "): ");
                String email = scanner.nextLine();
                if (email.isEmpty()) {
                    email = record.getString("email");
                }

                System.out.print("Enter new phone (blank to keep current value of " + record.getString("phone") + "): ");
                String phone = scanner.nextLine();
                if (phone.isEmpty()) {
                    phone = record.getString("phone");
                }

                System.out.print("Street? (Yes/No): ");
                String street = scanner.nextLine();

                String houseNumber = "";
                if (street.equalsIgnoreCase("yes")) {
                    System.out.print("Enter new house number: ");
                    houseNumber = scanner.nextLine();
                }

                record.put("firstName", firstName);
                record.put("lastName", lastName);
                record.put("email", email);
                record.put("phone", phone);
                record.put("street", street);
                record.put("houseNumber", houseNumber);

                writeRecords(records);

                System.out.println("Record updated successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Record not found.");
        }

        scanner.close();
    }

    private static void deleteRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter ID of record to delete: ");
        int id = scanner.nextInt();

        JSONArray records = readRecords();
        boolean found = false;

        for (int i = 0; i < records.length(); i++) {
            JSONObject record = records.getJSONObject(i);
            if (record.getInt("id") == id) {
                found = true;
                records.remove(i);
                writeRecords(records);
                System.out.println("Record deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Record not found.");
        }

        scanner.close();
    }

    private static int getNextId() {
        JSONArray records = readRecords();
        if (records.length() == 0) {
            return 1;
        } else {
            JSONObject lastRecord = records.getJSONObject(records.length() - 1);
            return lastRecord.getInt("id") + 1;
        }
    }

    private static JSONArray readRecords() {
        try {
            String data = new String(Files.readAllBytes(Paths.get(FILENAME)));
            return new JSONArray(data);
        } catch (IOException e) {
            return new JSONArray();
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    private static void writeRecords(JSONArray records) {
        try {
            Files.write(Paths.get(FILENAME), records.toString().getBytes());
        } catch (IOException e) {
            System.out.println("Unable to write to file.");
        }
    }
}