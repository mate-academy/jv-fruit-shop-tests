package core.basesyntax.strategy.handler;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    private FruitDao fruitDao;

    public PurchaseHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruit();
        int fruitQuantity = fruitTransaction.getQuantity();
        if (fruitQuantity < 0) {
            throw new IllegalArgumentException("Quantity can't be below zero you quantity is"
                    + fruitQuantity);
        }
        fruitDao.getStorage().put(fruitName, fruitDao.get(fruitName) - fruitQuantity);
    }
}
