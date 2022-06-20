package dao;

import java.util.Map;
import java.util.Set;

public class FruitDaoImpl implements FruitDao {
    private Map<String, Integer> storage;

    public FruitDaoImpl(Map<String, Integer> storage) {
        this.storage = storage;
    }

    public void add(String fruit, Integer quantity) {
        storage.put(fruit, quantity);
    }

    public Integer get(String fruit) {
        return storage.get(fruit);
    }

    @Override
    public Set<Map.Entry<String, Integer>> getAll() {
        return storage.entrySet();
    }
}
