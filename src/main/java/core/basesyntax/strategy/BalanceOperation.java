package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class BalanceOperation implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction can't be null");
        }
        if (storage == null) {
            throw new IllegalArgumentException("Storage can't be null");
        }

        storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
