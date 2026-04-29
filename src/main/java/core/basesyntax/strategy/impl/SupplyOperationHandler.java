package core.basesyntax.strategy.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    private StorageDao dao;

    public SupplyOperationHandler(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void execute(FruitTransaction fruit) {
        if (!dao.checkFruit(fruit.getName())) {
            dao.add(fruit);
        } else {
            dao.updateQuantity(fruit.getName(), fruit.getQuantity()
                    + dao.actualQuantity(fruit.getName()));
        }
    }
}
