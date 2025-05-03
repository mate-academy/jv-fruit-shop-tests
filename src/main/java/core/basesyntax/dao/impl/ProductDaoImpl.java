package core.basesyntax.dao.impl;

import core.basesyntax.dao.ProductDao;
import core.basesyntax.storage.ProductStorage;
import java.util.Map;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void updateAmount(String fruitName, Integer quantity) {
        checkIfProductDataValid(fruitName, quantity);
        ProductStorage.products.put(fruitName, quantity);
    }

    @Override
    public void addAmount(String fruitName, Integer quantity) {
        checkIfProductDataValid(fruitName, quantity);
        if (ProductStorage.products.containsKey(fruitName)) {
            ProductStorage.products.put(
                    fruitName, ProductStorage.products.get(fruitName) + quantity);
            return;
        }
        ProductStorage.products.put(fruitName, quantity);
    }

    @Override
    public void subtractAmount(String fruitName, Integer quantity) {
        checkIfProductDataValid(fruitName, quantity);
        if (ProductStorage.products.containsKey(fruitName)) {
            if (ProductStorage.products.get(fruitName) < quantity) {
                throw new RuntimeException("Invalid quantity " + quantity + " to subtract");
            }
            ProductStorage.products.put(
                    fruitName, ProductStorage.products.get(fruitName) - quantity);
            return;
        }
        throw new RuntimeException("Impossible to get product from empty productStorage!");
    }

    @Override
    public Map<String, Integer> getAllProducts() {
        return ProductStorage.products;
    }

    private void checkIfProductDataValid(String fruitName, Integer quantity) {
        if (fruitName == null || fruitName.isEmpty()) {
            throw new RuntimeException("Invalid product name " + fruitName
                    + " to work with product storage");
        }
        if (quantity == null || quantity < 0) {
            throw new RuntimeException("Negative or null product quantity " + fruitName
                    + ", can't work with product storage");
        }
    }
}
