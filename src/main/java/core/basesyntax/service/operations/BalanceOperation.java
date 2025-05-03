package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;

public class BalanceOperation implements OperationHandler {
    @Override
    public void doOperation(String fruitTransaction, Integer quantityForOperation) {
        Storage.getCalculatedTransactions().put(fruitTransaction, quantityForOperation);
    }
}
