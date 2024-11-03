package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT = "banana";
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.PURCHASE;
    private Map<String, Integer> storage;
    private PurchaseOperation purchaseOperation;

    @BeforeEach
    void setUp() {
        storage = new HashMap<>();
        purchaseOperation = new PurchaseOperation();
    }

    private void applyPurchase(int initialQuantity, int purchaseQuantity) {
        storage.put(FRUIT, initialQuantity);
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, purchaseQuantity);
        purchaseOperation.apply(storage, transaction);
    }

    @Test
    void purchasePositive_OK() {
        int initialQuantity = 100;
        int purchaseQuantity = 15;
        applyPurchase(initialQuantity, purchaseQuantity);
        assertEquals(initialQuantity - purchaseQuantity, storage.get(FRUIT),
                "Failed in purchasePositive_OK: "
                        + "Expected remaining quantity after purchase did not match.");
    }

    @Test
    void purchaseZero_OK() {
        int initialQuantity = 100;
        int purchaseQuantity = 0;
        applyPurchase(initialQuantity, purchaseQuantity);
        assertEquals(initialQuantity, storage.get(FRUIT),
                "Failed in purchaseZero_OK: "
                        + "Expected quantity should remain the same when purchasing zero units.");
    }

    @Test
    void purchaseNegative_NotOK() {
        int initialQuantity = 100;
        int purchaseQuantity = -25;
        storage.put(FRUIT, initialQuantity);
        FruitTransaction transaction = new FruitTransaction(OPERATION, FRUIT, purchaseQuantity);
        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(storage, transaction),
                "Failed in purchaseNegative_NotOK: "
                        + "Expected RuntimeException when purchasing a negative quantity.");
    }
}
