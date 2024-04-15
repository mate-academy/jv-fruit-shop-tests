package core.basesyntax.service.operations.strategy;

import core.basesyntax.dao.FruitTransactionDao;

public class BalanceOperationHandler implements OperationHandler {
    private final FruitTransactionDao fruitTransactionDao;

    public BalanceOperationHandler(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void performOperation(String name, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Can't set negative quantity");
        }

        fruitTransactionDao.add(name, quantity);
    }
}
