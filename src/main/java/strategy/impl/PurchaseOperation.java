package strategy.impl;

import dao.FruitDao;
import exception.FruitStorageException;
import model.FruitTransaction;
import strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public PurchaseOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        int current = fruitDao.getQuantity(fruit);
        if (current < quantity) {
            throw new FruitStorageException("Not enough " + fruit + " in stock!");
        }
        fruitDao.update(fruit, current - quantity);
    }
}
