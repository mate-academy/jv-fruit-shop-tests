package core.basesyntax.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    private Map<String, Integer> fruits;

    public Storage() {
        fruits = new HashMap<>();
    }

    public Storage(Map<String, Integer> outerStorage) {
        fruits = outerStorage;
    }

    public void flush() {
        fruits.clear();
    }

    public void put(String fruit, Integer amount) {
        fruits.put(fruit, amount);
    }

    public Map<String, Integer> getFruitsInStorage() {
        return new HashMap<>(fruits);
    }

    public List<String[]> getAll() {
        List<String[]> fruitsList = new ArrayList<>();
        for (String key : fruits.keySet()) {
            fruitsList.add(new String[] {key, String.valueOf(fruits.get(key))});
        }
        return fruitsList;
    }

}
