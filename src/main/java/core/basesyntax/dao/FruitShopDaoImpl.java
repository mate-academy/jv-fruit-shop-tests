package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitShopDaoImpl implements FruitShopDao {
    @Override
    public int getQuantity(String fruit) {
        return Storage.fruitsDB.entrySet().stream()
                .filter(whatFruit -> whatFruit.getKey().equals(fruit))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Fruit " + fruit
                    + " record not found"));
    }

    @Override
    public void put(String fruit, Integer quantity) {
        Storage.fruitsDB.remove(fruit);
        Storage.fruitsDB.put(fruit, quantity);
    }
}
