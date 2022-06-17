package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnFruit implements OperationHandler {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public boolean makeOperation(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.RETURN) {
            throw new RuntimeException("You try to use incorrect operation instead RETURN "
                    + transaction.getOperation());
        }
        Integer currentQuantity;
        try {
            currentQuantity = fruitDao.getFruitQuantity(transaction.getFruit());
            currentQuantity += transaction.getQuantity();
        } catch (RuntimeException r) {
            return fruitDao.update(transaction.getFruit(), transaction.getQuantity());
        }
        return fruitDao.update(transaction.getFruit(), currentQuantity);
    }
}
