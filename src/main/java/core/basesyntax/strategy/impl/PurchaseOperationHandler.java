package core.basesyntax.strategy.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    private StorageDao dao;

    public PurchaseOperationHandler(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void execute(FruitTransaction fruit) {

        if (!dao.checkFruit(fruit.getName())) {
            throw new RuntimeException("The fruit " + fruit.getName() + " does not exist");
        }

        if (fruit.getQuantity() > dao.actualQuantity(fruit.getName())) {
            throw new IllegalArgumentException("Balance can't be negative");
        }
        dao.updateQuantity(fruit.getName(), dao.actualQuantity(fruit.getName())
                - fruit.getQuantity());
    }
}
