package onlineshopping;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class WestminsterShoppingManager implements ShoppingManager {
    
    static final int maxProducts = 50;
    private List<Product> products = new ArrayList<>();
    public Scanner scanner;
    
    public WestminsterShoppingManager() {
        scanner = new Scanner(System.in);
        loadFromFile();  
    }

    public List<Product> getAllProducts() {
         return new ArrayList<>(products); 
    }

    @Override
    public void addNewProduct() {
        if (products.size() >= maxProducts) {
            System.out.println("Maximum product limit reached");
            return;
        }

        int choice = getCategoryChoice();

        System.out.print("Enter product ID: ");
        String productID = scanner.nextLine().trim();
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine().trim();
        int inStock = getIntInput("Enter available stock: ");
        double price = getDoubleInput("Enter product price: ");

        Product product = null;

        if (choice == 1) {
            System.out.print("Enter Brand: ");
            String brand = scanner.nextLine().trim();
            int warrantyPeriod = getIntInput("Enter Warranty Period: ");
            product = new Electronics(productID, productName, inStock, price, brand, warrantyPeriod);
        } else if (choice == 2) {
            System.out.print("Enter Size: ");
            String size = scanner.nextLine().trim();
            System.out.print("Enter Color: ");
            String color = scanner.nextLine().trim();
            product = new Clothing(productID, productName, inStock, price, size, color);
        }

        if (product != null) {
            products.add(product);
            System.out.println("Product Successfully added.");
        } else {
            System.out.println("Invalid choice or missing information.");
        }
    }

    private int getCategoryChoice() {
        while (true) {
            System.out.println("\nTo choose the Electronics Category Press 1\nTo choose Clothing Category Press 2");
            String input = scanner.nextLine().trim();
            if (input.matches("1|2")) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Invalid input. Please enter 1 or 2.");
            }
        }
    }

    private int getIntInput(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }


    @Override
    public void deleteProduct() {
        System.out.print("Enter the Product ID to delete: ");
        String productId = scanner.nextLine();

        Product foundProduct = null;
        for (Product product : products) {
            if (product.getProductID().equals(productId)) {
                foundProduct = product;
                break;
            }
        }
        if (foundProduct != null) {
            products.remove(foundProduct);
            System.out.println("--------------------------------");
            System.out.println("Deleted Product Details:");
            System.out.println("Product ID: " + foundProduct.getProductID());
            System.out.println("Product Name: " + foundProduct.getProductName());
            System.out.println("Product Type: " + (foundProduct instanceof Electronics ? "Electronics" : "Clothing"));
            System.out.println("--------------------------------");
            System.out.println("Total Products Left In System: " + products.size());
            System.out.println("--------------------------------");

            // Call saveToFile() method to update the file after deletion
            saveToFile();
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
    }

    @Override
    public void printProductList() {
    if (products.size() > 0) {
        Collections.sort(products, Comparator.comparing(Product::getProductID));

        for (Product product : products) {
            System.out.println("Product ID : " + product.getProductID());
            System.out.println("Name : " + product.getProductName());
            System.out.println("Category : " + product.getCategory());
            System.out.println("Price : " + product.getPrice()+ '£');
            System.out.println("Items Available : " + product.getInStock());

            if (product instanceof Electronics) {
                Electronics elec = (Electronics) product;
                System.out.println("Brand : " + elec.getBrand());
                System.out.println("Warranty Period : " + elec.getWarrantyPeriod() + " Weeks");
            } else if (product instanceof Clothing) {
                Clothing cloth = (Clothing) product;
                System.out.println("Size : " + cloth.getSize());
                System.out.println("Color : " + cloth.getColor());
            }

            System.out.println("--------------------------------");
        }
    } else {
        System.out.println("Sorry, no products to print!!!");
    }
}

    @Override
    public void saveToFile() {
        File productFile = new File("Products.txt");

        // Change 'true' to 'false' in FileWriter constructor to overwrite the file instead of appending
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(productFile, false))) {
            for (Product product : products) {
                StringBuilder sb = new StringBuilder();
                sb.append(product.getProductID()).append(",")
                        .append(product.getProductName()).append(",")
                        .append(product.getInStock()).append(",")
                        .append(product.getPrice());

                if (product instanceof Electronics) {
                    Electronics elec = (Electronics) product;
                    sb.append(",Electronics,").append(elec.getBrand()).append(",").append(elec.getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    Clothing cloth = (Clothing) product;
                    sb.append(",Clothing,").append(cloth.getSize()).append(",").append(cloth.getColor());
                }

                bufferedWriter.write(sb.toString());
                bufferedWriter.newLine();
            }
            System.out.println("Products successfully saved!");
        } catch (IOException e) {
            System.out.println("Error occurred while saving products: " + e.getMessage());
        }
    }


    private void loadFromFile() {
    File productFile = new File("Products.txt");
    if (!productFile.exists()) {
        return;
    }

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(productFile))) {
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] productData = line.split(",");

            String productID = productData[0];
            String productName = productData[1];
            int inStock = Integer.parseInt(productData[2]);
            double price = Double.parseDouble(productData[3]);
            String category = productData[4];

            Product product = null;
            if ("Electronics".equals(category)) {
                String brand = productData[5];
                int warrantyPeriod = Integer.parseInt(productData[6]);
                product = new Electronics(productID, productName, inStock, price, brand, warrantyPeriod);
                product.setCategory("Electronics"); // Explicitly setting the category
            } else if ("Clothing".equals(category)) {
                String size = productData[5];
                String color = productData[6];
                product = new Clothing(productID, productName, inStock, price, size, color);
                product.setCategory("Clothing"); // Explicitly setting the category
            }

            if (product != null) {
                products.add(product);
            }
        }
    } catch (IOException e) {
        System.out.println("Error occurred while loading products: " + e.getMessage());
    }
   }
    public Product getProductById(String productID) {
        for (Product product : products) {
            if (product.getProductID().equals(productID)) {
                return product;
            }
        }
        return null; // Return null or throw an exception if the product is not found
    }

}


       