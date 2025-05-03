package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void execute(Transaction transaction) {
        int fruitAmount = transaction.getQuantity();
        Storage.storage.put(new Fruit(transaction.getFruit().getName()), fruitAmount);
    }
}
