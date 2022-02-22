package fruitshop.dao;

import fruitshop.db.Storage;
import java.util.NoSuchElementException;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void addToStorage(String key, Integer value) {
        Storage.fruitList.put(key, value);
    }

    @Override
    public void addValue(String key, Integer value) {
        if (!Storage.fruitList.containsKey(key)) {
            throw new NoSuchElementException("Key doesn't exist");
        }
        Integer newValue = Storage.fruitList.get(key) + value;
        Storage.fruitList.put(key, newValue);
    }

    @Override
    public void subtractValue(String key, Integer value) {
        if (!Storage.fruitList.containsKey(key)) {
            throw new NoSuchElementException("Key doesn't exist");
        }
        if (Storage.fruitList.get(key) < value) {
            throw new RuntimeException("We don't have enough fruits");
        }
        Integer newValue = Storage.fruitList.get(key) - value;
        Storage.fruitList.put(key, newValue);
    }
}
