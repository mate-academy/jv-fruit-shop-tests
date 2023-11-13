package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.BalanceOperationHandler;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private StorageDao storageDao;
    private FruitTransaction fruitTransaction;
    private Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeEach
    void setUp() {
        storageDao = new FruitDao();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_expectedHandler_ok() {
        OperationHandler expectedOperationHandler = operationStrategy.getHandler(fruitTransaction);
        OperationHandler actualOperationHandler = operationStrategy.getHandler(fruitTransaction);

        assertEquals(expectedOperationHandler, actualOperationHandler);
    }

    @Test
    void getHandler_emptyHandlersMap_notOk() {
        handlers.clear();
        assertThrows(RuntimeException.class, () -> operationStrategy.getHandler(fruitTransaction));
    }

    @Test
    void getHandler_nullTransaction_notOk() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.getHandler(null));
    }
}
