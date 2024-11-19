package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {

    @Test
    void handle_purchaseItems_correctInventory_OK() {
        Map<String, Integer> inventory = new HashMap<>(Map.of("apple", 100, "banana", 50));
        List<FruitTransaction> transactions = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20)
        ));
        PurchaseOperation purchaseOperation = new PurchaseOperation();
        purchaseOperation.handle(transactions, inventory);

        Assertions.assertEquals(70, inventory.get("apple"));
        Assertions.assertEquals(30, inventory.get("banana"));
    }
}
