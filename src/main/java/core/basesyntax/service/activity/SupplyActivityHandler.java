package core.basesyntax.service.activity;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;

public class SupplyActivityHandler implements ActivityHandler {
    private FruitStorageDao fruitStorageDao;

    public SupplyActivityHandler(FruitStorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    @Override
    public boolean handle(FruitTransaction fruitTransaction) {
        Fruit fruit = fruitTransaction.getFruit();
        if (fruitStorageDao.getAmountByFruit(fruit) == null) {
            throw new RuntimeException("Missing balance info about of fruit "
                    + "for supply operation, fruit: " + fruit.getName());
        }
        return fruitStorageDao.update(fruit, fruitStorageDao.getAmountByFruit(fruit)
                + fruitTransaction.getAmount());
    }
}
