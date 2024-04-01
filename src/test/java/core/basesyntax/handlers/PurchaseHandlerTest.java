package core.basesyntax.handlers;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.PurchaseStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    @Test
    public void handle_ValidTransaction_UpdatesPurchase_Ok() {
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 30);

        Map<String, Integer> fruitStore = new HashMap<>();
        fruitStore.put("banana", 50);

        purchaseStrategy.handleTransaction(transaction, fruitStore);

        Integer actual = fruitStore.get("banana");
        Integer expected = 20;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handle_QuantityExceedsAvailable_ThrowsException_NotOk() {
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, "banana", 70);

        Map<String, Integer> fruitStore = new HashMap<>();
        fruitStore.put("banana", 50);

        Assert.assertThrows(RuntimeException.class, () ->
                purchaseStrategy.handleTransaction(transaction, fruitStore));
    }
}
