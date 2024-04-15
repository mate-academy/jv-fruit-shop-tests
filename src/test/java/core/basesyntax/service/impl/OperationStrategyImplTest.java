package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operations.strategy.BalanceOperationHandler;
import core.basesyntax.service.operations.strategy.OperationHandler;
import core.basesyntax.service.operations.strategy.PurchaseOperationHandler;
import core.basesyntax.service.operations.strategy.ReturnOperationHandler;
import core.basesyntax.service.operations.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static final FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @BeforeAll
    public static void init() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));

        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    public void get_correctData_ok() {
        assertEquals(operationHandlerMap.get(Operation.BALANCE),
                operationStrategy.get(Operation.BALANCE));
        assertEquals(operationHandlerMap.get(Operation.SUPPLY),
                operationStrategy.get(Operation.SUPPLY));
        assertEquals(operationHandlerMap.get(Operation.PURCHASE),
                operationStrategy.get(Operation.PURCHASE));
        assertEquals(operationHandlerMap.get(Operation.RETURN),
                operationStrategy.get(Operation.RETURN));
    }

    @Test
    public void get_null_notOk() {
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.get(null);
        });
    }
}
