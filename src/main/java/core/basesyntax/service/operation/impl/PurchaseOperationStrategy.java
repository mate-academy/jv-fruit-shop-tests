package core.basesyntax.service.operation.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationStrategy;

public class PurchaseOperationStrategy implements OperationStrategy {
    private final FruitDao fruitDao;

    public PurchaseOperationStrategy(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void performOperation(FruitTransaction fruitTransaction) {
        int fruitToPurchaseQuantity = fruitTransaction.getQuantity();
        fruitDao.getFruitQuantityByName(fruitTransaction.getFruitName())
                .filter(qty -> qty >= fruitToPurchaseQuantity)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Can't purchase more fruits than available in store"));
        fruitDao.subtractFruitQuantity(fruitTransaction.getFruitName(), fruitToPurchaseQuantity);
    }
}
