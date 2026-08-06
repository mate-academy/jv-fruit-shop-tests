package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public interface OperationHandler {
    void operate(Map<String, Integer> fruits, FruitTransaction transaction);
}
