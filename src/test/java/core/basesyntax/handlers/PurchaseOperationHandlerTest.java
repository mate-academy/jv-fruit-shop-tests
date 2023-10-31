package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.excteption.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void purchaseHandler_ValidData_ok() {
        Storage.STORAGE.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 70
        );
        assertDoesNotThrow(() -> purchaseOperationHandler.calculateOperation(fruitTransaction));
        int fruitsInStorage = Storage.STORAGE.get("banana");
        assertEquals(30, fruitsInStorage);
    }

    @Test
    void purchaseHandler_nullFruit_notOk() {
        Storage.STORAGE.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 10
        );
        assertThrows(NullPointerException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }

    @Test
    void purchaseHandler_NegativeQuantity_notOk() {
        Storage.STORAGE.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }

    @Test
    void purchaseHandler_quantityMoreAmount_notOk() {
        Storage.STORAGE.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 70
        );
        assertThrows(InvalidDataException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }
}
