package core.basesyntax.strategy.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
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
    void getOperation_validDataUsingPurchaseOperation_ok() {
        Storage.storage.put("banana", 39);
        operationHandler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(17);
        operationHandler.getOperation(fruitTransaction);
        int expectedValue = 22;
        int actualValue = Storage.storage.get("banana");
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getOperation_validDataUsingPurchaseOperation_notOk() {
        Storage.storage.put("banana", 7);
        operationHandler = new PurchaseHandler();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(29);
        assertThrows(RuntimeException.class, () -> operationHandler.getOperation(fruitTransaction));
    }
}
