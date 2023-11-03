package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeEach
    void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler(new FruitStorageDaoImpl()));
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(new FruitStorageDaoImpl()));
        operationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler(new FruitStorageDaoImpl()));
        operationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler(new FruitStorageDaoImpl()));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void findOperationHandler_forBalance_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.findByCode("b"));
        assertEquals(BalanceOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_forPurchase_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.findByCode("p"));
        assertEquals(PurchaseOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_forReturn_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.findByCode("r"));
        assertEquals(ReturnOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_forSupply_ok() {
        OperationHandler handler = operationStrategy
                .findOperationHandler(Operation.findByCode("s"));
        assertEquals(SupplyOperationHandler.class, handler.getClass());
    }

    @Test
    void findOperationHandler_forNonExistentOperation_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> operationStrategy.findOperationHandler(Operation.findByCode("UNKNOWN")));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitToStorageQuantityMap.clear();
    }
}
