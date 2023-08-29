package org.example;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class CRUDSystem2 {

    private static final String FILENAME = "src/main/resources/data.json";
    private static int nextID = 1;

    public static void main(String[] args) {

        // Load existing data from file
        JSONArray people = loadData();

        // Main program loop
        Scanner scanner = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            System.out.println("Select an operation:");
            System.out.println("1. Add a person");
            System.out.println("2. View all people");
            System.out.println("3. View a person");
            System.out.println("4. Update a person");
            System.out.println("5. Delete a person");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addPerson(people, scanner);
                    break;
                case 2:
                    viewAllPeople(people);
                    break;
                case 3:
                    viewPerson(people, scanner);
                    break;
                case 4:
                    updatePerson(people, scanner);
                    break;
                case 5:
                    deletePerson(people, scanner);
                    break;
                case 6:
                    done = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }

            // Save data back to file
            saveData(people);
        }
    }

    private static JSONArray loadData() {
        try {
            File file = new File(FILENAME);
            if (!file.exists()) {
                return new JSONArray();
            }
            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            JSONArray people = (JSONArray) parser.parse(reader);
            for (Object personObj : people) {
                JSONObject person = (JSONObject) personObj;
                int id = Integer.parseInt(person.get("id").toString());
                nextID = Math.max(nextID, id + 1);
            }
            return people;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static void saveData(JSONArray people) {
        try {
            FileWriter writer = new FileWriter(FILENAME);
            writer.write(people.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addPerson(JSONArray people, Scanner scanner) {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Has street address? (y/n): ");
        String hasStreetAddressStr = scanner.nextLine();
        boolean hasStreetAddress = hasStreetAddressStr.equalsIgnoreCase("y");
        int houseNumber = 0;
        if (hasStreetAddress) {
            System.out.print("House number: ");
            houseNumber = scanner.nextInt();
            scanner.nextLine();
        }
        JSONObject person = new JSONObject();
        person.put("id", nextID);
        person.put("firstName", firstName);
        person.put("lastName", lastName);
        person.put("email", email);
        person.put("phone", phone);
        person.put("hasStreetAddress", hasStreetAddress);
        person.put("houseNumber", houseNumber);
        people.add(person);
        System.out.println("Person added with ID " + nextID);
        nextID++;
    }

    private static void viewAllPeople(JSONArray people) {
        if (people.size() == 0) {
            System.out.println("No people to display.");
            return;
        }
        for (Object personObj : people) {
            JSONObject person = (JSONObject) personObj;
            System.out.println("=============================");
            System.out.println("ID: " + person.get("id"));
            System.out.println("First name: " + person.get("firstName"));
            System.out.println("Last name: " + person.get("lastName"));
            System.out.println("Email: " + person.get("email"));
            System.out.println("Phone: " + person.get("phone"));
            boolean hasStreetAddress = (boolean) person.get("hasStreetAddress");
            if (hasStreetAddress) {
                int houseNumber = Integer.parseInt(person.get("houseNumber").toString());
                System.out.println("Address: " + houseNumber);
            }
            System.out.println();
        }
    }

    private static void viewPerson(JSONArray people, Scanner scanner) {
        System.out.print("Enter ID of person to view: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        JSONObject person = findPerson(people, id);
        if (person == null) {
            System.out.println("Person not found.");
            return;
        }
        System.out.println("ID: " + person.get("id"));
        System.out.println("First name: " + person.get("firstName"));
        System.out.println("Last name: " + person.get("lastName"));
        System.out.println("Email: " + person.get("email"));
        System.out.println("Phone: " + person.get("phone"));
        boolean hasStreetAddress = (boolean) person.get("hasStreetAddress");
        if (hasStreetAddress) {
            int houseNumber = Integer.parseInt(person.get("houseNumber").toString());
            System.out.println("Address: " + houseNumber);
        }
        System.out.println();
    }

    private static void updatePerson(JSONArray people, Scanner scanner) {
        System.out.print("Enter ID of person to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        JSONObject person = findPerson(people, id);
        if (person == null) {
            System.out.println("Person not found.");
            return;
        }
        System.out.print("First name [" + person.get("firstName") + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) {
            person.put("firstName", firstName);
        }
        System.out.print("Last name [" + person.get("lastName") + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) {
            person.put("lastName", lastName);
        }
        System.out.print("Email [" + person.get("email") + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            person.put("email", email);
        }
        System.out.print("Phone [" + person.get("phone") + "]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            person.put("phone", phone);
        }
        boolean hasStreetAddress = (boolean) person.get("hasStreetAddress");
        if (hasStreetAddress) {
            System.out.print("Change house number? (y/n): ");
            String changeHouseNumberStr = scanner.nextLine();

            if (changeHouseNumberStr.equalsIgnoreCase("y")) {
                System.out.print("House number [" + person.get("houseNumber") + "]: ");
                String houseNumberStr = scanner.nextLine();
                if (!houseNumberStr.isEmpty()) {
                    int houseNumber = Integer.parseInt(houseNumberStr);
                    person.put("houseNumber", houseNumber);
                }
            }
        } else {
            System.out.print("Add street address? (y/n): ");
            String addStreetAddressStr = scanner.nextLine();
            if (addStreetAddressStr.equalsIgnoreCase("y")) {
                person.put("hasStreetAddress", true);
                System.out.print("House number: ");
                int houseNumber = scanner.nextInt();
                scanner.nextLine();
                person.put("houseNumber", houseNumber);
            }
        }
        System.out.println("Person updated.");
    }

    private static void deletePerson(JSONArray people, Scanner scanner) {
        System.out.print("Enter ID of person to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        JSONObject person = findPerson(people, id);
        if (person == null) {
            System.out.println("Person not found.");
            return;
        }
        people.remove(person);
        System.out.println("Person deleted.");
    }

    private static JSONObject findPerson(JSONArray people, int id) {
        for (Object personObj : people) {
            JSONObject person = (JSONObject) personObj;
            if ((int) person.get("id") == id) {
                return person;
            }
        }
        return null;
    }
}