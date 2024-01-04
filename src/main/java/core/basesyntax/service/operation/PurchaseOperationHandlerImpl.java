package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    private final FruitShopDao fruitShopDao;

    public PurchaseOperationHandlerImpl(FruitShopDao fruitShopDao) {
        this.fruitShopDao = fruitShopDao;
    }

    @Override
    public void operation(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int newQuantity = fruitShopDao.getQuantity(fruit) - fruitTransaction.getQuantity();
        if (newQuantity < 0) {
            throw new RuntimeException("Amount fruits can`t be negative");
        }
        fruitShopDao.put(fruit, newQuantity);
    }
}
