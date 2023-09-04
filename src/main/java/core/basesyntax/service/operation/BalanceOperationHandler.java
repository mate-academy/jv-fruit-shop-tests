package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    private final FruitDao balanceFruitDao;

    public BalanceOperationHandler(FruitDao fruitDao) {
        balanceFruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruit) {
        if (fruit.getFruitQuantity() >= 0) {
            balanceFruitDao.setFruitQuantity(fruit.getFruitName(), fruit.getFruitQuantity());
        } else {
            throw new RuntimeException("Balance of " + fruit.getFruitName()
                    + " is negative " + balanceFruitDao.getFruitQuantity(fruit.getFruitName()));
        }
    }
}
