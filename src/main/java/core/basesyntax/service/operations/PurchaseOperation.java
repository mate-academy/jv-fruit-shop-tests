package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void doOperation(String fruitTransaction, Integer quantityForOperation) {
        if (Storage.getCalculatedTransactions().get(fruitTransaction) >= quantityForOperation) {
            Storage.getCalculatedTransactions().put(fruitTransaction,
                    Storage.getCalculatedTransactions().get(fruitTransaction) - quantityForOperation);
        }
    }
}
