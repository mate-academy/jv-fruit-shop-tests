package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> storage, FruitTransaction transaction) {
        storage.merge(
                transaction.getFruit(),
                transaction.getQuantity(),
                Integer::sum
        );
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Balance cannot be negative");
        }
    }
}
