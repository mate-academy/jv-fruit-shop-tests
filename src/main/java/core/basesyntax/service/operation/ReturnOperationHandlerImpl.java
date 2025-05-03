package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandlerImpl implements OperationHandler {
    private final FruitShopDao fruitShopDao;

    public ReturnOperationHandlerImpl(FruitShopDao fruitShopDao) {
        this.fruitShopDao = fruitShopDao;
    }

    @Override
    public void operation(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int newQuantity = fruitShopDao.getQuantity(fruit) + fruitTransaction.getQuantity();
        fruitShopDao.put(fruit, newQuantity);
    }
}
