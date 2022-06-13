package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class FruitDaoImp implements FruitDao {
    @Override
    public void add(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new RuntimeException("Can't add null to db");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit is null");
        }
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Quantity = 0");
        }
        if (fruitTransaction.getOperation() == null) {
            throw new RuntimeException("Operation not specified");
        }
        Storage.warehouse.add(fruitTransaction);
    }

    @Override
    public FruitTransaction get(String fruit) {
        return Storage.warehouse.stream()
                .filter(f -> f.getFruit().equals(fruit))
                .findFirst()
                .orElse(null);
    }
}
