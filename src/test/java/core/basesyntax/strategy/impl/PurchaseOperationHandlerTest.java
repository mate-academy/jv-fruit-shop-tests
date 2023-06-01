package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_validFruitTransaction_Ok() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int purchasedQuantity = 3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, fruit, purchasedQuantity);
        purchaseOperationHandler.handle(fruitTransaction);
        int expectedQuantity = initialQuantity - purchasedQuantity;
        Assertions.assertEquals(expectedQuantity, Storage.FRUITS.get(fruit));
    }

    @Test
    public void handle_nullFruit_NotOk() {
        int purchasedQuantity = 3;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, null, purchasedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_emptyFruit_NotOk() {
        int purchasedQuantity = 3;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, "", purchasedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_negativeQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int purchasedQuantity = -3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, fruit, purchasedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_insufficientQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int purchasedQuantity = 15;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.PURCHASE, fruit, purchasedQuantity);
        Assertions.assertThrows(RuntimeException.class,
                () -> purchaseOperationHandler.handle(fruitTransaction));
    }
}
