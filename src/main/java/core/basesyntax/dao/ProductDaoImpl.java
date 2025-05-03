package core.basesyntax.dao;

import core.basesyntax.db.ProductStorage;
import java.util.Map;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    @Override
    public void setQuantity(String productName, Integer productQuantity) {
        ProductStorage.products.put(productName, productQuantity);
    }

    @Override
    public Optional<Integer> getQuantity(String productName) {
        return Optional.ofNullable(ProductStorage.products.get(productName));
    }

    @Override
    public Map<String, Integer> getAll() {
        return ProductStorage.products;
    }
}
