package core.basesyntax.strategy.impl;

import core.basesyntax.db.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    private StorageDao dao;

    public ReturnOperationHandler(StorageDao dao) {
        this.dao = dao;
    }

    @Override
    public void execute(FruitTransaction fruit) {

        if (!dao.checkFruit(fruit.getName())) {
            throw new RuntimeException("The fruit " + fruit.getName() + " does not exist");
        }
        dao.updateQuantity(fruit.getName(), fruit.getQuantity()
                + dao.actualQuantity(fruit.getName()));
    }
}
