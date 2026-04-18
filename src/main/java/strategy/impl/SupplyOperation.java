package strategy.impl;

import dao.FruitDao;
import model.FruitTransaction;
import strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public SupplyOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        int current = fruitDao.getQuantity(transaction.getFruit());
        fruitDao.update(transaction.getFruit(), current + transaction.getQuantity());
    }
}
