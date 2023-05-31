package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.dao.FruitDao;
import java.util.Objects;

public class SupplyOperationHandler implements OperationHandler {
    private final FruitDao fruitDao;

    public SupplyOperationHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupplyOperationHandler that = (SupplyOperationHandler) o;
        return Objects.equals(fruitDao, that.fruitDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitDao);
    }

    @Override
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (fruitDao.contains(fruit)) {
            fruitDao.addFruit(fruit, fruitDao.getQuantity(fruit) + transaction.getQuantity());
        } else {
            fruitDao.addFruit(fruit, transaction.getQuantity());
        }
    }
}
