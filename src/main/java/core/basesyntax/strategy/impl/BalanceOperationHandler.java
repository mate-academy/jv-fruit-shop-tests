package core.basesyntax.strategy.impl;

import core.basesyntax.dao.impl.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationsHandler;

public record BalanceOperationHandler(FruitDao fruitDao) implements OperationsHandler {
    @Override
    public void applyOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Balance quantity has incorrect value: "
                    + fruitTransaction.getQuantity());
        }
        fruitDao.putToStorage(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
