package org.example.View;

import java.util.Scanner;

public class DiscountMenu {
    public final static char ADD = '1';
    public final static char VIEW = '2';
    public final static char UPDATE = '3';
    public final static char DELETE = '4';
    public final static char EXIT = '5';

    public static char view() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println(ADD + ". Add a new discount");
            System.out.println(VIEW + ". View all discounts");
            System.out.println(UPDATE + ". Update a discount");
            System.out.println(DELETE + ". Delete a discount");
            System.out.println(EXIT + ". Exit");

            String line = scanner.nextLine().trim();
            boolean correctChoice = true;
            if (line.length() != 1) correctChoice = false;
            else switch (line.charAt(0)) {
                case ADD:
                case VIEW:
                case UPDATE:
                case DELETE:
                case EXIT:
                    correctChoice = true;
                    break;
                default:
                    correctChoice = false;
            }
            if (correctChoice) return line.charAt(0);
            else System.out.println("Invalid choice, please try again");
        }
    }
}
