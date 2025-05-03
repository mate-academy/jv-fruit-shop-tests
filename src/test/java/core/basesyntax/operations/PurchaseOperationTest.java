package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        Storage.storage.clear();
    }

    @Test
    void handle_purchaseOperation_Ok() {
        int expected = 10;
        Storage.storage.put("banana", 30);

        purchaseOperation.handle("banana", 20);
        int result = Storage.storage.get("banana");

        assertEquals(expected, result);
    }

    @Test
    void handle_purchaseOperation_notOk() {
        Storage.storage.put("banana", 30);
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.handle("banana", 40),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_purchaseNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> purchaseOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_purchaseNegativeInputFruitQuantity_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> purchaseOperation.handle("banana", -5),
                "InvalidInputDataException expected to be thrown");
    }
}
