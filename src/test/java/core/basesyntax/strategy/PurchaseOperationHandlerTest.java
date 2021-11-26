package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchase;

    @BeforeClass
    public static void setUp() {
        purchase = new PurchaseOperationHandler();
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
        purchase.operate("banana", 20);
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void operate_nullInputData_notOk() {
        purchase.operate(null, 25);
    }

    @Test(expected = NullPointerException.class)
    public void operate_noSuchFruit_notOk() {
        purchase.operate("orange", 10);
    }

    @Test(expected = RuntimeException.class)
    public void operate_newQuantityMoreThanOldQuantity_notOk() {
        purchase.operate("apple", 120);
    }
}
