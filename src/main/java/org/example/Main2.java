package org.example;


import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Write write = new Write();
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            if (option == 1) {
                System.out.print("Enter first name: ");
                String firstName = scanner.next();
                System.out.print("Enter last name: ");
                String lastName = scanner.next();
                System.out.print("Enter email: ");
                String email = scanner.next();
                System.out.print("Enter phone number: ");
                String phone = scanner.next();
                System.out.print("Does the user have a street address (y/n)? ");
                String hasStreetInput = scanner.next();
                boolean hasStreet = hasStreetInput.equalsIgnoreCase("y");
                String houseNumber = null;
                if (hasStreet) {
                    System.out.print("Enter house number: ");
                    houseNumber = scanner.next();
                }
                boolean success = UserManager.register(firstName, lastName, email, phone, hasStreet, houseNumber);
                if (success) {
                    System.out.println("Registration successful.");
                } else {
                    System.out.println("Registration failed. Email already registered.");
                }
            } else if (option == 2) {
                System.out.print("Enter email: ");
                String email = scanner.next();
                User user = UserManager.login(email);
                if (user != null) {
                    System.out.println("Login successful. User ID is " + user.getId() + ".");
//                    write.main();

                } else {
                    System.out.println("Login failed. User not found.");
                }
            } else if (option == 3) {
                break;
            }
        }
    }
}
