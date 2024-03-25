package onlineshopping;

import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Menu {

    private static WestminsterShoppingManager manager;
    private static ShoppingGUI gui;

    public static void main(String[] args) {
    manager = new WestminsterShoppingManager();
    Scanner input = new Scanner(System.in);

    while (true) {
        System.out.println("Select one to continue...");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save information to a file");
        System.out.println("5. Open GUI");
        System.out.println("6. Exit");
        System.out.print("\nEnter the Selection:  ");

        String userInput = input.nextLine().trim();
        int action = 0;


        try {
            action = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid entry. Please enter a number.");
            continue; // Skip the rest of the loop and prompt again
        }

        switch (action) {
            case 1:
                System.out.println("Add a new product");
                manager.addNewProduct();
                break;
            case 2:
                System.out.println("Delete a product");
                manager.deleteProduct();
                break;
            case 3:
                System.out.println("Print the list of products");
                manager.printProductList();
                break;
            case 4:
                System.out.println("Save information to a file");
                manager.saveToFile();
                break;
            case 5:
                System.out.println("Open GUI");
                openGUI();
                break;
            case 6:
                System.out.println("Exiting the program...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid selection. Please try again...");
                break;
        }
    }
}
    private static void openGUI() {
    if (gui == null) {
        SwingUtilities.invokeLater(() -> {
            gui = new ShoppingGUI(manager);
            gui.setVisible(true);
        });
    } else {
        SwingUtilities.invokeLater(() -> gui.setVisible(true));
    }
}
}
