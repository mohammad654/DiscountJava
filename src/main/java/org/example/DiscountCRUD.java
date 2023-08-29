package org.example;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DiscountCRUD {

    private static final String FILENAME = "src/main/resources/discounts.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static List<Discount> discounts = new ArrayList<>();
    private static int nextId = 1;

    public static void main(String[] args) {
        loadData();
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            System.out.println("Enter command (1.add/2.get/3.update/4.delete/5.quit):");
            command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "1":
                    addDiscount(scanner);
                    break;
                case "2":
                    getDiscount(scanner);
                    break;
                case "3":
                    updateDiscount(scanner);
                    break;
                case "4":
                    deleteDiscount(scanner);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid command!");
            }
        } while (!command.equals("quit"));
        saveData();
    }

    private static void addDiscount(Scanner scanner) {
        LocalDate startDate = readDate(scanner, "Enter start date (yyyy-MM-dd):");
        LocalDate endDate = readDate(scanner, "Enter end date (yyyy-MM-dd):");
        int boatTypeId = readInt(scanner, "Enter boat type ID (1-4):", 1, 4);
        float percentage = readFloat(scanner, "Enter discount percentage (0-100):", 0, 100);
        Discount discount = new Discount(nextId++, startDate, endDate, boatTypeId, percentage);
        discounts.add(discount);
        System.out.println("Discount added with ID " + discount.getId() + ".");
    }

    private static void getDiscount(Scanner scanner) {
        int id = readInt(scanner, "Enter discount ID:");
        Discount discount = findDiscount(id);
        if (discount == null) {
            System.out.println("Discount not found!");
        } else {
            System.out.println(gson.toJson(discount));
        }
    }

    private static int readInt(Scanner scanner, String s) {
        return 0;
    }

    private static void updateDiscount(Scanner scanner) {
        int id = readInt(scanner, "Enter discount ID:");
        Discount discount = findDiscount(id);
        if (discount == null) {
            System.out.println("Discount not found!");
        } else {
            LocalDate startDate = readDate(scanner, "Enter start date (yyyy-MM-dd):");
            LocalDate endDate = readDate(scanner, "Enter end date (yyyy-MM-dd):");
            int boatTypeId = readInt(scanner, "Enter boat type ID (1-4):", 1, 4);
            float percentage = readFloat(scanner, "Enter discount percentage (0-100):", 0, 100);
            discount.setStartDate(startDate);
            discount.setEndDate(endDate);
            discount.setBoatTypeId(boatTypeId);
            discount.setPercentage(percentage);
            System.out.println("Discount updated.");
        }
    }

    private static void deleteDiscount(Scanner scanner) {
        int id = readInt(scanner, "Enter discount ID:");
        Discount discount = findDiscount(id);
        if (discount == null) {
            System.out.println("Discount not found!");
        } else {
            discounts.remove(discount);
            System.out.println("Discount deleted.");
        }
    }

    private static LocalDate readDate(Scanner scanner, String message) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine().trim();
            try {
                LocalDate date = LocalDate.parse(input, formatter);
                return date;
            } catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }
    }

    private static int readInt(Scanner scanner, String message, int min, int max) {
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Value must be between " + min + " and " + max + "!");
                }
            } catch (Exception e) {
                System.out.println("Invalid integer format!");
            }
        }
    }

    private static float readFloat(Scanner scanner, String message, float min, float max) {
        while (true) {
            System.out.println(message);
            String input = scanner.nextLine().trim();
            try {
                float value = Float.parseFloat(input);
                if (value >= min && value <= max) {
                    return value;
                } else {
                    System.out.println("Value must be between " + min + " and " + max + "!");
                }
            } catch (Exception e) {
                System.out.println("Invalid float format!");
            }
        }
    }

    private static Discount findDiscount(int id) {
        for (Discount discount : discounts) {
            if (discount.getId() == id) {
                return discount;
            }
        }
        return null;
    }

    private static void loadData() {
        try {
            FileReader reader = new FileReader(FILENAME);
            Discount[] array = gson.fromJson(reader, Discount[].class);
            for (Discount discount : array) {
                discounts.add(discount);
                if (discount.getId() >= nextId) {
                    nextId = discount.getId() + 1;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading data from file!");
        }
    }

    private static void saveData() {
        try {
            FileWriter writer = new FileWriter(FILENAME);
            gson.toJson(discounts.toArray(new Discount[0]), writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("Error saving data to file!");
        }

    }

    private static class Discount {
        private int id;
        private LocalDate startDate;
        private LocalDate endDate;
        private int boatTypeId;
        private float percentage;

        public Discount(int id, LocalDate startDate, LocalDate endDate, int boatTypeId, float percentage) {
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.boatTypeId = boatTypeId;
            this.percentage = percentage;
        }

        public int getId() {
            return id;
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
    }

}