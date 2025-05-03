package core.basesyntax.service.operation;

import java.util.Map;

public class ReturnOperation implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity, Map<String, Integer> storage) {
        storage.put(fruit, storage.getOrDefault(fruit, 0) + quantity);
    }
}
