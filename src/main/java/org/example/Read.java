package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.Random;
import java.util.UUID;

public class Read {

    private static final String FILENAME = "src/main/resources/discount.json";
    static int id=0 ;
    public static void main(String[] args) {
        JSONArray jsonArray = readJsonFile();
        Write write = new Write();

        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 5) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View all Discount");
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
                    write.main();
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
                System.out.printf("%d. %s %s, %d\n", i + 1, jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("id"));
            }
        }
    }

    private static void addRecord(Scanner scanner, JSONArray jsonArray) {
        System.out.println("Please enter the firstname:");

        String firstname = scanner.nextLine();

        System.out.println("Please enter the lastname:");
        String lastname = scanner.nextLine();

        Random rand = new Random();
        // generate a random integer between 0 and 99
        int randomNum = rand.nextInt(1000000);
        UUID uuid = UUID.randomUUID();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",22);
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
     int mo=   jsonObject.getInt("id");
        System.out.println("mo = " + mo);
        System.out.printf("Deleting record %d: %s %s, %d\n", recordNumber, jsonObject.getString("firstname"), jsonObject.getString("lastname"), jsonObject.getInt("id"));

        jsonArray.remove(recordNumber - 1);

        System.out.println("Record deleted successfully");
    }


}