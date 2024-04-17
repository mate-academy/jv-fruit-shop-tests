package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;
import java.util.Set;

public class FruitTransactionDaoImpl implements FruitTransactionDao {
    @Override
    public void add(String name, int quantity) {
        if (name == null) {
            throw new RuntimeException("You can't create a record with the name null");
        }
        if (Storage.fruits.get(name) == null) {
            Storage.fruits.put(name, quantity);
        } else {
            update(name, quantity);
        }
    }

    @Override
    public void update(String name, int quantity) {
        if (name == null) {
            throw new RuntimeException("You can't update a record with the name null");
        }
        Storage.fruits.replace(name, quantity);
    }

    @Override
    public int getByName(String name) {
        if (name == null) {
            throw new RuntimeException("You can't get a record with the name null");
        }

        Integer value = Storage.fruits.get(name);
        if (value == null) {
            throw new RuntimeException("There is no element with the name " + name);
        }
        return value;
    }

    @Override
    public Set<Map.Entry<String, Integer>> getFull() {
        return Storage.fruits.entrySet();
    }
}
