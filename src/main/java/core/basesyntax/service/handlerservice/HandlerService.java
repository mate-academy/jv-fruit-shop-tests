package core.basesyntax.service.handlerservice;

import core.basesyntax.entity.FruitTransaction;
import core.basesyntax.service.handler.OperationHandlers;

public interface HandlerService {
    OperationHandlers getHandler(FruitTransaction.Operation activity);
}
