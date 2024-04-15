package core.basesyntax.service.operations.strategy;

import core.basesyntax.dao.FruitTransactionDao;

public class ReturnOperationHandler implements OperationHandler {

    private final FruitTransactionDao fruitTransactionDao;

    public ReturnOperationHandler(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void performOperation(String name, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Can't set negative quantity");
        }

        fruitTransactionDao.update(name, fruitTransactionDao.getByName(name) + quantity);
    }
}
