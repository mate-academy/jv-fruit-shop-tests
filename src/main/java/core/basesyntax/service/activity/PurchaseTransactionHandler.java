package core.basesyntax.service.activity;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import java.util.Objects;

public class PurchaseTransactionHandler implements TransactionHandler {
    private FruitDao fruitDao;

    public PurchaseTransactionHandler(FruitDao fruitDao) {
        this.fruitDao = fruitDao;
    }

    @Override
    public void handleTransaction(FruitTransaction fruitTransaction) {
        Integer balance = fruitDao.add(fruitTransaction.getFruitName(),
                -fruitTransaction.getQuantity());
        if (balance < 0) {
            throw new RuntimeException("Balance is negative after activity in class "
                    + this.getClass().getSimpleName());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PurchaseTransactionHandler that = (PurchaseTransactionHandler) o;
        return fruitDao.equals(that.fruitDao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fruitDao);
    }
}
