package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> storage, FruitTransaction transaction) {
        storage.merge(
                transaction.getFruit(),
                -transaction.getQuantity(),
                Integer::sum
        );
        if (storage.getOrDefault(transaction.getFruit(), 0) < 0) {
            throw new RuntimeException("Negative value found for fruit:" + transaction.getFruit());
        }
    }
}
