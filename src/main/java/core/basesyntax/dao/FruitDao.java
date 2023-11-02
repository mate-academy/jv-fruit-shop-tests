package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import java.util.Map;

public interface FruitDao {
    void addFirst(String fruitName, int amount);

    void add(String fruitName, int amount);

    Integer get(String fruitName);

    Map<Fruit, Integer> getAll();

    void remove(String fruitName, int amount);

    void removeAll();

    Integer size();
}
