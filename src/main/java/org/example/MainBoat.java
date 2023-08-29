package org.example;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
public class MainBoat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Boat CRUD Operations");
            System.out.println("--------------------");
            System.out.println("1. Create boat");
            System.out.println("2. Read boats");
            System.out.println("3. Update boat");
            System.out.println("4. Delete boat");
            System.out.println("5. Exit");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            try {
                switch (option) {
                    case 1:
                        BoatCRUD.create();
                        break;
                    case 2:
                        BoatCRUD.read();
                        break;
                    case 3:
                        BoatCRUD.update();
                        break;
                    case 4:
                        BoatCRUD.delete();
                        break;
                    case 5:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (org.json.simple.parser.ParseException e) {
                throw new RuntimeException(e);
            }

            System.out.println();
        }
    }

}