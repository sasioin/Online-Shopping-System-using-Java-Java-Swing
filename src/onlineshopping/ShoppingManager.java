package onlineshopping;

public interface ShoppingManager {
    void addNewProduct();
    void deleteProduct();
    void printProductList();
    void saveToFile();

    // Optional: void loadFromFile();
    Product getProductById(String productID);
}

