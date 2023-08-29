package org.example.View;

import org.example.DiscountManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DiscountCreate {
    private static void addDiscount(Scanner scanner, ArrayList<DiscountManager.Discountnew> discounts) {
        System.out.println("Enter start date (YYYY-MM-DD):");
        LocalDate startDate = LocalDate.parse(scanner.next());

        System.out.println("Enter end date (YYYY-MM-DD):");
        LocalDate endDate = LocalDate.parse(scanner.next());

        System.out.println("Enter boat type ID (1, 2, 3, or 4):");
        int boatTypeId = scanner.nextInt();

        System.out.println("Enter discount percentage:");
        float percentage = scanner.nextFloat();

        DiscountManager.Discountnew discount = new DiscountManager.Discountnew(startDate, endDate, boatTypeId, percentage);
        discounts.add(discount);

        System.out.println("Discount added successfully. ID: " + discount.getId());
    }

}
