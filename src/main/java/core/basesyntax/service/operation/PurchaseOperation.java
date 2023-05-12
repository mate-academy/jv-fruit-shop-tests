package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    private FruitTransactionDao fruitTransactionDao;

    public PurchaseOperation(FruitTransactionDao fruitTransactionDao) {
        this.fruitTransactionDao = fruitTransactionDao;
    }

    @Override
    public void operation(FruitTransaction fruitTransaction) {
        FruitTransaction getFruitTransaction = fruitTransactionDao.get(fruitTransaction.getFruit());
        int fruitQuantity = getFruitTransaction.getQuantity() - fruitTransaction.getQuantity();
        getFruitTransaction.setQuantity(fruitQuantity);
        fruitTransactionDao.update(getFruitTransaction);
    }
}
