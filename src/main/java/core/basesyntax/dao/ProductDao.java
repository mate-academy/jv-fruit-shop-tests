package core.basesyntax.dao;

import core.basesyntax.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> get();
}

