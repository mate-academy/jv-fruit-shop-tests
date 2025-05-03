package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> handlers;

    @BeforeAll
    static void beforeAll() {
        handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategy(handlers);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    void processOperation_nullInputData_notOk() {
        assertThrows(IllegalArgumentException.class, () -> operationStrategy.processOperation(null),
                "Invalid data!");
    }

    @Test
    void processOperation_validInputDataBalanceCase_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);
        operationStrategy.processOperation(fruitTransaction);
        assertEquals(10, Storage.FRUITS.get("Apple"));
    }

    @Test
    void processOperation_validInputDataSupplyCase_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "Banana", 5);
        Storage.FRUITS.put("Banana", 2);
        operationStrategy.processOperation(fruitTransaction);
        assertEquals(7, Storage.FRUITS.get("Banana"));
    }

    @Test
    void processOperation_validInputDataPurchaseCase_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "Orange", 8);
        Storage.FRUITS.put("Orange", 15);
        operationStrategy.processOperation(fruitTransaction);
        assertEquals(7, Storage.FRUITS.get("Orange"));
    }

    @Test
    void processOperation_validInputDataReturnCase_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "Grapes", 3);
        Storage.FRUITS.put("Grapes", 12);
        operationStrategy.processOperation(fruitTransaction);
        assertEquals(15, Storage.FRUITS.get("Grapes"));
    }
}
