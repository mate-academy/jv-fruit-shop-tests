package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    void handle(FruitTransaction transaction, Map<String, Integer> storage);
}
