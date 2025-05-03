package core.basesyntax.dao;

import java.util.Map;

public interface ProductDao {
    void updateAmount(String fruitName, Integer quantity);

    void addAmount(String fruitName, Integer quantity);

    void subtractAmount(String fruitName, Integer quantity);

    Map<String, Integer> getAllProducts();
}
