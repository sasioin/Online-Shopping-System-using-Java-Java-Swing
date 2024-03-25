package onlineshopping;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<Product, Integer> productQuantities;

    public ShoppingCart() {
        this.productQuantities = new HashMap<>();
    }

    public void addProductToCart(Product product) {
        productQuantities.merge(product, 1, Integer::sum);
    }

    public Map<Product, Integer> getProductsInCart() {
        return new HashMap<>(productQuantities);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Map.Entry<Product, Integer> entry : productQuantities.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
}


