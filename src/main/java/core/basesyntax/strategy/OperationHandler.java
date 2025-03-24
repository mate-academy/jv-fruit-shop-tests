package core.basesyntax.strategy;

import java.util.Map;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction transaction, Map<String, Integer> storage);
}
