package core.basesyntax.handlers;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.PurchaseStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final String BANANA_TO_OPERATION = "banana";

    @Test
    public void handle_ValidTransaction_UpdatesPurchase() {
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, BANANA_TO_OPERATION, 30);

        Map<String, Integer> fruitStore = new HashMap<>();
        fruitStore.put(BANANA_TO_OPERATION, 50);

        purchaseStrategy.handleTransaction(transaction, fruitStore);

        Integer actual = fruitStore.get(BANANA_TO_OPERATION);
        Integer expected = 20;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void handle_QuantityExceedsAvailable_ThrowsException_NotOk() {
        PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.PURCHASE, BANANA_TO_OPERATION, 70);

        Map<String, Integer> fruitStore = new HashMap<>();
        fruitStore.put(BANANA_TO_OPERATION, 50);

        Assert.assertThrows(RuntimeException.class, () ->
                purchaseStrategy.handleTransaction(transaction, fruitStore));
    }
}
