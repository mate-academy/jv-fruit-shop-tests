package core.basesyntax.handler;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.Fruit;

public class BalanceOperationHandler implements DataOperationHandler {
    private FruitDao fruitDao;

    public BalanceOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void fruitOperation(Fruit fruit) {
        if (fruit.getQuantity() < 0) {
            throw new RuntimeException("The balance cannot be negative");
        }
        fruitDao.add(fruit.getTypeFruit(), fruit.getQuantity());
    }
}
