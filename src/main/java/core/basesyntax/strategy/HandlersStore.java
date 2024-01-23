package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.Handler;
import java.util.Map;

public class HandlersStore {
    private static Map<FruitTransaction.Operation,Handler> strategy;

    public HandlersStore(Map<FruitTransaction.Operation, Handler> strategy) {
        this.strategy = strategy;
    }

    public static Map<FruitTransaction.Operation, Handler> getStrategy() {
        return strategy;
    }
}
