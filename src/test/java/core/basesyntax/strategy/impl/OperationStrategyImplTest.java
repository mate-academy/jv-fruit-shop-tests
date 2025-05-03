package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.handler.impl.BalanceOperationHandler;
import core.basesyntax.service.handler.impl.PurchaseOperationHandler;
import core.basesyntax.service.handler.impl.ReturnOperationHandler;
import core.basesyntax.service.handler.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        StorageDao storageDao = new StorageDaoImpl();
        Map<Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(Operation.BALANCE, new BalanceOperationHandler(storageDao));
        handlers.put(Operation.SUPPLY, new SupplyOperationHandler(storageDao));
        handlers.put(Operation.PURCHASE, new PurchaseOperationHandler(storageDao));
        handlers.put(Operation.RETURN, new ReturnOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getBalanceOperationHandler_isOk() {
        OperationHandler actual = operationStrategy.getOperationHandler(Operation.BALANCE);
        assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    void getSupplyOperationHandler_isOk() {
        OperationHandler actual = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertEquals(SupplyOperationHandler.class, actual.getClass());
    }

    @Test
    void getPurchaseOperationHandler_isOk() {
        OperationHandler actual = operationStrategy.getOperationHandler(Operation.PURCHASE);
        assertEquals(PurchaseOperationHandler.class, actual.getClass());
    }

    @Test
    void getReturnOperationHandler_isOk() {
        OperationHandler actual = operationStrategy.getOperationHandler(Operation.RETURN);
        assertEquals(ReturnOperationHandler.class, actual.getClass());
    }

    @Test
    void getNull_operationIsNull_isOk() {
        OperationHandler actual = operationStrategy.getOperationHandler(null);
        assertNull(actual);
    }

    @Test
    void getOperationHandler_mapIsNull_notOk() {
        OperationStrategy strategyWithNullMap = new OperationStrategyImpl(null);
        assertThrows(RuntimeException.class, () -> {
            strategyWithNullMap.getOperationHandler(Operation.BALANCE);
        }, "If map with handlers is Null it should throw Runtime Exception!");
    }
}
