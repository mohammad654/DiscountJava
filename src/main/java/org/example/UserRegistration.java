//package org.example;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Scanner;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//public class UserRegistration {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        boolean isLoggedIn = false;
//        String loggedInUser = null;
//
//        while (true) {
//            System.out.println("1. Register");
//            System.out.println("2. Login");
//            System.out.println("3. View all users");
//            System.out.println("4. Update details");
//            System.out.println("5. Delete account");
//            System.out.println("6. Exit");
//
//            System.out.print("Enter your choice: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    registerUser(scanner);
//                    break;
//
//                case 2:
//                    loggedInUser = loginUser(scanner);
//                    if (loggedInUser != null) {
//                        isLoggedIn = true;
//                    }
//                    break;
//
//                case 3:
//                    viewAllUsers(isLoggedIn, loggedInUser);
//                    break;
//
//                case 4:
//                    updateUserDetails(scanner, loggedInUser);
//                    break;
//
//                case 5:
//                    deleteAccount(scanner, loggedInUser);
//                    isLoggedIn = false;
//                    loggedInUser = null;
//                    break;
//
//                case 6:
//                    System.out.println("Exiting program...");
//                    System.exit(0);
//
//                default:
//                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
//            }
//        }
//    }
//
//    private static void registerUser(Scanner scanner) {
//        System.out.print("Enter first name: ");
//        String firstName = scanner.nextLine();
//
//        System.out.print("Enter last name: ");
//        String lastName = scanner.nextLine();
//
//        System.out.print("Are you a hotel guest? (Yes/No): ");
//        String hotelGuest = scanner.nextLine();
//
//        String roomNumber = null;
//        if (hotelGuest.equalsIgnoreCase("Yes")) {
//            System.out.print("Enter room number: ");
//            roomNumber = scanner.nextLine();
//        }
//
//        JSONObject userObject = new JSONObject();
//        userObject.put("firstName", firstName);
//        userObject.put("lastName", lastName);
//        userObject.put("hotelGuest", hotelGuest);
//        userObject.put("roomNumber", roomNumber);
//
//        JSONArray usersArray = readUsersFromFile();
//        usersArray.add(userObject);
//
//        try (FileWriter file = new FileWriter("src/main/resources/users.json")) {
//            file.write(usersArray.toJSONString());
//            System.out.println("Registration successful!");
//        } catch (IOException e) {
//            System.out.println("Registration failed. An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    private static JSONArray readUsersFromFile() {
//    }
//
//    private static String loginUser(Scanner scanner) {
//        System.out.print("Enter first name: ");
//        String firstName = scanner.nextLine();
//
//        System.out.print("Enter last name: ");
//        String lastName = scanner.nextLine();
//
//        JSONArray usersArray = readUsersFromFile();
//
//        for (Object object : usersArray) {
//            JSONObject userObject = (JSONObject) object;
//            if (userObject.get("firstName").equals(firstName) && userObject.get("lastName").equals(lastName)) {
//                System.out.println("Login successful!");
//                return firstName + " " + lastName;
//            }
//        }
//
//        System.out.println("Login failed. User not found.");
//        return null;
//    }
//
//    private static void viewAllUsers(boolean isLoggedIn, String loggedInUser) {
//        if (isLoggedIn) {
//            JSONArray usersArray = readUsersFromFile();
//            for (Object object : usersArray) {
//                JSONObject userObject = (JSONObject) object;
//                System.out.println("Name: " + userObject.get("firstName") + " " + userObject.get("lastName"));
//                System.out.println("Hotel guest: " + userObject.get("hotelGuest"));
//                if (userObject.get("hotelGuest").equals("Yes")) {
//                    System.out.println("Room number: " + userObject.get("roomNumber"));
//                }
//                System.out.println();
//            }
//        } else {
//            System.out.println("You need to login to view user details.");
//        }
//    }
//
//    private static void updateUserDetails(Scanner scanner, String loggedInUser) {
//        if (loggedInUser == null) {
//            System.out.println("You need to login to update your details.");
//            return;
//        }
//
//        JSONArray usersArray = readUsersFromFile();
//        JSONObject currentUserObject = null;
//
//        for (Object object : usersArray) {
//            JSONObject userObject = (JSONObject) object;
//            if (userObject.get("firstName").equals(loggedInUser.split(" ")[0]) && userObject.get("lastName").equals(loggedInUser.split(" ")[1])) {
//                currentUserObject = userObject;
//                break;
//            }
//        }
//
//        if (currentUserObject == null) {
//            System.out.println("Error: User not found.");
//            return;
//        }
//
//        System.out.print("Enter new first name (leave blank if no change): ");
//        String firstName = scanner.nextLine();
//
//        System.out.print("Enter new last name (leave blank if no change): ");
//        String lastName = scanner.nextLine();
//
//        System.out.print("Are you a hotel guest? (Yes/No, leave blank if no change): ");
//        String hotelGuest = scanner.nextLine();
//
//        String roomNumber = null;
//        if (hotelGuest.equalsIgnoreCase("Yes")) {
//            System.out.print("Enter room number: ");
//            roomNumber = scanner.nextLine();
//        } else if (hotelGuest.equalsIgnoreCase("No")) {
//            roomNumber = null;
//        } else {
//            roomNumber = (String) currentUserObject.get("roomNumber");
//        }
//
//        if (!firstName.equals("")) {
//            currentUserObject.put("firstName", firstName);
//        }
//
//        if (!lastName.equals("")) {
//            currentUserObject.put("lastName", lastName);
//        }
//
//        if (!hotelGuest.equals("")) {
//            currentUserObject.put("hotelGuest", hotelGuest);
//            currentUserObject.put("roomNumber", roomNumber);
//        }
//
//        try (FileWriter file = new FileWriter("users.json")) {
//            file.write(usersArray.toJSONString());
//            System.out.println("Details updated successfully!");
//        } catch (IOException e) {
//            System.out.println("An error occurred while updating details.");
//            e.printStackTrace();
//        }
//    }
//
//    private static void deleteAccount(Scanner scanner, String loggedInUser) {
//        if (loggedInUser == null) {
//            System.out.println("You need to login to delete your account.");
//            return;
//        }
//
//        JSONArray usersArray = readUsersFromFile();
//
//}}
