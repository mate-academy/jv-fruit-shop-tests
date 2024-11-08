package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> OPERATION_HANDLERS
            = new HashMap<>();
    private static final FruitTransaction.Operation BALANCE_OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation PURSCHASE_OPERATION
            = FruitTransaction.Operation.PURCHASE;
    private static final FruitTransaction.Operation RETURN_OPERATION
            = FruitTransaction.Operation.RETURN;
    private static final FruitTransaction.Operation SUPPLE_OPERATION
            = FruitTransaction.Operation.SUPPLY;
    private static final OperationHandler BALANCE_OPERATION_HANDLER
            = new BalanceOperationHandler();
    private static final OperationHandler PURCHASE_OPERATION_HANDLER
            = new PurchaseOperationHandler();
    private static final OperationHandler RETURN_OPERATION_HANDLER
            = new ReturnOperationHandler();
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationStrategy = new OperationStrategy(OPERATION_HANDLERS);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.BALANCE, BALANCE_OPERATION_HANDLER);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.PURCHASE, PURCHASE_OPERATION_HANDLER);
        OPERATION_HANDLERS.put(FruitTransaction.Operation.RETURN, RETURN_OPERATION_HANDLER);
    }

    @AfterEach
    void afterAll() {
        OPERATION_HANDLERS.clear();
    }

    @Test
    void get_isOK() {
        OperationHandler actual = operationStrategy.get(BALANCE_OPERATION);
        assertEquals(BALANCE_OPERATION_HANDLER, actual);
        actual = operationStrategy.get(PURSCHASE_OPERATION);
        assertEquals(PURCHASE_OPERATION_HANDLER, actual);
        actual = operationStrategy.get(RETURN_OPERATION);
        assertEquals(RETURN_OPERATION_HANDLER, actual);
    }

    @Test
    void get_nullValue_notOK() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.get(null));
    }

    @Test
    void get_OperationNotInHandlerMap_notOK() {
        assertThrows(NoSuchElementException.class, () -> operationStrategy.get(SUPPLE_OPERATION));
    }
}
