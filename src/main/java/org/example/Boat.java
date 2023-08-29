package org.example;

public class Boat {
    private int id;
    private String type;
    private int quantity;
    private double price;

    public Boat(int id, String type, int quantity, double price) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
