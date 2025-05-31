package core.basesyntax.service.handlerservice;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlers;
import java.util.Map;

public class HandlerServiceImpl implements HandlerService {
    private final Map<FruitTransaction.Operation, OperationHandlers> handlers;

    public HandlerServiceImpl(Map<FruitTransaction.Operation,
            OperationHandlers> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandlers getHandler(FruitTransaction.Operation activity) {
        return handlers.get(activity);
    }

}
