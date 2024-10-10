package core.basesyntax.service.strategy.strategyimpl;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(FruitRecord transaction) {
        if (transaction == null) {
            throw new NullPointerException("Transaction cannot be null");
        }
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
