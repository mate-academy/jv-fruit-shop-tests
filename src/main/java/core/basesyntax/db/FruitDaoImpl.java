package core.basesyntax.db;

import java.util.Map;
import java.util.Set;

public class FruitDaoImpl implements FruitDao {
    @Override
    public boolean addString(String fruitName, int quantity) {
        Storage.fruitStorage.put(fruitName, quantity);
        return true;
    }

    @Override
    public boolean remove(String fruitName) {
        int value = Storage.fruitStorage.remove(fruitName);
        return true;
    }

    @Override
    public boolean isEmpty() {
        return Storage.fruitStorage.isEmpty();
    }

    @Override
    public boolean contains(String fruitName) {
        return Storage.fruitStorage.containsKey(fruitName);
    }

    @Override
    public int getQuantityByName(String fruitName) {
        return Storage.fruitStorage.get(fruitName);
    }

    @Override
    public Set<Map.Entry<String, Integer>> getAll() {
        return Storage.fruitStorage.entrySet();
    }
}
