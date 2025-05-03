package core.basesyntax.service.operation;

import java.util.Map;

public class BalanceOperation implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity, Map<String, Integer> storage) {
        storage.put(fruit, quantity);
    }
}
