package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;

public class ReturnTransactionHandler implements TransactionHandler {
    @Override
    public void proceedTransaction(String item, int quantity) {
        Integer quantityBeforeTransaction = Storage.items.get(item);
        if (quantityBeforeTransaction == null) {
            throw new RuntimeException("The balance for " + item + "wasn't installed");
        }
        Storage.items.put(item, quantityBeforeTransaction + quantity);
    }
}
