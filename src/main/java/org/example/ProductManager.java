package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProductManager {
    private final String PRODUCTS_FILE = "src/main/resources/products.json";
    private List<Product> products;
    private ObjectMapper objectMapper;

    public ProductManager() {
        objectMapper = new ObjectMapper();
        products = new ArrayList<>();

        try {
            File file = new File(PRODUCTS_FILE);
            if (file.exists()) {
                products = objectMapper.readValue(file, new TypeReference<List<Product>>() {
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product type (Kajak, Rowing Boat, Supboard, or Electrical boat): ");
        String type = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();

        Product product = new Product(name, type, price);
        products.add(product);

        saveProducts();
    }

    public void readProducts() {
        for (Product product : products) {
            System.out.println(product.getId() + " - " + product.getName() + " (" + product.getType() + ") - $" + product.getPrice());
        }
    }

    public void updateProduct(UUID id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new product name (or leave blank to keep the same): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) {
                    product.name = name;
                }
                System.out.print("Enter new product type (Kajak, Rowing Boat, Supboard, or Electrical boat) (or leave blank to keep the same): ");
                String type = scanner.nextLine();
                if (!type.isEmpty()) {
                    product.type = type;
                }
                System.out.print("Enter new product price (or leave blank to keep the same): ");
                String priceStr = scanner.nextLine();
                if (!priceStr.isEmpty()) {
                    double price = Double.parseDouble(priceStr);
                    product.price = price;
                }

                saveProducts();
                return;
            }
        }
        System.out.println("Product not found with id " + id);
    }


    public void deleteProduct(UUID id) {
        products.removeIf(product -> product.getId().equals(id));
        saveProducts();
    }

    private void saveProducts() {
        try {
            objectMapper.writeValue(new File(PRODUCTS_FILE), products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager productManager = new ProductManager();

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. List products");
            System.out.println("2. Add a product");
            System.out.println("3. Update a product");
            System.out.println("4. Delete a product");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productManager.readProducts();
                    break;
                case 2:
                    productManager.createProduct();
                    break;
                case 3:
                    System.out.print("Enter product id to update: ");
                    String idStr = scanner.nextLine();
                    UUID id = UUID.fromString(idStr);
                    productManager.updateProduct(id);
                    break;
                case 4:
                    System.out.print("Enter product id to delete: ");
                    idStr = scanner.nextLine();
                    id = UUID.fromString(idStr);
                    productManager.deleteProduct(id);
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}