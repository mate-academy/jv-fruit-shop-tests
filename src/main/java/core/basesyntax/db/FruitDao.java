package core.basesyntax.db;

import java.util.Map.Entry;
import java.util.Set;

public interface FruitDao {
    boolean addString(String fruitName, int quantity);

    boolean remove(String fruitName);

    boolean isEmpty();

    boolean contains(String fruitName);

    int getQuantityByName(String fruitName);

    Set<Entry<String, Integer>> getAll();
}
