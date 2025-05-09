package fruitshop.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler handler;

    @BeforeEach
    void setUp_handler() {
        handler = new PurchaseOperationHandler();
    }

    @Test
    void apply_enoughQuantity_storageReducedOk() {
        Storage.put("apple", 50);
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);

        handler.apply(transaction);

        assertEquals(30, Storage.get("apple"));
    }

    @Test
    void apply_exactQuantity_storageZeroedOk() {
        Storage.put("banana", 10);

        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
        handler.apply(transaction);

        assertEquals(0, Storage.get("banana"));
    }

    @Test
    void apply_notEnoughQuantity_notOk() {
        Storage.put("orange", 5);

        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "orange", 10);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough orange"));
    }

    @Test
    void apply_negativeQuantity_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction transaction =
                    new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", -1);
        });
    }

    @Test
    void apply_fruitNotInStorage_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "kiwi", 1);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> handler.apply(transaction));

        assertTrue(exception.getMessage().contains("Not enough kiwi"));
    }

    @Test
    void apply_nullFruit_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, null, 1);

        assertThrows(NullPointerException.class, () -> handler.apply(transaction));
    }

    @AfterEach
    void clearStorage() {
        Storage.clear();
    }
}
