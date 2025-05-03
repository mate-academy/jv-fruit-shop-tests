package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import java.util.Objects;

public class BalanceOperationHandler implements FruitOperationHandler {
    private final FruitDao fruitDao;

    public BalanceOperationHandler(FruitDao fruitDao) {
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
        BalanceOperationHandler that = (BalanceOperationHandler) o;
        return Objects.equals(fruitDao, that.fruitDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitDao);
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        fruitDao.addFruit(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
