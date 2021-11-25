package core.basesyntax.db;

import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitRecordsDaoImpl implements FruitRecordsDao {
    @Override
    public void save(Fruit fruit, Integer quantity) {
        if (fruit == null || fruit.getFruitName() == null || quantity == null) {
            throw new RuntimeException("Can`t save data to DB. Input data have null values:"
                    + " fruit = " + fruit
                    + ", quantity = " + quantity);
        }
        FruitStorage.fruitStorage.put(fruit, quantity);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return FruitStorage.fruitStorage;
    }

    @Override
    public Integer getFruitAmountFromStorage(Fruit fruit) {
        if (fruit == null || fruit.getFruitName() == null) {
            throw new RuntimeException("Can`t get data from DB. Input parameter has null value: "
                    + " fruit = " + fruit);
        }
        return FruitStorage.fruitStorage.get(fruit);
    }
}
