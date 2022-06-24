package core.basesyntax.servise.transaction.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.servise.transaction.TransactionHandler;

public class PurchaseTransactionHandler implements TransactionHandler {

    @Override
    public void proceedTransaction(String item, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Can't purchase negative quantity of " + item);
        }
        int quantityBeforeTransaction = Storage.items.getOrDefault(item, 0);
        if (quantityBeforeTransaction < quantity) {
            throw new RuntimeException("Can't purchase. Deficiency in storage");
        }
        Storage.items.put(item, quantityBeforeTransaction - quantity);
    }
}
