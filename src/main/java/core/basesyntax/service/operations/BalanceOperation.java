package core.basesyntax.service.operations;

import core.basesyntax.dao.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        if (transaction == null
                || transaction.getOperation() == null
                || transaction.getFruit() == null
                || transaction.getQuantity() == null) {
            throw new IllegalArgumentException("Transaction fields must not be null");
        }
        FruitStorage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
