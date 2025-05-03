package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseHandler;

    @Before
    public void setUp() {
        purchaseHandler = new PurchaseOperationHandler();
        Storage.storage.put(new Fruit("banana"),25);
        Storage.storage.put(new Fruit("apple"), 100);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void operate_validData_ok() {
        int expected = 5;
        purchaseHandler.operate("banana", 20);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(actual, expected);
    }

    @Test(expected = NullPointerException.class)
    public void operate_nullInputData_notOk() {
        purchaseHandler.operate(null, 25);
    }

    @Test(expected = RuntimeException.class)
    public void operate_newQuantityMoreThanOldQuantity_notOk() {
        purchaseHandler.operate("apple", 120);
    }

    @Test(expected = NullPointerException.class)
    public void operate_noSuchFruit_notOk() {
        purchaseHandler.operate("orange", 10);
    }
}
