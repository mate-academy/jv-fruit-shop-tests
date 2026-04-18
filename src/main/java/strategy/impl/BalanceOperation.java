package strategy.impl;

import dao.FruitDao;
import model.FruitTransaction;
import strategy.OperationHandler;

public class BalanceOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public BalanceOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        // Використовуємо DAO замість прямого доступу до Storage
        fruitDao.update(transaction.getFruit(), transaction.getQuantity());
    }
}
