package core.basesyntax.shop.strategy;

import core.basesyntax.shop.impl.FruitTransaction;
import java.util.Map;

public class Strategy {
    private final Map<String, OperationHandler> handlers;

    public Strategy(Map<String, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler getHandler(FruitTransaction shopOperation) {
        return handlers.get(shopOperation.getOperation());
    }
}
