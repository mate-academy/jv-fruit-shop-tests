package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;

public class PurchaseTransactionHandler implements TransactionHandler {

    @Override
    public void proceedTransaction(String item, int quantity) {
        Integer quantityBeforeTransaction = Storage.items.get(item);
        if (quantityBeforeTransaction == null) {
            throw new RuntimeException("The balance for " + item + "wasn't installed");
        }
        if (quantityBeforeTransaction < quantity) {
            throw new RuntimeException("Can't purchase " + item + ". Deficiency in storage");
        }
        Storage.items.put(item, quantityBeforeTransaction - quantity);
    }
}
