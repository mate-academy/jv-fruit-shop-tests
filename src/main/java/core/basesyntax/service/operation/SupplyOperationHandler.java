package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao supplyFruitDao;

    public SupplyOperationHandler(FruitDao fruitDao) {
        supplyFruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruit) {
        if (fruit.getFruitQuantity() >= 0) {
            supplyFruitDao.setFruitQuantity(fruit.getFruitName(),
                    supplyFruitDao.getFruitQuantity(fruit.getFruitName())
                            + fruit.getFruitQuantity());
        } else {
            throw new RuntimeException("Balance of " + fruit.getFruitName()
                    + " is negative " + supplyFruitDao.getFruitQuantity(fruit.getFruitName()));
        }
    }
}
