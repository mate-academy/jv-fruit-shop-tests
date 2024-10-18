package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitOperationHandler purchaseOperation;
    private Map<String, Integer> inventory;

    @BeforeEach
    public void setUp() {
        inventory = new HashMap<>();
        purchaseOperation = new PurchaseOperation();

        inventory.put("apple", 5);
        inventory.put("orange", 5);
    }

    @Test
    public void testSuccessfulPurchaseOperationUpdatesInventoryCorrectly() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 3);
        purchaseOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(2, inventory.get("apple"));
    }

    @Test
    public void subtractionOfProductThatAreLessThan0_shouldThrowException_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "orange", 10);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.executeOperation(transaction, inventory));

        Assertions.assertEquals("Cannot complete purchase: "
                + "insufficient stock for orange", exception.getMessage());
    }
}
