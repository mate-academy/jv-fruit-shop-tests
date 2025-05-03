package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private Storage storage;
    private OperationHandler balanceHandler;
    private OperationHandler purchaseHandler;
    private OperationHandler returnHandler;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        storage = new StorageImpl();
        balanceHandler = new BalanceOperation();
        purchaseHandler = new PurchaseOperation();
        returnHandler = new ReturnOperation();
        supplyHandler = new SupplyOperation();

        Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, balanceHandler);
        operationHandlers.put(Operation.PURCHASE, purchaseHandler);
        operationHandlers.put(Operation.RETURN, returnHandler);
        operationHandlers.put(Operation.SUPPLY, supplyHandler);

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void applyOperation_delegateToCorrectHandler_ok() {
        operationStrategy.applyOperation(Operation.BALANCE, "banana", 20, storage);
        assertEquals(20, storage.getQuantity("banana"));

        operationStrategy.applyOperation(Operation.SUPPLY, "banana", 15, storage);
        assertEquals(35, storage.getQuantity("banana"));

        operationStrategy.applyOperation(Operation.PURCHASE, "banana", 5, storage);
        assertEquals(30, storage.getQuantity("banana"));

        operationStrategy.applyOperation(Operation.RETURN, "banana", 10, storage);
        assertEquals(40, storage.getQuantity("banana"));
    }

    @Test
    void applyOperation_unsupportedOperation_notOk() {
        assertThrows(IllegalArgumentException.class, () ->
                operationStrategy.applyOperation(Operation.valueOf("UNSUPPORTED_OPERATION"),
                        "banana", 20, storage));
    }
}
