package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.strategy.BalanceOperationHandler;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.PurchaseOperationHandler;
import core.basesyntax.service.strategy.ReturnOperationHandler;
import core.basesyntax.service.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;

public class OperationHandlerStrategyImpl implements OperationHandlerStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> map = new HashMap<>();
    private final StorageDao storageDao = new StorageDaoImpl();

    {
        map.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storageDao));
        map.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(storageDao));
        map.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storageDao));
        map.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storageDao));
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        OperationHandler operationHandler = map.get(operation);
        if (operationHandler == null) {
            throw new RuntimeException("Wrong operation");
        }
        return operationHandler;
    }
}
