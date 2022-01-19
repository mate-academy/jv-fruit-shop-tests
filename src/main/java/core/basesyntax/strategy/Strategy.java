package core.basesyntax.strategy;

import core.basesyntax.dto.ShopOperation;
import java.util.Map;

public class Strategy {
    private Map<String, OperationHandler> handlers;

    public Strategy(Map<String, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    public OperationHandler getHandler(ShopOperation shopOperation) {
        return handlers.get(shopOperation.getOperation());
    }
}
