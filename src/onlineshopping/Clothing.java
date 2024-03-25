package onlineshopping;

class Clothing extends Product {
    private String size;
    private String color;
    private String category;

    public Clothing(String productID, String productName, int inStock, double price, String size, String color) {
        super(productID, productName, inStock, price, "Clothing");
        this.size = size;
        this.color = color;
        this.category = "Clothing";
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }
    

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    

    @Override
    public String getProductDetails() {
        return "Clothing{" +
               "productID='" + getProductID() + '\'' +
               ", productName='" + getProductName() + '\'' +
               ", inStock=" + getInStock() +
               ", price=" + getPrice() +
               ", category='" + getCategory() + '\'' + // Use the correct getter method name
               ", size='" + size + '\'' +
               ", color='" + color + '\'' +
               '}';
    }
}
