package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void calculate(Transaction transaction) {
        if (Storage.fruitsStorage.containsKey(transaction.getFruit())) {
            throw new RuntimeException("Data base already contains balance of fruit: "
                    + transaction.getFruit());
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("invalid quantity for transaction "
                    + transaction.getQuantity());
        }
        Storage.fruitsStorage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
