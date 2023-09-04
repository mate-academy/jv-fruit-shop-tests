package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    private final FruitDao returnFruitDao;

    public ReturnOperationHandler(FruitDao fruitDao) {
        this.returnFruitDao = fruitDao;
    }

    @Override
    public void handle(FruitTransaction fruit) {
        if (fruit.getFruitQuantity() >= 0) {
            returnFruitDao.setFruitQuantity(fruit.getFruitName(),
                    returnFruitDao.getFruitQuantity(fruit.getFruitName())
                            + fruit.getFruitQuantity());
        } else {
            throw new RuntimeException("Balance of " + fruit.getFruitName()
                    + " is negative " + returnFruitDao.getFruitQuantity(fruit.getFruitName()));
        }
    }
}
