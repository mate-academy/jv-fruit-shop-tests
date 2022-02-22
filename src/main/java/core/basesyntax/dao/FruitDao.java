package core.basesyntax.dao;

import java.util.Map;

public interface FruitDao<K, V> {

    void add(String fruit, Integer amount);

    int getFruitCount(String nameFruit);

    void remove(String fruitName, int amount);

    Map<K, V> getAll();
}
