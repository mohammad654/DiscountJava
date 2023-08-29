package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DiscountManager {
    public static class Discountnew {
        public static int lastId = 0;
        public int id;
        public LocalDate startDate;
        public LocalDate endDate;
        public int boatTypeId;
        public float percentage;

        public Discountnew(LocalDate startDate, LocalDate endDate, int boatTypeId, float percentage) {
            this.id = ++lastId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.boatTypeId = boatTypeId;
            this.percentage = percentage;
        }

        // getters and setters

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

        // main method
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            ArrayList<Discountnew> discounts = loadDiscountsFromFile();

            while (true) {
                System.out.println("Choose an option:");
                System.out.println("1. Add a new discount");
                System.out.println("2. View all discounts");
                System.out.println("3. Update a discount");
                System.out.println("4. Delete a discount");
                System.out.println("5. Exit");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addDiscount(scanner, discounts);
                        break;
                    case 2:
                        viewDiscounts(discounts);
                        break;
                    case 3:
                        updateDiscount(scanner, discounts);
                        break;
                    case 4:
                        deleteDiscount(scanner, discounts);
                        break;
                    case 5:
                        saveDiscountsToFile(discounts);
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice, please try again");
                        break;
                }
            }
        }

        // methods
        private static ArrayList<Discountnew> loadDiscountsFromFile() {
            ArrayList<Discountnew> discounts = new ArrayList<>();
            File file = new File("discounts.json");

            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    Gson gson = new Gson();
                    discounts = gson.fromJson(reader, new TypeToken<ArrayList<Discountnew>>() {
                    }.getType());
                    lastId = discounts.get(discounts.size() - 1).getId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return discounts;
        }

        private static void saveDiscountsToFile(ArrayList<Discountnew> discounts) {
            Gson gson = new Gson();
            String json = gson.toJson(discounts);
//src/main/resources/
            try (FileWriter writer = new FileWriter("discounts.json")) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void addDiscount(Scanner scanner, ArrayList<Discountnew> discounts) {
            System.out.println("Enter start date (YYYY-MM-DD):");
            LocalDate startDate = LocalDate.parse(scanner.next());

            System.out.println("Enter end date (YYYY-MM-DD):");
            LocalDate endDate = LocalDate.parse(scanner.next());

            System.out.println("Enter boat type ID (1, 2, 3, or 4):");
            int boatTypeId = scanner.nextInt();

            System.out.println("Enter discount percentage:");
            float percentage = scanner.nextFloat();

            Discountnew discount = new Discountnew(startDate, endDate, boatTypeId, percentage);
            discounts.add(discount);

            System.out.println("Discount added successfully. ID: " + discount.getId());
        }

        private static void viewDiscounts(ArrayList<Discountnew> discounts) {
            for (Discountnew discount : discounts) {
                System.out.println(discount.getId() + " - From " + discount.getStartDate() + " to " + discount.getEndDate() + " - Boat Type ID: " + discount.getBoatTypeId() + " - Discount Percentage: " + discount.getPercentage());
            }
        }

        private static void updateDiscount(Scanner scanner, ArrayList<Discountnew> discounts) {
            System.out.println("Enter discount ID:");
            int id = scanner.nextInt();

            for (Discountnew discount : discounts) {
                if (discount.getId() == id) {
                    System.out.println("Enter start date (YYYY-MM-DD):");
                    LocalDate startDate = LocalDate.parse(scanner.next());
                    discount.setStartDate(startDate);

                    System.out.println("Enter end date (YYYY-MM-DD):");
                    LocalDate endDate = LocalDate.parse(scanner.next());
                    discount.setEndDate(endDate);

                    System.out.println("Enter boat type ID (1, 2, 3, or 4):");
                    int boatTypeId = scanner.nextInt();
                    discount.setBoatTypeId(boatTypeId);

                    System.out.println("Enter discount percentage:");
                    float percentage = scanner.nextFloat();
                    discount.setPercentage(percentage);

                    System.out.println("Discount updated successfully.");
                    return;
                }
            }

            System.out.println("Discount not found.");
        }

        private static void deleteDiscount(Scanner scanner, ArrayList<Discountnew> discounts) {
            System.out.println("Enter discount ID:");
            int id = scanner.nextInt();

            for (Discountnew discount : discounts) {
                if (discount.getId() == id) {
                    discounts.remove(discount);
                    System.out.println("Discount deleted successfully.");
                    return;
                }
            }

            System.out.println("Discount not found.");
        }
    }
}
