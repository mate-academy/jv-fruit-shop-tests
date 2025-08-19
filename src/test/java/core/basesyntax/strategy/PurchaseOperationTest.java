package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    private final PurchaseOperation purchaseOperation = new PurchaseOperation();

    @BeforeEach
    void setUp() {
        Storage.storage.put("apple", 100);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);

        purchaseOperation.handle(transaction, Storage.storage);

        assertEquals(80, Storage.storage.get("apple"));
    }

    @Test
    void handle_purchaseMoreThanAvailable_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 150);

        assertThrows(IllegalArgumentException.class, () ->
                purchaseOperation.handle(transaction, Storage.storage)
        );
    }

    @Test
    void handle_purchaseNonExistingFruit_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10);

        assertThrows(IllegalArgumentException.class, () ->
                purchaseOperation.handle(transaction, Storage.storage)
        );
    }

    @Test
    void handle_nullTransaction_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                purchaseOperation.handle(null, Storage.storage)
        );
    }

    @Test
    void handle_nullStorage_throwIllegalArgumentException() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 50);

        assertThrows(IllegalArgumentException.class, () ->
                purchaseOperation.handle(transaction, null)
        );
    }
}
