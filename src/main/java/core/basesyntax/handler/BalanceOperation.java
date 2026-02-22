package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;
import java.util.Map;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction, Map<String, Integer> storage) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        storage.put(
                transaction.getFruit(), transaction.getQuantity());
    }
}
