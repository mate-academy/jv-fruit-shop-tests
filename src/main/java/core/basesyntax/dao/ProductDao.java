package core.basesyntax.dao;

import core.basesyntax.model.Product;
import java.util.List;

public interface ProductDao {
    Product get(String productName);

    List<Product> getAll();

    void update(Product product);

    void add(Product product);
}
