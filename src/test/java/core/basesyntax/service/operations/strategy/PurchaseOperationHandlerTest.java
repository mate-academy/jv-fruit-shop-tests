package core.basesyntax.service.operations.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private final OperationHandler operationHandler =
            new PurchaseOperationHandler(new FruitTransactionDaoImpl());

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void performOperation_normalData_ok() {
        Storage.fruits.put("banana", 5);
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("something", 9);

        operationHandler.performOperation("banana", 3);
        operationHandler.performOperation("apple", 2);

        assertEquals(2, Storage.fruits.get("banana"));
        assertEquals(8, Storage.fruits.get("apple"));
        assertEquals(9, Storage.fruits.get("something"));
    }

    @Test
    public void performOperation_negativeResult_throwsException() {
        Storage.fruits.put("banana", 5);

        assertThrows(RuntimeException.class, () -> {
            operationHandler.performOperation("banana", 10);
        });
    }

    @Test
    public void performOperation_negativeQuantity_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.performOperation("banana", -20);
        });
    }

    @Test
    public void performOperation_purchaseDefunctFruit_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            operationHandler.performOperation("banana", 10);
        });
    }
}
