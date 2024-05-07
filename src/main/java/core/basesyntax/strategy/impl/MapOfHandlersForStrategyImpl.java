package core.basesyntax.strategy.impl;

import static core.basesyntax.servise.impl.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.RETURN;
import static core.basesyntax.servise.impl.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.exception.MapOfHandlersForStrategyException;
import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import java.util.HashMap;
import java.util.Map;

public class MapOfHandlersForStrategyImpl implements MapOfHandlersForStrategy {
    private static final Map<FruitTransaction.Operation, OperationService> handlers
            = new HashMap<>();

    public MapOfHandlersForStrategyImpl(DaoStorage daoStorage) {
        if (daoStorage == null) {
            throw new MapOfHandlersForStrategyException(
                    "The argument DaoStorage for Operation services is NULL");
        }
        handlers.put(BALANCE, new BalanceOperationService(daoStorage));
        handlers.put(SUPPLY, new IncomingOperationService(daoStorage));
        handlers.put(RETURN, new IncomingOperationService(daoStorage));
        handlers.put(PURCHASE, new OutgoingOperationService(daoStorage));
    }

    public MapOfHandlersForStrategyImpl(
            Map<FruitTransaction.Operation, OperationService> alternativeMap,
            DaoStorage daoStorage) {
        if (alternativeMap == null || alternativeMap.isEmpty() || daoStorage == null) {
            throw new MapOfHandlersForStrategyException("The arguments is NULL or Empty.");
        }
        handlers.putAll(alternativeMap);
    }

    @Override
    public Map<FruitTransaction.Operation, OperationService> getHandlers() {
        if (!handlers.isEmpty()) {
            return handlers;
        }
        throw new MapOfHandlersForStrategyException("The Map is empty");
    }

    @Override
    public void putHandler(FruitTransaction.Operation operation,
                           OperationService operationService) {
        if (operation == null || operationService == null) {
            throw new MapOfHandlersForStrategyException("Operation or operation service is NULL");
        }
        handlers.put(operation, operationService);
    }

    @Override
    public void removeHandler(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new MapOfHandlersForStrategyException("Operation for remove is NULL");
        }
        handlers.remove(operation);
    }
}
