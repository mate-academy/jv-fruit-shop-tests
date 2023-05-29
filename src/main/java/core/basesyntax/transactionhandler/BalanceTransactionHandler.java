package core.basesyntax.transactionhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.service.FruitTransaction;

public class BalanceTransactionHandler implements TransactionHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        Storage.fruits.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
