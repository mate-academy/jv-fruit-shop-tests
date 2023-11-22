package core.basesyntax.strategy;

import core.basesyntax.db.FruitShopDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    private FruitShopDao fruitShopDao;

    public ReturnOperationHandler(FruitShopDao fruitShopDao) {
        this.fruitShopDao = fruitShopDao;
    }

    @Override
    public void handleOperation(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int newQuantity = fruitShopDao.getFruitQuantity(fruit) + transaction.getQuantity();
        fruitShopDao.put(fruit, newQuantity);
    }
}
