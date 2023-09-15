package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.operations.BalanceOperationHandler;
import core.basesyntax.service.impl.operations.PurchaseOperationHandler;
import core.basesyntax.service.impl.operations.ReturnOperationHandler;
import core.basesyntax.service.impl.operations.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static OperationStrategyImpl strategy;
    private static OperationStrategyImpl emptyStrategy;
    private FruitTransaction transaction =
            new FruitTransaction(Operation.BALANCE, "banana", 10);

    @BeforeAll
    static void setup() {
        Map<Operation, OperationHandler> operationsWithHandlers = new HashMap<>();

        operationsWithHandlers.put(Operation.BALANCE, new BalanceOperationHandler());
        operationsWithHandlers.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationsWithHandlers.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationsWithHandlers.put(Operation.RETURN, new ReturnOperationHandler());

        strategy = new OperationStrategyImpl(operationsWithHandlers);
    }

    @Test
    void performTransaction_nullInput_notOk() {
        assertThrows(RuntimeException.class, () -> transaction.performTransaction(emptyStrategy));
    }

    @Test
    void performTransaction_ok() {
        assertEquals(Operation.BALANCE, transaction.getOperation());
        transaction.performTransaction(strategy);
        assertEquals(10, Storage.getFruitsBalance("banana"));

        transaction =
                new FruitTransaction(Operation.RETURN, "apple", 5);
        assertEquals("apple", transaction.getFruit());
        transaction.performTransaction(strategy);
        assertEquals(5, Storage.getFruitsBalance("apple"));

        transaction =
                new FruitTransaction(Operation.PURCHASE, "banana", 10);
        assertEquals(10, transaction.getAmount());
        transaction.performTransaction(strategy);
        assertEquals(0, Storage.getFruitsBalance("banana"));
    }
}
