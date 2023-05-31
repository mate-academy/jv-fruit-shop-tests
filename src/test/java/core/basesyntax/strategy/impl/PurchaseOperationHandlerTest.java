package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private Fruit fruit;
    private PurchaseOperationHandler purchaseOperationHandler;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.storage.put(new Fruit("banana"), 88);
        fruit = new Fruit("banana");
        fruitTransaction = new FruitTransaction("p", fruit, 89);
    }

    @Test(expected = RuntimeException.class)
    public void purchase89_BananaFromEmptyStorage_NotOk() {
        purchaseOperationHandler.apply(fruitTransaction);
    }

    @Test
    public void purchase77_BananaFromStorage_Ok() {
        fruitTransaction.setCount(77);
        purchaseOperationHandler.apply(fruitTransaction);
    }
}
