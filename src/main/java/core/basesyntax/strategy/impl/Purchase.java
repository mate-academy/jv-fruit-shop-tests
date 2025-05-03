package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class Purchase implements OperationHandler {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Override
    public boolean makeOperation(FruitTransaction transaction) {
        if (transaction.getOperation() != FruitTransaction.Operation.PURCHASE) {
            throw new RuntimeException("You try to use incorrect operation instead PURCHASE "
                    + transaction.getOperation());
        }
        int currentQuantity = fruitDao.getFruitQuantity(transaction.getFruit());
        if (transaction.getQuantity() > currentQuantity) {
            throw new RuntimeException("There are not enough "
                    + transaction.getFruit() + " in shop. There only " + currentQuantity);
        }
        currentQuantity -= transaction.getQuantity();
        return fruitDao.update(transaction.getFruit(), currentQuantity);
    }
}
