package core.basesyntax.strategy.impl;

import core.basesyntax.handler.*;
import core.basesyntax.strategy.*;
import core.basesyntax.utility.*;

import java.util.*;

public class ShopOperationStrategyImpl implements ShopOperationStrategy {
    private final Map<Operation, ShopOperationHandler> operationHandlerMap;

    public ShopOperationStrategyImpl(Map<Operation, ShopOperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public ShopOperationHandler get(Operation operation) {
        return operationHandlerMap.get(operation);
    }
}
