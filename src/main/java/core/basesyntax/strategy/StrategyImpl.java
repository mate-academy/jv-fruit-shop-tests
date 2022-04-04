package core.basesyntax.strategy;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.service.operation.AddOperationHandlerImpl;
import core.basesyntax.service.operation.BalanceOperationHandlerImpl;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandlerImpl;
import java.util.HashMap;
import java.util.Map;

public class StrategyImpl implements Strategy {
    private final StorageDao fruitStorageDao;

    public StrategyImpl(StorageDao fruitStorageDao) {
        this.fruitStorageDao = fruitStorageDao;
    }

    public Map<String, OperationHandler> getMap() {
        Map<String, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put("r", new AddOperationHandlerImpl(fruitStorageDao));
        operationHandlerMap.put("p", new PurchaseOperationHandlerImpl(fruitStorageDao));
        operationHandlerMap.put("b", new BalanceOperationHandlerImpl(fruitStorageDao));
        operationHandlerMap.put("s", new AddOperationHandlerImpl(fruitStorageDao));
        return operationHandlerMap;
    }

    @Override
    public OperationHandler get(String operation) {
        return getMap().get(operation);
    }
}
