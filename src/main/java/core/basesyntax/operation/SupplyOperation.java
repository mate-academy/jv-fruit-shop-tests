package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> storage, FruitTransaction transaction) {
        int quantity = transaction.getQuantity();

        if (quantity < 0) {
            throw new RuntimeException("Cant be negative: " + quantity);
        }

        storage.merge(
                transaction.getFruit(),
                quantity,
                Integer::sum
        );
    }
}
