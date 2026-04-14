package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    void handleOperation(FruitTransaction transaction, Map<String, Integer> storage);
}
