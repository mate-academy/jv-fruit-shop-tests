package core.basesyntax.operation;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.StorageService;
import core.basesyntax.db.StorageServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        storageService = new StorageServiceImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageService));
        operationHandlers.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageService));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    public void getHandler_BalanceHandler_Ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.BALANCE);
        assertNotNull(handler);
    }

    @Test
    public void getHandler_PurchaseHandler_Ok() {
        OperationHandler handler = operationStrategy
                .getHandler(FruitTransaction.Operation.PURCHASE);
        assertNotNull(handler);
    }

    @Test
    public void getHandler_NoHandlerFound_NotOk() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(FruitTransaction.Operation.RETURN));
    }
}
