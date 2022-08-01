package core.basesyntax.dao;

import java.util.Map;
import java.util.Optional;

public interface FruitDao {
    void update(String fruitName, int quantity);

    Optional<Integer> get(String fruitName);

    Map<String,Integer> getAll();
}
