package core.basesyntax.service.operation.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;

public class OperationReturnHandler implements OperationHandler {

    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity not be negative");
        }
        int quantityFromDb = operationDao.get(fruitTransaction.getFruit());
        operationDao.put(fruitTransaction.getFruit(), quantityFromDb
                + fruitTransaction.getQuantity());
    }
}
