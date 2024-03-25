
package onlineshopping;

abstract class Product {
    private String productID;
    private String productName;
    private int inStock;
    private double price;
    private String category;


    public Product(String productID, String productName, int inStock, double price, String category) {
        this.productID = productID;
        this.productName = productName;
        this.inStock = inStock;
        this.price = price;
        this.category = category;
    }


    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getProductDetails();
}
