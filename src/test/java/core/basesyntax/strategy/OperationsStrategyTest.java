package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.handlers.BalanceOperationHandler;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.storage.Storage;
import core.basesyntax.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationsStrategyTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 100;
    private static OperationsStrategy operationsStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        operationsStrategy = new OperationsStrategy(handlers);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void performTransaction_ValidOperation_Ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(APPLE_QUANTITY);
        operationsStrategy.performTransaction(fruitTransaction);
        assertEquals(APPLE_QUANTITY, Storage.getFruitBalance().get(APPLE));
    }
}
