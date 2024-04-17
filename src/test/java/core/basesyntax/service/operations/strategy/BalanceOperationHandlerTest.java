package core.basesyntax.service.operations.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private final OperationHandler operationHandler =
            new BalanceOperationHandler(new FruitTransactionDaoImpl());

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void performOperation_oneField_ok() {
        operationHandler.performOperation("banana", 20);
        operationHandler.performOperation("apple", 10);

        assertEquals(20, Storage.fruits.get("banana"));
        assertEquals(10, Storage.fruits.get("apple"));
    }

    @Test
    public void performOperation_severalField_ok() {
        operationHandler.performOperation("banana", 20);
        operationHandler.performOperation("banana", 5);
        operationHandler.performOperation("banana", 10);

        assertEquals(10, Storage.fruits.get("banana"));
    }

    @Test
    public void performOperation_negativeQuantity_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.performOperation("banana", -20);
        });
    }

    @Test
    public void performOperation_nullKey_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.performOperation(null, 5);
        });
    }
}
