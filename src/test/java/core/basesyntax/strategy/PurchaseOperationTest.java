package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void apply_nullTransaction_notOk() {
        assertThrows(Exception.class, () -> purchaseOperation.apply(null));
    }

    @Test
    void remove_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", -10
        );
        assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(transaction);
        });
    }

    @Test
    void remove_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, null, 10
        );
        assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(transaction);
        });
    }

    @Test
    void remove_quantityGreaterThanCurrent_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 10
        );
        Storage.add("banana", 5);
        assertThrows(IllegalArgumentException.class, () -> {
            purchaseOperation.apply(transaction);
        });
    }

    @Test
    void remove_validQuantity_Ok() {
        Storage.add("banana", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 1
        );
        purchaseOperation.apply(transaction);
        assertEquals(9, Storage.get("banana"));
    }
}
