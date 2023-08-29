package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CreateDiscount {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private int boatTypeId;
    private float percentage;

    // Constructor
    public CreateDiscount(int boatTypeId, float percentage) {
        this.id = getNextId();
        this.startDate = LocalDate.now();
        this.endDate = LocalDate.now().plusDays(7);
        this.boatTypeId = boatTypeId;
        this.percentage = percentage;
    }

    // Getter and setter methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getBoatTypeId() {
        return boatTypeId;
    }

    public void setBoatTypeId(int boatTypeId) {
        this.boatTypeId = boatTypeId;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    // Method to get the next ID for a discount
    private int getNextId() {
        int nextId = 1;

        try {
            File file = new File("discounts.json");

            // If the file does not exist, return 1
            if (!file.exists()) {
                return nextId;
            }

            // Read the JSON file and get the highest ID value
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new Scanner(file).useDelimiter("\\Z").next());

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                int id = Integer.parseInt(jsonObject.get("id").toString());

                if (id >= nextId) {
                    nextId = id + 1;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return nextId;
    }

    // Method to save the discount to the JSON file
    public void saveDiscount() {
        try {
            File file = new File("src/main/resources/discounts.json");

            // If the file does not exist, create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // Read the JSON file and add the new discount to the array
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new Scanner(file).useDelimiter("\\Z").next());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", this.id);
            jsonObject.put("startDate", this.startDate.toString());
            jsonObject.put("endDate", this.endDate.toString());
            jsonObject.put("boatTypeId", this.boatTypeId);
            jsonObject.put("percentage", this.percentage);

            jsonArray.add(jsonObject);

            // Write the updated JSON array back to the file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.close();

            System.out.println("Discount created successfully.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Main method to create a new discount
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the boat type ID (1, 2, 3, or 4): ");
        int boatTypeId = scanner.nextInt();

        System.out.println("Enter the discount percentage (e.g. 10.5): ");
        float percentage = scanner.nextFloat();

        CreateDiscount discount = new CreateDiscount(boatTypeId, percentage);
        discount.saveDiscount();

        scanner.close();
    }
}
