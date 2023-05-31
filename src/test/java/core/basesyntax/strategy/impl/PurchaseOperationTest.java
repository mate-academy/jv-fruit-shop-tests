package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationTest {
    private static OperationHandler purchaseOperation;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperation = new PurchaseOperation();
        fruit = new Fruit("apple");
    }

    @Test
    public void execute_validData_ok() {
        Storage.stock.put(fruit, 100);
        Integer expected = 35;
        purchaseOperation.execute(fruit, 65);
        Assert.assertEquals(
                "The method must correctly subtract the quantity.",
                expected, Storage.stock.get(fruit));
    }

    @Test(expected = NullPointerException.class)
    public void execute_notValidFruit_notOk() {
        purchaseOperation.execute(new Fruit("banana"), 10);
    }

    @Test
    public void execute_nullIgnoringFruit_Ok() {
        Storage.stock.put(fruit, 100);
        purchaseOperation.execute(null, 10);
        Assert.assertNull(Storage.stock.get(null));
    }

    @Test(expected = RuntimeException.class)
    public void execute_moreThanTotal_notOk() {
        Storage.stock.put(fruit, 9);
        purchaseOperation.execute(fruit, 10);
    }

    @Test(expected = RuntimeException.class)
    public void execute_negativeNumber_notOk() {
        Storage.stock.put(fruit, 100);
        purchaseOperation.execute(fruit, -10);
    }

    @After
    public void tearDown() {
        Storage.stock.clear();
    }
}
