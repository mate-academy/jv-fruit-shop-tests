package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Product;
import java.util.List;
import java.util.Objects;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product get(String productName) {
        return Storage.products.stream()
                .filter(p -> Objects.equals(p.getName(), productName))
                .findFirst().orElseThrow(RuntimeException::new);
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public void update(Product product) {
        Product productToRemove = get(product.getName());
        Storage.products.remove(productToRemove);
        Storage.products.add(product);
    }

    @Override
    public void add(Product product) {
        if (product == null) {
            throw new NullPointerException("Product can't be null");
        }
        Storage.products.add(product);
    }
}
