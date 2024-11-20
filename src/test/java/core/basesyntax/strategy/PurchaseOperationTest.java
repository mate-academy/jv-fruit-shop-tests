package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    @Test
    void handle_purchaseItems_correctInventory_OK() {
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 100, "banana", 50));
        FruitTransaction transaction1 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30);
        FruitTransaction transaction2 =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        purchaseOperation.handle(transaction1, inventory);
        purchaseOperation.handle(transaction2, inventory);

        assertEquals(70, inventory.get("apple"));
        assertEquals(30, inventory.get("banana"));
    }
}
