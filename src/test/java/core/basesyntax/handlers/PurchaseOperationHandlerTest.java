package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
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
        Storage.storage.clear();
    }

    @Test
    void purchaseHandler_validData_ok() {
        Storage.storage.put("banana", 100);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 70
        );
        purchaseOperationHandler.calculateOperation(fruitTransaction);
        int fruitsInStorage = Storage.storage.get("banana");
        assertEquals(30, fruitsInStorage);
    }

    @Test
    void purchaseHandler_nullFruit_notOk() {
        Storage.storage.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 10
        );
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }

    @Test
    void purchaseHandler_NegativeQuantity_notOk() {
        Storage.storage.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -100
        );
        assertThrows(InvalidDataException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }

    @Test
    void purchaseHandler_quantityMoreAmount_notOk() {
        Storage.storage.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 70
        );
        assertThrows(InvalidDataException.class,
                () -> purchaseOperationHandler.calculateOperation(fruitTransaction));
    }
}
