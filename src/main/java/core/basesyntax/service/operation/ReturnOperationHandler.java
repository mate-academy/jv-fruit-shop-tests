package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import java.util.Objects;

public class ReturnOperationHandler implements FruitOperationHandler {
    private final FruitDao fruitDao;

    public ReturnOperationHandler(FruitDao fruitDao) {
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
        ReturnOperationHandler that = (ReturnOperationHandler) o;
        return Objects.equals(fruitDao, that.fruitDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitDao);
    }

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruit();
        int currentQuantity = fruitDao.getQuantity(fruit);
        int transactionQuantity = fruitTransaction.getQuantity();
        fruitDao.addFruit(fruit, currentQuantity + transactionQuantity);
    }
}
