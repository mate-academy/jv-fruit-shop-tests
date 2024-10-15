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
    void setUp() {
        inventory = new HashMap<>();
        purchaseOperation = new PurchaseOperation();
    }

    @Test
    void addQuantityToFruit_executeOperation_Ok() {
        inventory.put("apple", 5);
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 3);
        purchaseOperation.executeOperation(transaction, inventory);

        Assertions.assertEquals(2, inventory.get("apple"));
    }

    @Test
    void subtractionOfProductThatAreLessThan0_executeOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperation.executeOperation(transaction, inventory));
    }
}
