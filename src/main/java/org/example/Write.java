package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Write {

    private static final String FILENAME = "src/main/resources/discounts.json";

    public static void main() {
        JSONArray jsonArray = readJsonFile();
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) {
            System.out.println("\nPlease select an option:666666666");
            System.out.println("1. View all Discount1111");
            System.out.println("2. Add a Discount");
            System.out.println("3. Edit a Discounts");
            System.out.println("4. Delete a Discount");
            System.out.println("5. Exit");
            System.out.println("66666");
            System.out.print("Choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    viewRecords(jsonArray);
                    break;
                case 2:
                    addRecord(scanner, jsonArray);
                    writeJsonFile(jsonArray);
                    break;
                case 3:
                    editRecord(scanner, jsonArray);
                    writeJsonFile(jsonArray);
                    break;
                case 4:
                    deleteRecord(scanner, jsonArray);
                    writeJsonFile(jsonArray);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    break;
                case 6:
                    System.out.println("666666666666");
                    break;
                default:

                    System.out.println("Invalid choice, please try again");
                    break;
            }
        }

        scanner.close();
    }

    private static JSONArray readJsonFile() {
        try (FileReader reader = new FileReader(FILENAME)) {
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONArray(tokener);
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return new JSONArray();
        }
    }

    private static void writeJsonFile(JSONArray jsonArray) {
        try (FileWriter writer = new FileWriter(FILENAME)) {
            writer.write(jsonArray.toString(4)); // use 4-space indentation
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void viewRecords(JSONArray jsonArray) {
        if (jsonArray.length() == 0) {
            System.out.println("No records found");
        } else {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                System.out.printf("%d. %s %s, %d\n", i + 1,jsonObject.getInt("Id"), jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("age"));
                System.out.printf("%d. %s %s, %d\n", i + 1, jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("id")," =>");
            }
        }
    }

    private static void addRecord(Scanner scanner, JSONArray jsonArray) {
        System.out.println("Please enter the firstname:");
        int id = 0;
        String firstname = scanner.nextLine();

        System.out.println("Please enter the lastname:");
        String lastname = scanner.nextLine();

        id += 1;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
//        jsonObject.put("Id", id);
        jsonObject.put("firstname", firstname);
        jsonObject.put("lastname", lastname);


        jsonArray.put(jsonObject);
        System.out.println("Record added successfully");
    }

    private static void editRecord(Scanner scanner, JSONArray jsonArray) {
        System.out.println("Please enter the record number you want to edit:");
        int recordNumber = scanner.nextInt();
        scanner.nextLine();
        if (recordNumber < 1 || recordNumber > jsonArray.length()) {
            System.out.println("Invalid record number");
            return;
        }

        JSONObject jsonObject = jsonArray.getJSONObject(recordNumber - 1);

        System.out.printf("Editing record %d: %s %s, %d\n", recordNumber, jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("id"));

        System.out.println("Please enter the new firstname (leave blank to keep the same):");
        String firstname = scanner.nextLine();
        if (!firstname.isBlank()) {
            jsonObject.put("firstname", firstname);
        }

        System.out.println("Please enter the new lastname (leave blank to keep the same):");
        String lastname = scanner.nextLine();
        if (!lastname.isBlank()) {
            jsonObject.put("lastname", lastname);
        }

        System.out.println("Please enter the new id (enter 0 to keep the same):");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id != 0) {
            jsonObject.put("id", id);
        }

        System.out.println("Record updated successfully");
    }

    private static void deleteRecord(Scanner scanner, JSONArray jsonArray) {
        System.out.println("Please enter the record number you want to delete:");
        int recordNumber = scanner.nextInt();
        scanner.nextLine();

        if (recordNumber < 1 || recordNumber > jsonArray.length()) {
            System.out.println("Invalid record number");
            return;
        }

        JSONObject jsonObject = jsonArray.getJSONObject(recordNumber - 1);

        System.out.printf("Deleting record %d: %s %s, %d\n", recordNumber, jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("id"));

        jsonArray.remove(recordNumber - 1);

        System.out.println("Record deleted successfully");
    }


}