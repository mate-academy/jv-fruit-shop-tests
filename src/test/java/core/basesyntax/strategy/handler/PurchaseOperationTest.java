package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        Storage.getFruits().clear();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void apply_ok() {
        Storage.getFruits().put("apple", 10);

        purchaseOperation.apply(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                "apple",
                5));

        assertEquals(5, Storage.getFruits().get("apple"));
    }

    @Test
    void apply_notEnoughFruit_notOk() {
        assertThrows(RuntimeException.class,
                () -> purchaseOperation.apply(
                        new FruitTransaction(
                                FruitTransaction.Operation.PURCHASE,
                                "apple",
                                5)));
    }
}
