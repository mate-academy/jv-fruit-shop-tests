package core.basesyntax.strategy.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    private StorageDao dao;

    public BalanceOperationHandler(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void execute(FruitTransaction fruit) {
        if (dao.checkFruit(fruit.getName())) {
            dao.updateQuantity(fruit.getName(), fruit.getQuantity());
        } else {
            dao.add(fruit);
        }
    }
}
