package org.example;

import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

public class BoatCRUD {
    private static final String FILENAME = "src/main/resources/discounts.json";
    private static int nextId = 1;

    @SuppressWarnings("unchecked")
    public static void create() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter boat type (1=Kajak loop, 2=Rowing Boat loop, 3=Supboard loop, 4=Electrical boat loop): ");
        int type = scanner.nextInt();

        String boatType;
        double price;
        int maxQuantity;
        switch (type) {
            case 1:
                boatType = "Kajak";
                price = 23;
                maxQuantity = 10;
                break;
            case 2:
                boatType = "Rowing Boat ";
                price = 27.5;
                maxQuantity = 10;
                break;
            case 3:
                boatType = "Supboard ";
                price = 17.5;
                maxQuantity = 20;
                break;
            case 4:
                boatType = "Electrical boat ";
                price = 35;
                maxQuantity = 5;
                break;
            default:
                System.out.println("Invalid boat type.");
                return;
        }

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        if (quantity > maxQuantity) {
            System.out.println("Maximum quantity for this boat type is " + maxQuantity + ".");
            return;
        }

        Boat boat = new Boat(nextId++, boatType, quantity, price);

        JSONArray boats = readBoats();
        boats.add(boatToJson(boat));
        writeBoats(boats);

        System.out.println("Boat created.");
    }

    @SuppressWarnings("unchecked")
    public static void read() throws IOException, ParseException {
        JSONArray boats = readBoats();

        for (Object o : boats) {
            JSONObject boatJson = (JSONObject) o;
            Boat boat = jsonToBoat(boatJson);
            System.out.println(boat.getId() + ": " + boat.getType() + ", Quantity: " + boat.getQuantity() + ", Price: " + boat.getPrice());
        }
    }

    public static void update() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter boat ID: ");
        int id = scanner.nextInt();

        JSONArray boats = readBoats();

        int index = -1;
        for (int i = 0; i < boats.size(); i++) {
            JSONObject boatJson = (JSONObject) boats.get(i);
            Boat boat = jsonToBoat(boatJson);
            if (boat.getId() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Boat not found.");
            return;
        }

        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        if (quantity < 0) {
            System.out.println("Quantity must be non-negative.");
            return;
        }

        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();
        if (price < 0) {
            System.out.println("Price must be non-negative.");
            return;
        }

        JSONObject boatJson = (JSONObject) boats.get(index);
        Boat boat = jsonToBoat(boatJson);
        boat.setQuantity(quantity);
        boat.setPrice(price);
        boats.set(index, boatToJson(boat));

        writeBoats(boats);

        System.out.println("Boat updated.");
    }

    public static void delete() throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter boat ID: ");
        int id = scanner.nextInt();

        JSONArray boats = readBoats();

        int index = -1;
        for (int i = 0; i < boats.size(); i++) {
            JSONObject boatJson = (JSONObject) boats.get(i);
            Boat boat = jsonToBoat(boatJson);
            if (boat.getId() == id) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("Boat not found.");
            return;
        }

        boats.remove(index);

        writeBoats(boats);

        System.out.println("Boat deleted.");
    }

    private static JSONArray readBoats() throws IOException, ParseException {
        FileReader reader = new FileReader(FILENAME);
        JSONParser parser = new JSONParser();
        JSONArray boats = (JSONArray) parser.parse(reader);
        reader.close();
        return boats;
    }

    private static void writeBoats(JSONArray boats) throws IOException {
        FileWriter writer = new FileWriter(FILENAME);
        writer.write(boats.toJSONString());
        writer.close();
    }

    private static JSONObject boatToJson(Boat boat) {
        JSONObject boatJson = new JSONObject();
        boatJson.put("id", boat.getId());
        boatJson.put("type", boat.getType());
        boatJson.put("quantity", boat.getQuantity());
        boatJson.put("price", boat.getPrice());
        return boatJson;
    }

    private static Boat jsonToBoat(JSONObject boatJson) {
        int id = ((Long) boatJson.get("id")).intValue();
        String type = (String) boatJson.get("type");
        int quantity = ((Long) boatJson.get("quantity")).intValue();
        double price = (Double) boatJson.get("price");
        return new Boat(id, type, quantity, price);
    }
}