package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void getOperation_null_notOk() {
        BalanceHandler operationHandler = new BalanceHandler();
        assertThrows(IllegalArgumentException.class, () -> operationHandler.getOperation(null));
    }

    @Test
    void getOperation_validDataUsingSupplyOperation_ok() {
        operationHandler = new SupplyHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(100);
        operationHandler.getOperation(fruitTransaction);
        int expectedValue = 100;
        int actualValue = Storage.storage.get("banana");
        assertEquals(expectedValue, actualValue);
    }
}
