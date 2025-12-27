package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private PurchaseOperation purchaseOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    void setUp() {
        purchaseOperation = new PurchaseOperation();
        inventory = new HashMap<>();
    }

    @Test
    void apply_purchase_ok() {
        inventory.put("banana", 20);

        FruitTransaction tr = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        purchaseOperation.apply(tr, inventory);

        assertEquals(15, inventory.get("banana"));
    }

    @Test
    void apply_notEnough_exception() {
        inventory.put("banana", 2);

        FruitTransaction tr = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "banana", 5);

        assertThrows(RuntimeException.class, () -> purchaseOperation.apply(tr, inventory));
    }
}
