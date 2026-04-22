package strategy.impl;

import dao.FruitDao;
import model.FruitTransaction;
import strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {
    private final FruitDao fruitDao;

    public ReturnOperation(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        int currentQuantity = fruitDao.getQuantity(fruit);
        int newQuantity = currentQuantity + quantity;

        fruitDao.update(fruit, newQuantity);
    }
}
