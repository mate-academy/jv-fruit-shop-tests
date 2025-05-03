package core.basesyntax.dao;

import java.util.TreeMap;

public interface FruitDao {
    int getAmount(String fruitName);

    void add(String fruitName, int quantity);

    void changeAmount(String fruitName, int newAmount);

    TreeMap<String, Integer> getStorageData();
}
