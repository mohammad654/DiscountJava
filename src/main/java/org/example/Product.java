package org.example;

import java.util.UUID;

public class Product {
    private UUID id;
    String name;
    String type;
    double price;

    public Product(String name, String type, double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }
}
