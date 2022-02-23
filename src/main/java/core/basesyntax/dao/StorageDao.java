package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.List;

public interface StorageDao {
    void add(String fruitName, int amount);

    void substract(String fruitName, int amount);

    Fruit get(String fruitName);

    List<Fruit> getStorage();
}
