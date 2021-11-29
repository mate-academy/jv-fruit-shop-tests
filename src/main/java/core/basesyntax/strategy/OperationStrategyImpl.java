package core.basesyntax.strategy;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.exception.OperationException;
import core.basesyntax.service.operation.AddOperationHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<String, OperationHandler> operationHandlerMap;

    {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("r", new AddOperationHandlerImpl(new FruitStorageDaoImpl()));
        operationHandlerMap.put("p", new PurchaseOperationHandlerImpl(new FruitStorageDaoImpl()));
        operationHandlerMap.put("b", new AddOperationHandlerImpl(new FruitStorageDaoImpl()));
        operationHandlerMap.put("s", new AddOperationHandlerImpl(new FruitStorageDaoImpl()));
    }

    @Override
    public OperationHandler get(String operation) {
        OperationHandler operationHandler = operationHandlerMap.get(operation);
        if (operationHandler == null) {
            throw new OperationException("Operation have not be found, please check the input");
        }
        return operationHandler;
    }
}
