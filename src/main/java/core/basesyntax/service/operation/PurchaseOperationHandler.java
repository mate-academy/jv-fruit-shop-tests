package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    private final FruitDao purchaseFruitDao;

    public PurchaseOperationHandler(FruitDao fruitDao) {
        purchaseFruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruit) {
        if (fruit.getFruitQuantity() > 0) {
            purchaseFruitDao.setFruitQuantity(fruit.getFruitName(),
                    purchaseFruitDao.getFruitQuantity(fruit.getFruitName())
                            - fruit.getFruitQuantity());
        } else {
            throw new RuntimeException("Balance of " + fruit.getFruitName()
                    + " is negative " + purchaseFruitDao.getFruitQuantity(fruit.getFruitName()));
        }
    }
}
