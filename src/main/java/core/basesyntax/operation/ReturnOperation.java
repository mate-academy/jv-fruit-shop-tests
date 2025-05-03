package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> storage, FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Cannot be negative");
        }
        storage.merge(
                transaction.getFruit(),
                transaction.getQuantity(),
                Integer::sum
        );
    }
}
