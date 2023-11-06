package core.basesyntax.dao;

import java.util.Map;

public interface FruitDao {
    void addFirst(String fruitName, int amount);

    void add(String fruitName, int amount);

    Integer get(String fruitName);

    Map<String, Integer> getAll();

    void reduce(String fruitName, int amount);

    void removeAll();

    Integer size();
}
