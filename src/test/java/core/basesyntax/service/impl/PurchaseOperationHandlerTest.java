package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test (expected = RuntimeException.class)
    public void process_initialStorageIsEmpty_notOk() {
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 8));
    }

    @Test
    public void process_initialQuantityIsEqualsToPurchase_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Integer expected = 0;
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 50));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test
    public void process_initialQuantityIsMoreThanPurchase_ok() {
        Storage.storage.put(new Fruit("apple"), 50);
        Integer expected = 10;
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 10));
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 30));
        Integer actual = Storage.storage.get(new Fruit("apple"));
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void process_initialQuantityIsLess_notOk() {
        Storage.storage.put(new Fruit("apple"), 50);
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 18));
        purchaseOperationHandler.process(new FruitTransaction("p", new Fruit("apple"), 37));
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }
}
