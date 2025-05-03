package core.basesyntax.service.impl;

import core.basesyntax.service.MapCreator;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.AddOperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;

public class MapCreatorImpl implements MapCreator {
    @Override
    public Map<String, OperationHandler> createMap() {
        Map<String, OperationHandler> handlers = new HashMap<>();
        handlers.put("b", new BalanceOperationHandler());
        handlers.put("p", new PurchaseOperationHandler());
        handlers.put("r", new AddOperationHandler());
        handlers.put("s", new AddOperationHandler());
        return handlers;
    }
}
