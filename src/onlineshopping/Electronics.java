package onlineshopping;

class Electronics extends Product {
    
    private String brand;
    private int warrantyPeriod;
    private String category;

    public Electronics(String productID, String productName, int inStock, double price, String brand, int warrantyPeriod) {
        super(productID, productName, inStock, price, "Electronics");
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
        this.category = "Electronics";

    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }
    

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }
    

    @Override
    public String getProductDetails() {
        return "Electronics{" +
               "productID='" + getProductID() + '\'' +
               ", productName='" + getProductName() + '\'' +
               ", inStock=" + getInStock() +
               ", price=" + getPrice() +
               ", category='" + getCategory() + '\'' + // Use the correct getter method name
               ", brand='" + brand + '\'' +
               ", warrantyPeriod=" + warrantyPeriod + " years" +
               '}';
    }
    
}

