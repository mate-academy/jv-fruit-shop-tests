package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;

public class ReturnOperation implements OperationHandler {
    @Override
    public void doOperation(String fruitTransaction, Integer quantityForOperation) {
        Storage.getCalculatedTransactions().put(fruitTransaction,
                Storage.getCalculatedTransactions().get(fruitTransaction) + quantityForOperation);
    }
}
